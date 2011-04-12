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

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.user.AuthenicationFailureException;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
import com.ajah.user.UserNotFoundException;
import com.ajah.user.UserStatusImpl;
import com.ajah.user.UserType;
import com.ajah.user.UserTypeImpl;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailImpl;
import com.ajah.user.email.EmailStatusImpl;
import com.ajah.user.email.data.EmailDao;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserInfoImpl;
import com.ajah.user.info.UserSource;
import com.ajah.util.Validate;
import com.ajah.util.crypto.Password;
import com.ajah.util.data.format.EmailAddress;

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

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private EmailDao emailDao;

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
	public User getUser(String username, Password password) throws AuthenicationFailureException, UserNotFoundException {
		User user = this.userDao.findUserByUsername(username, password.toString());
		if (user != null) {
			log.fine("getUser successful");
			return user;
		}
		throw new AuthenicationFailureException(username);
	}

	/**
	 * Find a user info object if possible, otherwise create one.
	 * 
	 * @param userId
	 *            User ID of the user that we want the info about. *
	 * @return UserInfo from the database if possible, otherwise a new/empty one
	 *         will be returned with the UserId set.
	 */
	public UserInfo getUserInfo(UserId userId) {
		UserInfo userInfo = this.userInfoDao.findById(userId);
		if (userInfo != null) {
			return userInfo;
		}
		return new UserInfoImpl(userId);
	}

	/**
	 * Creates a new user.
	 * 
	 * @param emailAddress
	 *            Email address, will be used as provisional username, required.
	 * @param password
	 *            Password, required.
	 * @param ip
	 *            IP of the signup
	 * @param source
	 *            source of the signup
	 * @param type
	 *            Type of user to create, required.
	 * @return New user, if save was successful.
	 */
	@Transactional
	public User createUser(EmailAddress emailAddress, Password password, String ip, UserSource source, UserType type) {
		User user = new UserImpl();
		user.setId(new UserId(UUID.randomUUID().toString()));
		user.setUsername(emailAddress.toString());
		user.setStatus(UserStatusImpl.NEW);
		user.setType(UserTypeImpl.NORMAL);
		this.userDao.insert(user, password);

		Email email = new EmailImpl();
		email.setId(new EmailId(UUID.randomUUID().toString()));
		email.setUserId(user.getId());
		email.setAddress(emailAddress);
		email.setStatus(EmailStatusImpl.ACTIVE);
		this.emailDao.insert(email);

		UserInfo userInfo = new UserInfoImpl(user.getId());
		userInfo.setPrimaryEmailId(email.getId());
		this.userInfoDao.insert(userInfo);
		return user;
	}

	/**
	 * Finds a user by username or email. Tries by email if the parameter is a
	 * valid address, otherwise tries by username.
	 * 
	 * @param usernameOrEmail
	 *            Username or email, as entered by user.
	 * @return User, if found.
	 * @throws UserNotFoundException
	 *             If user is not found.
	 */
	public User findUserByUsernameOrEmail(String usernameOrEmail) throws UserNotFoundException {
		if (Validate.isEmail(usernameOrEmail)) {
			return findUserByEmail(usernameOrEmail);
		}
		return findUserByUsername(usernameOrEmail);
	}

	/**
	 * Finds a user by email.
	 * 
	 * @param address
	 *            Email, as entered by user.
	 * @return User, if found.
	 * @throws UserNotFoundException
	 *             If user is not found.
	 */
	public User findUserByEmail(String address) throws UserNotFoundException {
		Email email = this.emailDao.findEmailByAddress(address);
		if (email != null) {
			log.fine("Found email " + email.getAddress());
			User user = this.userDao.findById(email.getUserId());
			if (user != null) {
				log.fine("Found user by email: " + user.getUsername());
				return user;
			}
		}
		throw new UserNotFoundException(address);
	}

	/**
	 * Finds a user by username.
	 * 
	 * @param username
	 *            Username as entered by user.
	 * @return User, if found.
	 * @throws UserNotFoundException
	 *             If user is not found.
	 */
	public User findUserByUsername(String username) throws UserNotFoundException {
		User user = this.userDao.findUserByUsername(username);
		if (user != null) {
			log.fine("Found user by username: " + user.getUsername());
			return user;
		}
		throw new UserNotFoundException(username);
	}

	/**
	 * @param userId
	 * @param password
	 */
	public void changePassword(UserId userId, Password password) {
		this.userDao.update(userId, password);
	}

}