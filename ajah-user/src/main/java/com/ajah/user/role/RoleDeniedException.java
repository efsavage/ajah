/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.role;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Thrown when an {@link Role} was expected to be found, but was not.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDeniedException extends Exception {

	private final String roleName;
	private final String resourceName;

	/**
	 * Thrown when an operation requires a role but that is not available to the
	 * current user.
	 * 
	 * @param roleName
	 *            The readable name of the role to use in an error message.
	 * @param resourceName
	 *            The name of the resource that access was denied on.
	 */
	public RoleDeniedException(final String roleName) {
		super("Role: " + roleName);
		this.roleName = roleName;
		this.resourceName = null;
	}

	/**
	 * Thrown when an operation requires a role but that is not available to the
	 * current user.
	 * 
	 * @param roleName
	 *            The readable name of the role to use in an error message.
	 * @param resourceName
	 *            The name of the resource that access was denied on.
	 */
	public RoleDeniedException(final String roleName, final String resourceName) {
		super("Role: " + roleName + " on " + resourceName);
		this.roleName = roleName;
		this.resourceName = resourceName;
	}

}
