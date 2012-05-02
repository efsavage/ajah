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
package com.ajah.user.email.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.spring.jdbc.DatabaseAccessException;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailNotFoundException;
import com.ajah.util.AjahUtils;

/**
 * Manages persistence of {@link Email} entities.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmailManager {

	@Autowired
	private EmailDao emailDao;

	/**
	 * Find an email by it's unique ID.
	 * 
	 * @param emailId
	 *            The ID to look for, required.
	 * @return The Email, if found.
	 * @throws EmailNotFoundException
	 *             If no Email is found.
	 * @throws DatabaseAccessException
	 */
	public Email getEmail(final EmailId emailId) throws EmailNotFoundException, DatabaseAccessException {
		AjahUtils.requireParam(emailId, "emailId");
		final Email email = this.emailDao.findById(emailId);
		if (email == null) {
			throw new EmailNotFoundException(emailId);
		}
		return email;
	}

}
