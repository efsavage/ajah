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

import java.text.DateFormat;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Thrown when a user is not old enough to perform an age-restricted operation.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AgeRequirementNotMetException extends Exception {

	private String username;

	/**
	 * Public constructor.
	 * 
	 * @param maxBirthDate
	 *            The latest birthdate permitted.
	 */
	public AgeRequirementNotMetException(final Date maxBirthDate) {
		super("Must be born on or before " + DateFormat.getDateInstance(DateFormat.SHORT).format(maxBirthDate));
	}

}
