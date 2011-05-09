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

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
import com.ajah.user.UserStatusImpl;
import com.ajah.user.UserTypeImpl;
import com.ajah.util.AjahUtils;
import com.ajah.util.crypto.Password;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserDao extends AbstractAjahDao<UserId, User> {

	private static final Logger log = Logger.getLogger(UserDao.class.getName());

	private static final String SELECT_FIELDS = "user_id, username, status, type";

	private static final String TABLE_NAME = "user";

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
			return this.jdbcTemplate.queryForObject("SELECT " + SELECT_FIELDS + " FROM user WHERE username = ? and password = ?", new Object[] {
					username, password }, new UserRowMapper());
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
		return findByField("username", username);
	}

	/**
	 * UPDATEs the user table with a new password.
	 * 
	 * @param userId
	 *            ID of user to update, required.
	 * @param password
	 *            Password to update to, required.
	 */
	public void update(UserId userId, Password password) {
		AjahUtils.requireParam(userId, "userId");
		AjahUtils.requireParam(password, "password");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		this.jdbcTemplate.update("UPDATE user SET password = ? WHERE user_id = ?", new Object[] { password.toString(), userId.toString() });
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSelectFields() {
		return SELECT_FIELDS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected RowMapper<User> getRowMapper() {
		return new UserRowMapper();
	}

}