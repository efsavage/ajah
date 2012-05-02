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
package com.ajah.user.signup;

import java.util.Date;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.crypto.Password;
import com.ajah.spring.jdbc.DatabaseAccessException;
import com.ajah.user.User;
import com.ajah.user.UserType;
import com.ajah.user.data.UserManager;
import com.ajah.user.info.UserSource;
import com.ajah.util.data.format.EmailAddress;

/**
 * Manages creation of signUps.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class SignUpManager {

	@Autowired
	private UserManager userManager;

	/**
	 * Returns a signUp record for a user.
	 * 
	 * @param emailAddress
	 *            Username or email of the user logging in.
	 * @param password
	 *            Password of the user logging in, unencrypted.
	 * @param ip
	 *            IP of requesting user
	 * @param source
	 *            Source of signUp attempt
	 * @param type
	 *            Type of signUp attempt
	 * @return SignUp record, will never return null.
	 * @throws DatabaseAccessException
	 *             If the queries could not be completed.
	 */
	public SignUp signUp(final EmailAddress emailAddress, final Password password, final String ip, final UserSource source, final UserType type) throws DatabaseAccessException {
		log.fine("SignUp attempt for: " + emailAddress);
		final SignUp signUp = new SignUp();
		signUp.setIp(ip);
		signUp.setCreated(new Date());
		signUp.setSource(source);
		signUp.setStatus(SignUpStatus.SUCCESS);
		// TODO signup should be saved
		final User user = this.userManager.createUser(emailAddress, password, ip, source, type);
		log.info(user.getUsername() + " created!");
		// try {
		// } catch (RuntimeException e) {
		// log.log(Level.SEVERE, e.getMessage(), e);
		// signUp.setStatus(SignUpStatus.ABORT);
		// } catch (AuthenicationFailureException e) {
		// log.log(Level.INFO, e.getMessage());
		// signUp.setUsername(e.getUsername());
		// signUp.setStatus(SignUpStatus.FAIL);
		// } catch (UserNotFoundException e) {
		// log.log(Level.INFO, e.getMessage());
		// signUp.setStatus(SignUpStatus.FAIL);
		// }
		return signUp;
	}

}
