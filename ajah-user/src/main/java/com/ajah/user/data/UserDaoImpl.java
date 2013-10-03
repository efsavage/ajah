/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ajah.crypto.Password;
import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.spring.jdbc.err.DataOperationExceptionUtils;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
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

	@Override
	public User findByUsername(final String username) throws DataOperationException {
		return super.findByField("username", username);
	}

	@Override
	public User findByUsernameAndPassword(final String username, final String password) throws DataOperationException {
		return super.findByFields(new String[] { "username", "password" }, new String[] { username, password });
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
	public int update(final UserId userId, final Password password) throws DataOperationException {
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
	 * @see com.ajah.user.data.UserDao#count(com.ajah.user.UserType,
	 *      com.ajah.user.UserStatus)
	 */
	@Override
	public long count(UserType type, UserStatus status) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

}
