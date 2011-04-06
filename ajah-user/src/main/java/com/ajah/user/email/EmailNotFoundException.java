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

/**
 * Thrown when no persisted {@link Email} could be found.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class EmailNotFoundException extends Exception {

	private static final long serialVersionUID = 6056888345467361434L;

	/**
	 * @param emailId
	 *            The ID of the email that was sought.
	 * @see Exception#Exception(String)
	 */
	public EmailNotFoundException(EmailId emailId) {
		super(emailId.getId());
	}

	/**
	 * @see Exception#Exception()
	 */
	public EmailNotFoundException() {
		super();
	}

}