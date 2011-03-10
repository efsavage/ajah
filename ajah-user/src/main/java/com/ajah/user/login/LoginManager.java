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
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class LoginManager {

	private static final Logger log = Logger.getLogger(LoginManager.class.getName());

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
	 */
	public Login login(String username, String password, String ip, LoginSource source, LoginType type) {
		log.fine("Login attempt for: " + username);
		Login login = new Login();
		login.setIp(ip);
		login.setCreated(new Date());
		login.setSource(source);
		try {
			User user = this.userManager.getUser(username, password);
			login.setUser(user);
			login.setUsername(username);
			login.setStatus(LoginStatus.SUCCESS);
			log.fine("User " + user.getUsername() + " logged in");
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			login.setStatus(LoginStatus.ABORT);
		} catch (AuthenicationFailureException e) {
			log.log(Level.INFO, e.getMessage());
			login.setUsername(e.getUsername());
			login.setStatus(LoginStatus.FAIL);
		} catch (UserNotFoundException e) {
			log.log(Level.INFO, e.getMessage());
			login.setStatus(LoginStatus.FAIL);
		}
		return login;
	}

}