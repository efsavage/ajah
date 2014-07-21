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
 * Thrown when a requested action is not permitted in the user's location.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeographicRestrictionException extends Exception {

	private boolean current;
	private boolean home;

	/**
	 * Public constructor.
	 * 
	 * @param current
	 *            Is the restriction based on the user's current location?
	 * @param home
	 *            Is the restriction based on the user/account's home
	 *            location/residence?
	 * 
	 * @param message
	 *            The message explaining the error.
	 */
	public GeographicRestrictionException(final boolean current, final boolean home, final String message) {
		super(message);
		this.current = current;
		this.home = home;
	}

}
