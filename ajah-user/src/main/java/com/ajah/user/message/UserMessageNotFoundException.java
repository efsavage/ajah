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
package com.ajah.user.message;

import lombok.EqualsAndHashCode;

/**
 * Thrown when a message is requested that does not exist.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@EqualsAndHashCode(callSuper = true)
public class UserMessageNotFoundException extends Exception {

	private final UserMessageId id;

	/**
	 * Thrown when a message is requested that does not exist.
	 * 
	 * @param id
	 *            ID that was sought.
	 */
	public UserMessageNotFoundException(final UserMessageId id) {
		super("UserMessage: " + id + " not found");
		this.id = id;
	}

}
