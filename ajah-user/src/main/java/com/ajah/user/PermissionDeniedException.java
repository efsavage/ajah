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
package com.ajah.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Thrown when a resource is denied to the current user.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionDeniedException extends Exception {

	/**
	 * @see Exception#Exception()
	 */
	public PermissionDeniedException() {
		super();
	}

	/**
	 * @see Exception#Exception(String)
	 * @param message
	 *            The error message
	 */
	public PermissionDeniedException(final String message) {
		super(message);
	}

}
