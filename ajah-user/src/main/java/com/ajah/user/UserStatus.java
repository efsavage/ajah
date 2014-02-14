/*
 *  Copyright 2011-2013 Eric F. Savage, code@efsavage.com
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

import com.ajah.util.IdentifiableEnum;

/**
 * Basic implementations of UserStatus.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum UserStatus implements IdentifiableEnum<String> {

	/**
	 * New user, unverified/unpaid.
	 */
	NEW("0", "New", "New", "New"),
	/**
	 * Regular user, should be the normal case.
	 */
	ACTIVE("1", "Active", "Active", "Active");

	/**
	 * Finds a UserStatus that matches the id on id, name, or name().
	 * 
	 * @param id
	 *            Value to match against id, name, or name()
	 * @return Matching UserStatus, or null.
	 */
	public static UserStatus get(final String id) {
		for (final UserStatus type : values()) {
			if (type.getId().equals(id) || type.getCode().equals(id) || type.name().equals(id)) {
				return type;
			}
		}
		return null;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String description;

	private UserStatus(final String id, final String code, final String name, final String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	/**
	 * The short, display-friendly code of the status. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the status. Cannot be null.
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * The display-friendly description of the status.
	 * 
	 * @return The display-friendly description of the status. May be null.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * The internal ID of the status.
	 * 
	 * @return The internal ID of the status. Cannot be null.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The display-friendly name of the status. If no name is applicable, it
	 * should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the status. Cannot be null.
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
