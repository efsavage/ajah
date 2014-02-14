/*  Copyright 2011 Eric F. Savage, code@efsavage.com
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

import com.ajah.crypto.Password;
import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserStatus;
import com.ajah.user.UserType;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface UserDao extends AjahDao<UserId, User> {

	/**
	 * Inserts a User into the database.
	 * 
	 * @param user
	 *            The user to insert.
	 * @param password
	 *            The user's password.
	 * @return The number of rows inserted.
	 * @throws DataOperationException
	 *             If the record could not be inserted.
	 */
	public int insert(final User user, final Password password) throws DataOperationException;

	/**
	 * Updates a user's password in the database.
	 * 
	 * @param userId
	 *            The ID of the user to update.
	 * @param password
	 *            The user's new password.
	 * @return The number of rows inserted.
	 * @throws DataOperationException
	 *             If the record could not be inserted.
	 */
	public int update(final UserId userId, final Password password) throws DataOperationException;

	/**
	 * Finds a user by username field.
	 * 
	 * @param username
	 *            The username to query
	 * @return The user, if found, otherwise null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User findByUsername(final String username) throws DataOperationException;

	/**
	 * Finds a user by username and password.
	 * 
	 * @param username
	 *            The username to query.
	 * @param password
	 *            The (hashed) password to query.
	 * @return The user, if found, otherwise null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User findByUsernameAndPassword(final String username, final String password) throws DataOperationException;

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The user type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	long count(final UserType type, final UserStatus status) throws DataOperationException;

}
