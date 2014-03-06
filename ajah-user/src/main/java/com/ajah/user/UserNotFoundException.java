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
package com.ajah.user;

import lombok.EqualsAndHashCode;

import com.ajah.util.data.format.EmailAddress;

/**
 * Thrown when a user is requested that does not exist.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@EqualsAndHashCode(callSuper = false)
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -1591254351139322758L;
	private final String username;
	private final UserId userId;

	/**
	 * Thrown when a user is requested by a username that does not exist.
	 * 
	 * @param username
	 *            Username that was sought.
	 */
	public UserNotFoundException(final String username) {
		super("user: " + username + " not found");
		this.username = username;
		this.userId = null;
	}

	/**
	 * Thrown when a user is requested by an ID that does not exist.
	 * 
	 * @param userId
	 *            User ID that was sought.
	 */
	public UserNotFoundException(final UserId userId) {
		super("id: " + userId + " not found");
		this.userId = userId;
		this.username = null;
	}

	/**
	 * Thrown when a user is requested by an email that does not exist.
	 * 
	 * @param emailAddress
	 *            Email address that was sought.
	 */
	public UserNotFoundException(EmailAddress emailAddress) {
		super("EMAIL: " + (emailAddress == null ? null : emailAddress.toString()));
		this.username = emailAddress == null ? null : emailAddress.toString();
		this.userId = null;
	}

}
