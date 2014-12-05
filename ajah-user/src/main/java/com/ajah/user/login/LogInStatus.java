/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.login;

import com.ajah.util.IdentifiableEnum;

/**
 * Valid states of {@link LogIn} entities.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum LogInStatus implements IdentifiableEnum<String> {

	/**
	 * Login was aborted before it could succeed or fail.
	 */
	ABORT("0", "abort", "Aborted", "Aborted.", false, false, false),
	/**
	 * Success.
	 */
	SUCCESS("1", "success", "Successful", "Successful.", true, false, false),
	/**
	 * Error.
	 */
	ERROR("-2", "error", "Error", "Error.", false, true, false),
	/**
	 * Failed.
	 */
	FAIL("-1", "fail", "Failed", "Failed.", false, false, true);

	/**
	 * Finds a LogInStatus that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching LogInStatus, or null.
	 */
	public static LogInStatus get(final String string) {
		for (final LogInStatus type : values()) {
			if (type.getId().equals(string) || type.getCode().equals(string) || type.name().equals(string) || type.getName().equals(string)) {
				return type;
			}
		}
		return null;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String description;
	private final boolean success;
	private final boolean error;
	private final boolean failed;

	private LogInStatus(final String id, final String code, final String name, final String description, final boolean success, final boolean error, final boolean failed) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.success = success;
		this.error = error;
		this.failed = failed;
	}

	/**
	 * The short, display-friendly code of the type. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the type. Cannot be null.
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * The display-friendly description of the type.
	 * 
	 * @return The display-friendly description of the type. May be null.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * The internal ID of the type.
	 * 
	 * @return The internal ID of the type. Cannot be null.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The display-friendly name of the type. If no name is applicable, it
	 * should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the type. Cannot be null.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Does this status mean that the entity is in an error state?
	 * 
	 * @return true if in error state, otherwise false
	 */
	public boolean isError() {
		return this.error;
	}

	/**
	 * Does this status mean that the entity has been failed?
	 * 
	 * @return true if failed, otherwise false
	 */
	public boolean isFailed() {
		return this.failed;
	}

	/**
	 * Does this status mean that the entity is success?
	 * 
	 * @return true if success, otherwise false
	 */
	public boolean isSuccess() {
		return this.success;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}
}
