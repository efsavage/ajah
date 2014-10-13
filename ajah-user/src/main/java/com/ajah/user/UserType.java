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
 * Basic implementations of UserType.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum UserType implements IdentifiableEnum<String> {

	/**
	 * Bot user, has no permissions except those explicitly assigned to it.
	 */
	BOT("0", "Bot", "Bot User", "Bot user, has no implicit permissions.", false, true),
	/**
	 * Regular user, should be the default.
	 */
	NORMAL("1", "Normal", "Normal User", "Normal user, has default set of permissions.", false, false),
	/**
	 * Super (root) user, has all permissions.
	 */
	SUPER_USER("2", "Super", "Super User", "Super user, has all permissions.", true, false),
	/**
	 * A staff user that may or may not have special restrictions or
	 * permissions.
	 */
	STAFF("4", "Staff", "Staff", "Staff.", true, false),
	/**
	 * A test account, may be restricted from some operations in some
	 * environments.
	 */
	TEST("5", "Test", "Test", "Test.", true, false);

	/**
	 * Finds a UserType that matches the id on id, name, or name().
	 * 
	 * @param id
	 *            Value to match against id, name, or name()
	 * @return Matching UserType, or null.
	 */
	public static UserType get(final String id) {
		for (final UserType type : values()) {
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
	private final boolean superUser;
	private final boolean botUser;

	private UserType(final String id, final String code, final String name, final String description, final boolean superUser, final boolean botUser) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.superUser = superUser;
		this.botUser = botUser;
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
	 * Returns if this user type is a bot user, meaning it probably has a single
	 * purpose related to automation. It has no default permissions, including
	 * being able to log in, and must specifically assigned any access levels.
	 * 
	 * @return the botUser
	 */
	public boolean isBotUser() {
		return this.botUser;
	}

	/**
	 * Returns if this user type is a super user, meaning it has full access and
	 * all permissions. This is similar to being a root or administrator user.
	 * 
	 * @return the superUser True if the user is a super user.
	 */
	public boolean isSuperUser() {
		return this.superUser;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
