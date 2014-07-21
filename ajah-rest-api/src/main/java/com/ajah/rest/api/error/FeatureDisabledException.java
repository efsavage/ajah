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
 * Thrown when a feature that is being access has been disabled.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeatureDisabledException extends Exception {

	private String name;
	private boolean temporary;

	/**
	 * Public constructor.
	 * 
	 * @param name
	 *            the Name of the missing parameter.
	 * @param temporary
	 *            Is this only temporarily disabled?
	 */
	public FeatureDisabledException(final String name, final boolean temporary) {
		super("Parameter '" + name + "' is " + (temporary ? "temporarily unavailable." : "no longer available."));
		this.name = name;
		this.temporary = temporary;
	}

}
