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

import java.util.Date;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.crypto.Password;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.AuthenicationFailureException;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
import com.ajah.user.UserNotFoundException;
import com.ajah.user.UserStatus;
import com.ajah.user.UserType;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailImpl;
import com.ajah.user.email.EmailStatusImpl;
import com.ajah.user.email.data.EmailDao;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserInfoImpl;
import com.ajah.user.info.UserSource;
import com.ajah.util.AjahUtils;
import com.ajah.util.Validate;
import com.ajah.util.data.format.EmailAddress;

/**
 * Persistence manager for Users.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log
public class UserManager {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private EmailDao emailDao;

	/**
	 * Changes a user's password.
	 * 
	 * @param userId
	 *            The ID of the user to update.
	 * @param password
	 *            The new password.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public void changePassword(final UserId userId, final Password password) throws DataOperationException {
		this.userDao.update(userId, password);
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
	 * @throws DataOperationException
	 *             If the queries could not be completed.
	 */
	public User createUser(final EmailAddress emailAddress, final Password password, final String ip, final UserSource source, final UserType type) throws DataOperationException {
		final User user = new UserImpl();
		user.setId(new UserId(UUID.randomUUID().toString()));
		user.setUsername(emailAddress.toString());
		user.setStatus(UserStatus.NEW);
		user.setType(UserType.NORMAL);
		this.userDao.insert(user, password);

		final Email email = new EmailImpl();
		email.setId(new EmailId(UUID.randomUUID().toString()));
		email.setUserId(user.getId());
		email.setAddress(emailAddress);
		email.setStatus(EmailStatusImpl.ACTIVE);
		this.emailDao.insert(email);

		final UserInfo userInfo = new UserInfoImpl(user.getId());
		userInfo.setPrimaryEmailId(email.getId());
		userInfo.setCreated(new Date());
		this.userInfoDao.insert(userInfo);
		return user;
	}

	/**
	 * Finds a user by email.
	 * 
	 * @param address
	 *            Email, as entered by user.
	 * @return User, if found.
	 * @throws UserNotFoundException
	 *             If user is not found.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User findUserByEmail(final String address) throws UserNotFoundException, DataOperationException {
		final Email email = this.emailDao.findByAddress(address);
		if (email != null) {
			log.fine("Found email " + email.getAddress());
			final User user = this.userDao.load(email.getUserId());
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
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User findUserByUsername(final String username) throws UserNotFoundException, DataOperationException {
		final User user = this.userDao.findByUsername(username);
		if (user != null) {
			log.fine("Found user by username: " + user.getUsername());
			return user;
		}
		throw new UserNotFoundException(username);
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
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User findUserByUsernameOrEmail(final String usernameOrEmail) throws UserNotFoundException, DataOperationException {
		if (Validate.isEmail(usernameOrEmail)) {
			return findUserByEmail(usernameOrEmail);
		}
		return findUserByUsername(usernameOrEmail);
	}

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
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User getUser(final String username, final Password password) throws AuthenicationFailureException, UserNotFoundException, DataOperationException {
		AjahUtils.requireParam(username, "username");
		final User user = this.userDao.findByUsernameAndPassword(username, password.toString());
		if (user != null) {
			log.fine("getUser successful");
			return user;
		}
		log.fine("getUser failed");

		throw new AuthenicationFailureException(username + " authentication failed");
	}

	/**
	 * Find a user info object if possible, otherwise create one.
	 * 
	 * @param userId
	 *            User ID of the user that we want the info about. *
	 * @return UserInfo from the database if possible, otherwise a new/empty one
	 *         will be returned with the UserId set.
	 * @throws DataOperationException
	 */
	public UserInfo getUserInfo(final UserId userId) throws DataOperationException {
		final UserInfo userInfo = this.userInfoDao.load(userId);
		if (userInfo != null) {
			return userInfo;
		}
		return new UserInfoImpl(userId);
	}

	/**
	 * Is this username already in use?
	 * 
	 * @param username
	 * @return true if the username exists, otherwise false.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public boolean usernameExists(final String username) throws DataOperationException {
		return this.userDao.findByUsername(username) != null;
	}

	/**
	 * Loads a user by unique ID.
	 * 
	 * @param userId
	 *            The ID of the user, required.
	 * @return The matching user, will not be null.
	 * @throws UserNotFoundException
	 *             If the user could not be found.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User load(UserId userId) throws UserNotFoundException, DataOperationException {
		User user = this.userDao.load(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		return user;
	}

}
