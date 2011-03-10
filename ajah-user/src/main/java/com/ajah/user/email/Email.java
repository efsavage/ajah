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
package com.ajah.user.email;

import com.ajah.user.UserId;

/**
 * An email address, as associated with a user.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface Email {

	/**
	 * Sets the unique ID of this email record.
	 * 
	 * @param emailId
	 *            The unique ID of this email record.
	 */
	void setId(EmailId emailId);

	/**
	 * Sets the ID of the user to whom this email belongs.
	 * 
	 * @param userId
	 *            The ID of the user to whom this email belongs.
	 */
	void setUserId(UserId userId);

	/**
	 * Sets the email address.
	 * 
	 * @param address
	 *            The email address.
	 */
	void setAddress(String address);

	/**
	 * Sets the status of this Email.
	 * 
	 * @param emailStatus
	 *            The status of this Email.
	 */
	void setStatus(EmailStatus emailStatus);

}