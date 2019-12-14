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
package com.ajah.user.login.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.crypto.Crypto;
import com.ajah.crypto.CryptoException;
import com.ajah.crypto.HmacSha1Password;
import com.ajah.crypto.Password;
import com.ajah.lang.ConfigException;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.AuthenticationFailureException;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.UserNotFoundException;
import com.ajah.user.UserStatus;
import com.ajah.user.data.UserManager;
import com.ajah.user.login.LogIn;
import com.ajah.user.login.LogInId;
import com.ajah.user.login.LogInSource;
import com.ajah.user.login.LogInStatus;
import com.ajah.user.login.LogInType;

import lombok.extern.java.Log;

/**
 * Manages data operations for {@link LogIn}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class LogInManager {

	/**
	 * Returns token value for a user, that can be used to authenticate later.
	 * The actual scheme of token generation should be encapsulated fully here
	 * so it can be changed. Currently the only available scheme is to encrypt
	 * the username and password hash.
	 * 
	 * Note: Tokens should always expire when a user changes their password.
	 * 
	 * @param user
	 *            The user to generate a token for.
	 * @param password
	 *            Password object for user to include in the token.
	 * @return Token that can be used to authenticate.
	 * @throws ConfigException
	 *             If there is a cryptographic error.
	 */
	public static String getTokenValue(final User user, final Password password) {
		try {
			return Crypto.toAES(user.getUsername() + "|" + password.toString());
		} catch (final CryptoException e) {
			throw new ConfigException(e);
		}
	}

	@Autowired
	private LogInDao logInDao;

	@Autowired
	private UserManager userManager;

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count() throws DataOperationException {
		return count(null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The logIn type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final LogInType type, final LogInStatus status) throws DataOperationException {
		return this.logInDao.count(type, status);
	}

	/**
	 * Creates a new {@link LogIn} with the given properties.
	 * 
	 * @param name
	 *            The name of the logIn, required.
	 * @param type
	 *            The type of logIn, required.
	 * @param status
	 *            The status of the logIn, required.
	 * @return The result of the creation, which will include the new logIn at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<LogIn> create(final String name, final LogInType type, final LogInStatus status) throws DataOperationException {
		final LogIn logIn = new LogIn();
		logIn.setUsername(name);
		logIn.setType(type);
		logIn.setStatus(status);
		return save(logIn);
	}

	/**
	 * Returns a list of {@link LogIn}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of logIn, optional.
	 * @param status
	 *            The status of the logIn, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link LogIn}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<LogIn> list(final LogInType type, final LogInStatus status, final long page, final long count) throws DataOperationException {
		return this.logInDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link LogIn} by it's ID.
	 * 
	 * @param logInId
	 *            The ID to load, required.
	 * @return The matching logIn, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws LogInNotFoundException
	 *             If the ID specified did not match any logIns.
	 */
	public LogIn load(final LogInId logInId) throws DataOperationException, LogInNotFoundException {
		final LogIn logIn = this.logInDao.load(logInId);
		if (logIn == null) {
			throw new LogInNotFoundException(logInId);
		}
		return logIn;
	}

	/**
	 * Returns a login record for a user.
	 * 
	 * @param username
	 *            Username or email of the user logging in.
	 * @param password
	 *            Password of the user logging in, unencrypted.
	 * @param ip
	 *            IP of requesting user
	 * @param source
	 *            Source of login attempt
	 * @param type
	 *            Type of login attempt
	 * @return Login record, will never return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public LogIn login(final String username, final Password password, final String ip, final LogInSource source, final LogInType type) throws DataOperationException {
		log.fine("Login by user/pass attempt for: " + username);
		final LogIn login = new LogIn();
		login.setIp(ip);
		login.setCreated(new Date());
		login.setSource(source);
		login.setType(type);
		try {
			final User user = this.userManager.getUser(username, password);
			login.setUser(user);
			login.setUsername(username);
			login.setStatus(LogInStatus.SUCCESS);
			log.fine("User " + user.getUsername() + " logged in");
		} catch (final RuntimeException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			login.setStatus(LogInStatus.ABORT);
		} catch (final AuthenticationFailureException e) {
			log.log(Level.INFO, e.getMessage());
			login.setUsername(e.getUsername());
			login.setStatus(LogInStatus.FAIL);
		} catch (final UserNotFoundException e) {
			log.log(Level.INFO, e.getMessage());
			login.setStatus(LogInStatus.FAIL);
		}
		try {
			save(login);
		} catch (DataOperationException e) {
			// While this is a significant error, we don't want to fail the
			// whole login process on the save.
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		return login;
	}

	/**
	 * Returns a login record for a user.
	 * 
	 * @param userId
	 *            The ID of the user attempting to log in.
	 * @param password
	 *            Password of the user logging in, unencrypted.
	 * @param ip
	 *            IP of requesting user
	 * @param source
	 *            Source of login attempt
	 * @param type
	 *            Type of login attempt
	 * @return Login record, will never return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public LogIn login(final UserId userId, final Password password, final String ip, final LogInSource source, final LogInType type) throws DataOperationException {
		log.fine("Login by user/pass attempt for: " + userId);
		final LogIn login = new LogIn();
		login.setIp(ip);
		login.setCreated(new Date());
		login.setSource(source);
		login.setType(type);
		try {
			final User user = this.userManager.getUser(userId, password);
			login.setUser(user);
			login.setUsername(user.getUsername());
			if (user.getStatus() == UserStatus.ACTIVE || user.getStatus() == UserStatus.NEW) {
				login.setStatus(LogInStatus.SUCCESS);
			} else {
				log.warning("Failed login because user is status: " + user.getStatus());
				login.setStatus(LogInStatus.FAIL);
			}
			log.fine("User " + user.getUsername() + " logged in");
		} catch (final RuntimeException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			login.setStatus(LogInStatus.ABORT);
		} catch (final AuthenticationFailureException e) {
			log.log(Level.INFO, e.getMessage());
			login.setUsername(e.getUsername());
			login.setStatus(LogInStatus.FAIL);
		} catch (final UserNotFoundException e) {
			log.log(Level.INFO, e.getMessage());
			login.setStatus(LogInStatus.FAIL);
		}
		try {
			save(login);
		} catch (DataOperationException e) {
			// While this is a significant error, we don't want to fail the
			// whole login process on the save.
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		return login;
	}

	/**
	 * Logs a user in by token value. Common usage would be to store token in a
	 * cookie.
	 * 
	 * @param token
	 *            Token value
	 * @param ip
	 *            IP of requesting user
	 * @param source
	 *            Source of login attempt
	 * @param type
	 *            Type of login attempt
	 * @return Login record, will never be null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public LogIn loginByToken(final String token, final String ip, final LogInSource source, final LogInType type) throws DataOperationException {
		log.fine("Login by token attempt for: " + token);
		try {
			final String decrypted = Crypto.fromAES(token);
			log.fine("token contents: " + decrypted);
			final String username = decrypted.split("\\|")[0];
			final Password password = new HmacSha1Password(decrypted.split("\\|")[1], true);
			final LogIn logIn = login(username, password, ip, source, type);
			logIn.setToken(token);
			return logIn;
		} catch (final CryptoException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			final LogIn login = new LogIn();
			login.setIp(ip);
			login.setCreated(new Date());
			login.setSource(source);
			login.setStatus(LogInStatus.ERROR);
			login.setType(type);
			return login;
		}
	}

	/**
	 * Saves an {@link LogIn}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param logIn
	 *            The logIn to save.
	 * @return The result of the save operation, which will include the new
	 *         logIn at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<LogIn> save(final LogIn logIn) throws DataOperationException {
		boolean create = false;
		if (logIn.getId() == null) {
			logIn.setId(new LogInId(UUID.randomUUID().toString()));
			create = true;
		}
		if (logIn.getCreated() == null) {
			logIn.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<LogIn> result = this.logInDao.insert(logIn);
			log.fine("Created LogIn " + logIn.getUsername() + " [" + logIn.getId() + "]");
			return result;
		}
		final DataOperationResult<LogIn> result = this.logInDao.update(logIn);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated LogIn " + logIn.getUsername() + " [" + logIn.getId() + "]");
		}
		return result;
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(final String search) throws DataOperationException {
		return this.logInDao.searchCount(search);
	}

	public void retry(LogIn logIn) throws DataOperationException {
		logIn.setRetry(true);
		save(logIn);
	}

	public String getUsernameByToken(String token) throws CryptoException {
		final String decrypted = Crypto.fromAES(token);
		log.fine("token contents: " + decrypted);
		return decrypted.split("\\|")[0];
	}

}
