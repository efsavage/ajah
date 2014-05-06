/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.rest.api.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Thrown when a requested email already exists, most likely during a signup
 * process.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmailExistsException extends Exception {

	private String email;

	/**
	 * Public constructor.
	 * 
	 * @param email
	 *            The email requested.
	 * @param message
	 *            The message explaining why it was invalid.
	 */
	public EmailExistsException(final String email, final String message) {
		super(message);
		this.email = email;
	}

}
