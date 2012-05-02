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
package com.ajah.user.login;

import java.util.Date;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.crypto.Crypto;
import com.ajah.crypto.HmacSha1Password;
import com.ajah.crypto.Password;
import com.ajah.spring.jdbc.DatabaseAccessException;
import com.ajah.user.AuthenicationFailureException;
import com.ajah.user.User;
import com.ajah.user.UserNotFoundException;
import com.ajah.user.data.UserManager;

/**
 * Manages creation of logins.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Transactional(rollbackFor = Exception.class)
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
	 */
	public static String getTokenValue(final User user, final Password password) {
		return Crypto.toAES(user.getUsername() + "|" + password.toString());
	}

	@Autowired
	private UserManager userManager;

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
	 * @throws DatabaseAccessException
	 *             If the query could not be executed.
	 */
	public LogIn login(final String username, final Password password, final String ip, final LogInSource source, final LogInType type) throws DatabaseAccessException {
		log.fine("Login by user/pass attempt for: " + username);
		final LogIn login = new LogIn();
		login.setIp(ip);
		login.setCreated(new Date());
		login.setSource(source);
		try {
			final User user = this.userManager.getUser(username, password);
			login.setUser(user);
			login.setUsername(username);
			login.setStatus(LogInStatus.SUCCESS);
			log.fine("User " + user.getUsername() + " logged in");
		} catch (final RuntimeException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			login.setStatus(LogInStatus.ABORT);
		} catch (final AuthenicationFailureException e) {
			log.log(Level.INFO, e.getMessage());
			login.setUsername(e.getUsername());
			login.setStatus(LogInStatus.FAIL);
		} catch (final UserNotFoundException e) {
			log.log(Level.INFO, e.getMessage());
			login.setStatus(LogInStatus.FAIL);
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
	 * @throws DatabaseAccessException
	 *             If the query could not be executed.
	 */
	public LogIn loginByToken(final String token, final String ip, final LogInSource source, final LogInType type) throws DatabaseAccessException {
		log.fine("Login by token attempt for: " + token);
		final String decrypted = Crypto.fromAES(token);
		log.fine("token contents: " + decrypted);
		final String username = decrypted.split("\\|")[0];
		final Password password = new HmacSha1Password(decrypted.split("\\|")[1], true);
		return login(username, password, ip, source, type);
	}
}
