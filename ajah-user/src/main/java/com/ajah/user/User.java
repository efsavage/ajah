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
package com.ajah.user;

/**
 * A User corresponds to a person who is using the system. A person should not
 * need to have separate users within the application.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface User {

	/**
	 * Unique ID of the user.
	 * 
	 * @return Unique ID of the user, may be null.
	 */
	UserId getId();

	/**
	 * The status of the user.
	 * 
	 * @return The status of the user. May be null if not saved/complete.
	 */
	UserStatus getStatus();

	/**
	 * The type of the user.
	 * 
	 * @return The type of the user. May be null if not saved/complete.
	 */
	UserType getType();

	/**
	 * Unique username of the user.
	 * 
	 * @return Unique ID of the user, may be null.
	 */
	String getUsername();

	/**
	 * Sets the user ID.
	 * 
	 * @param userId
	 *            The User ID, should not be null.
	 */
	void setId(UserId userId);

	/**
	 * Sets the user status.
	 * 
	 * @param status
	 *            The user's status, should not be null.
	 */
	void setStatus(UserStatus status);

	/**
	 * Sets the user's type.
	 * 
	 * @param type
	 *            The user's type, should not be null.
	 */
	void setType(UserType type);

	/**
	 * Set the username.
	 * 
	 * @param username
	 *            Should not be null.
	 */
	void setUsername(String username);

}