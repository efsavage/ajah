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

import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
import com.ajah.user.UserStatusImpl;
import com.ajah.user.UserTypeImpl;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserInfoImpl;
import com.ajah.util.AjahUtils;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
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
			return this.jdbcTemplate.queryForObject("SELECT user_id, username, password, status, type FROM user WHERE username = ? and password = ?",
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
			user.setPassword(rs.getString("password"));
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

}