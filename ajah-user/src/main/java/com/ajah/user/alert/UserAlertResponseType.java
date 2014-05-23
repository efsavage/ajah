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
package com.ajah.user.alert;

import com.ajah.util.IdentifiableEnum;

/**
 * Valid types of UserAlert responses.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum UserAlertResponseType implements IdentifiableEnum<String> {

	/**
	 * Response required, show it until dismissed.
	 */
	DISMISS("1", "dismiss", "Dismissal required", "Dismissal required."),
	/**
	 * No response required, just show it.
	 */
	NONE("0", "none", "None", "No response required.");

	/**
	 * Finds a UserAlertResponseType that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching UserAlertResponseType, or null.
	 */
	public static UserAlertResponseType get(final String string) {
		for (final UserAlertResponseType responseType : values()) {
			if (responseType.getId().equals(string) || responseType.getCode().equals(string) || responseType.name().equals(string) || responseType.getName().equals(string)) {
				return responseType;
			}
		}
		return null;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String description;

	private UserAlertResponseType(final String id, final String code, final String name, final String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	/**
	 * The short, display-friendly code of the responseType. If no code is
	 * applicable, it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the responseType. Cannot be
	 *         null.
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * The display-friendly description of the responseType.
	 * 
	 * @return The display-friendly description of the responseType. May be
	 *         null.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * The internal ID of the responseType.
	 * 
	 * @return The internal ID of the responseType. Cannot be null.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The display-friendly name of the responseType. If no name is applicable,
	 * it should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the responseType. Cannot be null.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
