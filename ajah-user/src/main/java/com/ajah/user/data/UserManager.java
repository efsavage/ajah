/*
 *  Copyright 2011-2015 Eric F. Savage, code@efsavage.com
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.crypto.CryptoException;
import com.ajah.crypto.Password;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.AuthenticationFailureException;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserImpl;
import com.ajah.user.UserNotFoundException;
import com.ajah.user.UserStatus;
import com.ajah.user.UserStatusReason;
import com.ajah.user.UserType;
import com.ajah.user.audit.UserAuditField;
import com.ajah.user.audit.UserAuditType;
import com.ajah.user.audit.data.UserAuditManager;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailStatus;
import com.ajah.user.email.EmailType;
import com.ajah.user.email.data.EmailManager;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserInfoImpl;
import com.ajah.user.info.UserSourceId;
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
	private UserAuditManager userAuditManager;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	EmailManager emailManager;

	/**
	 * Activates a user for the specified reason.
	 * 
	 * @param user
	 *            The user to deactivate.
	 * @param statusReason
	 *            The reason for deactivation.
	 * @param type
	 * @param ip
	 * @param headers
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 * @throws UserNotFoundException
	 *             If the specified user was not found.
	 */
	public void activate(final User user, final UserId staffUserId, final UserStatusReason statusReason, final UserAuditType type, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException, UserNotFoundException {
		final UserStatus oldStatus = this.userDao.getStatus(user.getId());
		user.setStatus(UserStatus.ACTIVE);
		user.setStatusReason(statusReason);
		this.userAuditManager.create(user.getId(), staffUserId, UserAuditField.STATUS, oldStatus.getId(), UserStatus.ACTIVE.getId(), type, userComment, staffComment, ip, headers);
		this.userDao.update(user);
	}

	/**
	 * Blocks a user for the specified reason.
	 * 
	 * @param user
	 *            The user to block.
	 * @param statusReason
	 *            The reason for blocking.
	 * @param type
	 * @param ip
	 * @param headers
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 * @throws UserNotFoundException
	 *             If the specified user was not found.
	 */
	public void block(final User user, final UserId staffUserId, final UserStatusReason statusReason, final UserAuditType type, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException, UserNotFoundException {
		final UserStatus oldStatus = this.userDao.getStatus(user.getId());
		user.setStatus(UserStatus.BLOCKED);
		user.setStatusReason(statusReason);
		this.userAuditManager.create(user.getId(), staffUserId, UserAuditField.STATUS, oldStatus.getId(), UserStatus.BLOCKED.getId(), type, userComment, staffComment, ip, headers);
		this.userDao.update(user);
	}

	/**
	 * Changes a user's password.
	 * 
	 * @param userId
	 *            The ID of the user to update.
	 * @param password
	 *            The new password.
	 * @param type
	 *            The type of change this is.
	 * @param ip
	 * @param headers
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws CryptoException
	 *             If there was a problem hashing the password.
	 */
	public void changePassword(final UserId userId, final UserId staffUserId, final Password password, final UserAuditType type, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException, CryptoException {
		final Password oldPassword = this.userDao.getPassword(userId);
		this.userDao.updatePassword(userId, password);
		this.userAuditManager.create(userId, staffUserId, UserAuditField.PASSWORD, oldPassword.toString(), password.toString(), type, userComment, staffComment, ip, headers);
	}

	/**
	 * Changes a user's username.
	 * 
	 * @param userId
	 *            The ID of the user to update.
	 * @param username
	 *            The new username.
	 * @param type
	 *            The type of change this is.
	 * @param ip
	 * @param headers
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public void changeUsername(final UserId userId, final UserId staffUserId, final String username, final UserAuditType type, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException {
		final String oldUsername = this.userDao.getUsername(userId);
		this.userDao.updateUsername(userId, username);
		this.userAuditManager.create(userId, staffUserId, UserAuditField.USERNAME, oldUsername, username, type, userComment, staffComment, ip, headers);
	}

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count() throws DataOperationException {
		return count(null, null);
	}

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
	public int count(final UserType type, final UserStatus status) throws DataOperationException {
		return this.userDao.count(type, status);
	}

	/**
	 * Creates a new user.
	 * 
	 * @param username
	 *            The username, used to login. May be the same as email address
	 *            for some applications.
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
	public User createUser(final String username, final EmailAddress emailAddress, final Password password, final String ip, final UserSourceId source, final UserType type)
			throws DataOperationException {
		final User user = new UserImpl();
		user.setId(new UserId(UUID.randomUUID().toString()));
		user.setUsername(username);
		user.setStatus(UserStatus.NEW);
		user.setType(UserType.NORMAL);
		this.userDao.insert(user, password);

		final Email email = this.emailManager.create(user.getId(), emailAddress.toString(), EmailType.STANDARD, EmailStatus.ACTIVE).getEntity();

		final UserInfo userInfo = new UserInfoImpl(user.getId());
		userInfo.setPrimaryEmailId(email.getId());
		userInfo.setCreated(new Date());
		this.userInfoDao.insert(userInfo);
		return user;
	}

	/**
	 * Deactivates a user for the specified reason.
	 * 
	 * @param user
	 *            The user to deactivate.
	 * @param statusReason
	 *            The reason for deactivation.
	 * @param type
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 * @throws UserNotFoundException
	 *             If the specified user was not found.
	 */
	public void deactivate(final User user, final UserId staffUserId, final UserStatusReason statusReason, final UserAuditType type, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException, UserNotFoundException {
		final UserStatus oldStatus = this.userDao.getStatus(user.getId());
		user.setStatus(UserStatus.INACTIVE);
		user.setStatusReason(statusReason);
		this.userDao.update(user);
		log.info("User " + user.getUsername() + " is now status " + user.getStatus() + " (" + user.getStatusReason() + ")");
		this.userAuditManager.create(user.getId(), staffUserId, UserAuditField.STATUS, oldStatus.getId(), UserStatus.INACTIVE.getId(), type, userComment, staffComment, ip, headers);
		this.userDao.update(user);
	}

	/**
	 * Blocks a user for the specified reason.
	 * 
	 * @param user
	 *            The user to disable.
	 * @param statusReason
	 *            The reason for disabling.
	 * @param type
	 * @param string2
	 * @param string
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 * @throws UserNotFoundException
	 *             If the specified user was not found.
	 */
	public void disable(final User user, final UserId staffUserId, final UserStatusReason statusReason, final UserAuditType type, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException, UserNotFoundException {
		final UserStatus oldStatus = this.userDao.getStatus(user.getId());
		user.setStatus(UserStatus.DISABLED);
		user.setStatusReason(statusReason);
		this.userAuditManager.create(user.getId(), staffUserId, UserAuditField.STATUS, oldStatus.getId(), UserStatus.DISABLED.getId(), type, userComment, staffComment, ip, headers);
		this.userDao.update(user);
	}

	/**
	 * Finds a user by email.
	 * 
	 * @param emailAddress
	 *            The email address, as entered by user.
	 * @return User, if found.
	 * @throws UserNotFoundException
	 *             If user is not found.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User findUserByEmail(final EmailAddress emailAddress) throws UserNotFoundException, DataOperationException {
		final Email email = this.emailManager.find(emailAddress);
		if (email != null) {
			log.fine("Found email " + email.getAddress());
			final User user = this.userDao.load(email.getUserId());
			if (user != null) {
				log.fine("Found user by email: " + user.getUsername());
				return user;
			}
		}
		throw new UserNotFoundException(emailAddress);
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
			return findUserByEmail(new EmailAddress(usernameOrEmail));
		}
		return findUserByUsername(usernameOrEmail);
	}

	/**
	 * Returns a random active.
	 * 
	 * @return A random user, may be null if no users satisfy the criteria.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User getRandomUser() throws DataOperationException {
		return this.userDao.getRandomUser(UserStatus.ACTIVE);
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
	 * @throws AuthenticationFailureException
	 *             If the password was not correct
	 * @throws UserNotFoundException
	 *             If no user could be found for the username supplied
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User getUser(final String username, final Password password) throws AuthenticationFailureException, UserNotFoundException, DataOperationException {
		AjahUtils.requireParam(username, "username");
		final User user = this.userDao.findByUsernameAndPassword(username, password.toString());
		if (user != null) {
			log.fine("getUser successful");
			return user;
		}
		log.fine("getUser failed");

		throw new AuthenticationFailureException(username + " authentication failed");
	}

	/**
	 * Attempt to find a user and authenticate.
	 * 
	 * @param userId
	 *            The ID of the user to attempt to fetch.
	 * @param password
	 *            The user's password, unencrypted.
	 * @return User if found and authenticated correctly, will never return
	 *         null.
	 * @throws AuthenticationFailureException
	 *             If the password was not correct
	 * @throws UserNotFoundException
	 *             If no user could be found for the username supplied
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public User getUser(final UserId userId, final Password password) throws AuthenticationFailureException, UserNotFoundException, DataOperationException {
		AjahUtils.requireParam(userId, "userId");
		final User user = this.userDao.findByUserIdAndPassword(userId, password.toString());
		if (user != null) {
			log.fine("getUser successful");
			return user;
		}
		log.fine("getUser failed");

		throw new AuthenticationFailureException(userId + " authentication failed");
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

	public List<User> list(final int page, final int count) throws DataOperationException {
		return this.userDao.list("username", Order.ASC, page, count);
	}

	public List<User> list(final String username, final String firstName, final String lastName, final UserStatus status, final String sort, final Order order, final int page, final int count)
			throws DataOperationException {
		return this.userDao.list(username, firstName, lastName, status, sort, order, page, count);
	}

	public List<User> load(final List<UserId> userIds) throws UserNotFoundException, DataOperationException {
		// TODO Optimize this to a single query?
		final List<User> users = new ArrayList<User>();
		for (final UserId userId : userIds) {
			users.add(load(userId));
		}
		return users;
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
	public User load(final UserId userId) throws UserNotFoundException, DataOperationException {
		final User user = this.userDao.load(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		return user;
	}

	public DataOperationResult<UserInfo> save(final UserInfo userInfo) throws DataOperationException {
		if (userInfo.getCreated() == null) {
			userInfo.setCreated(new Date());
		}
		final DataOperationResult<UserInfo> result = this.userInfoDao.update(userInfo);
		return result;
	}

	public int searchCount(final String username, final String firstName, final String lastName, final UserStatus status) throws DataOperationException {
		return this.userDao.searchCount(username, firstName, lastName, status);
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
	 * Converts a user to a test user. Only users that are
	 * {@link UserType#NORMAL} can be converted, other types must be converted
	 * to normal first.
	 * 
	 * @param user
	 *            The user to convert.
	 * @param type
	 *            The type of audit to record.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public void convertToTest(User user, final UserId staffUserId, UserAuditType type, String userComment, String staffComment, String ip, String headers) throws DataOperationException {
		if (user.getType() == UserType.NORMAL) {
			this.userAuditManager.create(user.getId(), staffUserId, UserAuditField.TYPE, user.getType().getId(), UserType.TEST.getId(), type, userComment, staffComment, ip, headers);
			user.setType(UserType.TEST);
			this.userDao.update(user);
		}
	}

	public List<UserInfo> listBySource(String source, int page, int count) throws DataOperationException {
		return this.userInfoDao.listBySource(source, page, count);
	}

}
