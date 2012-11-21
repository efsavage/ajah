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
import org.springframework.transaction.annotation.Transactional;

import com.ajah.crypto.Password;
import com.ajah.spring.jdbc.err.DataOperationException;
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
@Transactional(rollbackFor = Exception.class)
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
	 * @throws DataOperationException
	 *             If the queries could not be completed.
	 * @throws UsernameExistsException
	 *             If a user with that username already exists.
	 */
	public SignUp signUp(final EmailAddress emailAddress, final Password password, final String ip, final UserSource source, final UserType type) throws DataOperationException,
			UsernameExistsException {
		log.fine("SignUp attempt for: " + emailAddress);
		final SignUp signUp = new SignUp();
		signUp.setIp(ip);
		signUp.setCreated(new Date());
		signUp.setSource(source);
		signUp.setStatus(SignUpStatus.SUCCESS);
		// TODO signup should be saved
		if (this.userManager.usernameExists(emailAddress.toString())) {
			log.fine(emailAddress.toString() + " is in use");
			throw new UsernameExistsException(emailAddress.toString());
		}

		final User user = this.userManager.createUser(emailAddress, password, ip, source, type);
		log.info(user.getUsername() + " created!");
		return signUp;
	}

}
