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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
import com.ajah.user.UserStatusImpl;
import com.ajah.user.UserTypeImpl;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserInfoImpl;
import com.ajah.util.AjahUtils;
import com.ajah.util.crypto.Password;

/**
 * Data operations on the "user" table.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public class UserDao {

	private static final Logger log = Logger.getLogger(UserDao.class.getName());

	private JdbcTemplate jdbcTemplate;

	/**
	 * Sets up a new JDBC template with the supplied data source.
	 * 
	 * @param dataSource
	 *            DataSource to use for a new JDBC template.
	 */
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// private TransactionTemplate transactionTemplate;
	//
	// /**
	// * Sets the transaction manager.
	// *
	// * @param transactionManager
	// */
	// @Autowired
	// public void setTransactionManager(PlatformTransactionManager
	// transactionManager) {
	// this.transactionTemplate = new TransactionTemplate(transactionManager);
	// }

	/**
	 * Finds a user by the username field.
	 * 
	 * @param username
	 *            Value to match against user.username column.
	 * @param password
	 * @return User, if found, or null.
	 */
	public User findUserByUsername(String username, String password) {
		try {
			return this.jdbcTemplate.queryForObject("SELECT " + UserInfoRowMapper.USER_FIELDS + " FROM user WHERE username = ? and password = ?",
					new Object[] { username, password }, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	static final class UserRowMapper implements RowMapper<User> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new UserImpl();
			user.setId(new UserId(rs.getString("user_id")));
			user.setUsername(rs.getString("username"));
			user.setStatus(UserStatusImpl.get(rs.getString("status")));
			user.setType(UserTypeImpl.get(rs.getString("type")));
			return user;
		}

	}

	/**
	 * Finds user info by user id.
	 * 
	 * @param userId
	 *            UserID for user, required.
	 * @return User info for given ID, or null.
	 */
	public UserInfo findUserInfo(UserId userId) {
		AjahUtils.requireParam(userId, "userId");
		try {
			return this.jdbcTemplate.queryForObject(
					"SELECT user_id, first_name, middle_name, last_name, birth_day, birth_month, birth_year FROM user_info WHERE user_id = ?",
					new Object[] { userId.toString() }, new UserInfoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	static final class UserInfoRowMapper implements RowMapper<UserInfo> {

		static final String USER_FIELDS = "user_id, username, status, type";

		/**
		 * {@inheritDoc}
		 */
		@Override
		public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserInfo userInfo = new UserInfoImpl(new UserId(rs.getString("user_id")));
			userInfo.setFirstName(rs.getString("first_name"));
			userInfo.setMiddleName(rs.getString("middle_name"));
			userInfo.setLastName(rs.getString("last_name"));
			userInfo.setBirthDay(rs.getInt("birth_day"));
			userInfo.setBirthMonth(rs.getInt("birth_month"));
			userInfo.setBirthYear(rs.getInt("birth_year"));
			return userInfo;
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
	 */
	@Transactional
	public void insert(User user, Password password) {
		AjahUtils.requireParam(user, "user");
		AjahUtils.requireParam(password, "password");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		this.jdbcTemplate.update("INSERT INTO user (user_id, username, password, status, type) VALUES (?,?,?,?,?)", new Object[] {
				user.getId().toString(), user.getUsername(), password.toString(), user.getStatus().getId() + "", user.getType().getId() + "" });
	}

	/**
	 * Find a user by username.
	 * 
	 * @param username
	 *            Value to match against the user.username column, required.
	 * @return User if found, otherwise null.
	 */
	public User findUserByUsername(String username) {
		AjahUtils.requireParam(username, "username");
		try {
			return this.jdbcTemplate.queryForObject("SELECT " + UserInfoRowMapper.USER_FIELDS + " FROM user WHERE username = ?",
					new Object[] { username }, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	/**
	 * Find a user by unique ID.
	 * 
	 * @param userId
	 *            Value to match against the user.user_id column, required.
	 * @return User if found, otherwise null.
	 */
	public User findUser(UserId userId) {
		AjahUtils.requireParam(userId, "userId");
		try {
			return this.jdbcTemplate.queryForObject("SELECT " + UserInfoRowMapper.USER_FIELDS + " FROM user WHERE user_id = ?",
					new Object[] { userId.toString() }, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

}