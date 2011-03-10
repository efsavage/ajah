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

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.user.AuthenicationFailureException;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserNotFoundException;
import com.ajah.user.info.UserInfo;
import com.ajah.util.crypto.Crypto;

/**
 * Persistence manager for Users.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
public class UserManager {

	private static final Logger log = Logger.getLogger(UserManager.class.getName());

	@Autowired
	private UserDao userDao;

	/**
	 * Attempt to find a user and authenticate.
	 * 
	 * @param username
	 *            A valid username for a user.
	 * @param password
	 *            The user's password, unencrypted.
	 * @return User if found and authenticated correctly, will never return
	 *         null.
	 * @throws AuthenicationFailureException
	 *             If the password was not correct
	 * @throws UserNotFoundException
	 *             If no user could be found for the username supplied
	 */
	public User getUser(String username, String password) throws AuthenicationFailureException, UserNotFoundException {
		User user = this.userDao.findUserByUsername(username, Crypto.getHmacSha1Hex(password));
		if (user != null) {
			log.fine("getUser successful");
			return user;
		}
		throw new AuthenicationFailureException(username);
	}

	/**
	 * Find a user info object.
	 * 
	 * @param userId
	 *            User ID of the user that we want the info about. *
	 * @return User if found and authenticated correctly, will never return
	 *         null.
	 * @throws UserNotFoundException
	 *             If no user could be found for the username supplied
	 */
	public UserInfo getUserInfo(UserId userId) throws UserNotFoundException {
		UserInfo userInfo = this.userDao.findUserInfo(userId);
		return userInfo;
	}

}