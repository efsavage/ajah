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
 * Valid types of LogIn entities.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum LogInType implements IdentifiableEnum<String> {

	/**
	 * Manual.
	 */
	MANUAL("1", "manual", "Manual", "Login was done by human/form."),
	/**
	 * Cookie.
	 */
	COOKIE("2", "cookie", "Cookie", "Login was done via cookie."),
	/**
	 * Signup.
	 */
	SIGNUP("3", "signup", "Signup", "Login was done as part of signup process."),
	/**
	 * OAuth.
	 */
	OAUTH("4", "oauth", "OAuth", "Login was done via OAuth."),
	/**
	 * API Token.
	 */
	API_TOKEN("5", "apitoken", "API Token", "Login was done by API token.");

	/**
	 * Finds a LogInType that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching LogInType, or null.
	 */
	public static LogInType get(final String string) {
		for (final LogInType type : values()) {
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

	private LogInType(final String id, final String code, final String name, final String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
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

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
