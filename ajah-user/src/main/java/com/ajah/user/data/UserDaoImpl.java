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

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
import com.ajah.util.AjahUtils;
import com.ajah.util.crypto.Password;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserDaoImpl extends AbstractAjahDao<UserId, User, UserImpl> implements UserDao {

	@Override
	public User findByUsername(final String username) {
		return super.findByField("username", username);
	}

	@Override
	public User findByUsernameAndPassword(final String username, final String password) {
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
	 */
	@Override
	public int insert(final User user, final Password password) {
		AjahUtils.requireParam(user, "user");
		AjahUtils.requireParam(password, "password");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		return this.jdbcTemplate.update("INSERT INTO user (user_id, username, password, status, type) VALUES (?,?,?,?,?)",
				new Object[] { user.getId().toString(), user.getUsername(), password.toString(), user.getStatus().getId() + "", user.getType().getId() + "" });
	}

	/**
	 * UPDATEs the user table with a new password.
	 * 
	 * @param userId
	 *            ID of user to update, required.
	 * @param password
	 *            Password to update to, required.
	 */
	@Override
	public int update(final UserId userId, final Password password) {
		AjahUtils.requireParam(userId, "userId");
		AjahUtils.requireParam(password, "password");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		return this.jdbcTemplate.update("UPDATE user SET password = ? WHERE user_id = ?", new Object[] { password.toString(), userId.toString() });
	}

}
