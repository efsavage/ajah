/*
 *  Copyright 2013-2014 Eric F. Savage, code@efsavage.com
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
 * Thrown when a required paramter was not found on the request.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequiredParameterException extends Exception {

	private String name;

	/**
	 * Public constructor.
	 * 
	 * @param name
	 *            the Name of the missing paramter.
	 */
	public RequiredParameterException(String name) {
		super("Paramter '" + name + "' is required.");
		this.name = name;
	}

}
