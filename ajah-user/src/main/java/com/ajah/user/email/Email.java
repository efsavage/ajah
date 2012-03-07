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
import com.ajah.util.Identifiable;
import com.ajah.util.data.format.EmailAddress;

/**
 * An email address, as associated with a user.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface Email extends Identifiable<EmailId> {

	/**
	 * Sets the unique ID of this email record.
	 * 
	 * @param emailId
	 *            The unique ID of this email record.
	 */
	@Override
	void setId(final EmailId emailId);

	/**
	 * Sets the ID of the user to whom this email belongs.
	 * 
	 * @param userId
	 *            The ID of the user to whom this email belongs.
	 */
	void setUserId(final UserId userId);

	/**
	 * Sets the email address.
	 * 
	 * @param address
	 *            The email address.
	 */
	void setAddress(final EmailAddress address);

	/**
	 * Sets the status of this Email.
	 * 
	 * @param emailStatus
	 *            The status of this Email.
	 */
	void setStatus(final EmailStatus emailStatus);

	/**
	 * Gets the ID of the user to whom this email belongs.
	 * 
	 * @return userId The ID of the user to whom this email belongs.
	 */
	UserId getUserId();

	/**
	 * Gets the email address.
	 * 
	 * @return The email address.
	 */
	EmailAddress getAddress();

	/**
	 * Returns the status of this Email.
	 * 
	 * @return The status of this Email.
	 */
	EmailStatus getStatus();

}
