/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.ajah.user.data;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.ajah.crypto.CryptoException;
import com.ajah.crypto.HmacSha1Password;
import com.ajah.crypto.Password;
import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.spring.jdbc.err.DataOperationExceptionUtils;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
import com.ajah.user.UserNotFoundException;
import com.ajah.user.UserStatus;
import com.ajah.user.UserType;
import com.ajah.util.AjahUtils;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserDaoImpl extends AbstractAjahDao<UserId, User, UserImpl> implements UserDao {

	/**
	 * @see com.ajah.user.data.UserDao#count(com.ajah.user.UserType,
	 *      com.ajah.user.UserStatus)
	 */
	@Override
	public long count(final UserType type, final UserStatus status) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	@Override
	public User findByUserIdAndPassword(final UserId userId, final String password) throws DataOperationException {
		return super.find(new Criteria().eq(userId).eq("password", password));
	}

	@Override
	public User findByUsername(final String username) throws DataOperationException {
		return super.findByField("username", username);
	}

	@Override
	public User findByUsernameAndPassword(final String username, final String password) throws DataOperationException {
		return super.findByFields(new String[] { "username", "password" }, new String[] { username, password });
	}

	/**
	 * @see com.ajah.user.data.UserDao#getPassword(com.ajah.user.UserId)
	 */
	@Override
	public Password getPassword(final UserId userId) throws CryptoException, DataOperationException {
		try {
			final String value = this.jdbcTemplate.queryForObject("SELECT password from user WHERE user_id=?", String.class, new Object[] { userId.toString() });
			return new HmacSha1Password(value, true);
		} catch (final EmptyResultDataAccessException e) {
			return null;
		} catch (final DataAccessException e) {
			throw DataOperationExceptionUtils.translate(e, "user");
		}
	}

	/**
	 * @see com.ajah.user.data.UserDao#getUsername(com.ajah.user.UserId)
	 */
	@Override
	public String getUsername(final UserId userId) throws DataOperationException {
		try {
			return this.jdbcTemplate.queryForObject("SELECT username from user WHERE user_id=?", String.class, new Object[] { userId.toString() });
		} catch (final EmptyResultDataAccessException e) {
			return null;
		} catch (final DataAccessException e) {
			throw DataOperationExceptionUtils.translate(e, "user");
		}
	}

	/**
	 * @see com.ajah.user.data.UserDao#getRandomUser(UserStatus)
	 */
	@Override
	public User getRandomUser(final UserStatus status) throws DataOperationException {
		return super.find(new Criteria().eq("status", status).randomOrder());
	}

	/**
	 * @see com.ajah.user.data.UserDao#getStatus(com.ajah.user.UserId)
	 */
	@Override
	public UserStatus getStatus(final UserId userId) throws UserNotFoundException, DataOperationException {
		try {
			final String value = this.jdbcTemplate.queryForObject("SELECT status from user WHERE user_id=?", String.class, new Object[] { userId.toString() });
			return UserStatus.get(value);
		} catch (final DataAccessException e) {
			throw DataOperationExceptionUtils.translate(e, "user");
		}
	}

	/**
	 * This method is for saving a new user. It includes the password field
	 * since that field is the only field on the user table that is not mapped
	 * to a User property.
	 * 
	 * @param user
	 *            The user to save, required.
	 * @param password
	 *            The password for the user, required.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	@Override
	public int insert(final User user, final Password password) throws DataOperationException {
		AjahUtils.requireParam(user, "user");
		AjahUtils.requireParam(password, "password");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		try {
			return this.jdbcTemplate.update("INSERT INTO user (user_id, username, password, status, type) VALUES (?,?,?,?,?)",
					new Object[] { user.getId().toString(), user.getUsername(), password.toString(), user.getStatus().getId() + "", user.getType().getId() + "" });
		} catch (final DataAccessException e) {
			throw DataOperationExceptionUtils.translate(e, "user");
		}
	}

	/**
	 * UPDATEs the user table with a new password.
	 * 
	 * @param userId
	 *            ID of user to update, required.
	 * @param password
	 *            Password to update to, required.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	@Override
	@CacheEvict(value = "User", allEntries = true)
	public int updatePassword(final UserId userId, final Password password) throws DataOperationException {
		AjahUtils.requireParam(userId, "userId");
		AjahUtils.requireParam(password, "password");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		try {
			return this.jdbcTemplate.update("UPDATE user SET password = ? WHERE user_id = ?", new Object[] { password.toString(), userId.toString() });
		} catch (final DataAccessException e) {
			throw DataOperationExceptionUtils.translate(e, "user");
		}
	}

	/**
	 * @see com.ajah.user.data.UserDao#updateUsername(com.ajah.user.UserId,
	 *      java.lang.String)
	 */
	@Override
	public int updateUsername(final UserId userId, final String username) throws DataOperationException {
		AjahUtils.requireParam(userId, "userId");
		AjahUtils.requireParam(username, "username");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		try {
			return this.jdbcTemplate.update("UPDATE user SET username = ? WHERE user_id = ?", new Object[] { username, userId.toString() });
		} catch (final DataAccessException e) {
			throw DataOperationExceptionUtils.translate(e, "user");
		}
	}
}
