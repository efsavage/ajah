/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.message;

/**
 * Basic implementations of UserMessageType.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum UserMessageTypeImpl implements UserMessageType {

	/**
	 * Regular message, should be the default.
	 */
	NORMAL("1", "UserMessage", "UserMessage", "UserMessage"),
	/**
	 * System message, probably important.
	 */
	SYSTEM("2", "System", "System", "System"),
	/**
	 * UserMessage that is a link to a URL.
	 */
	LINK("3", "Link", "Link", "Link");

	/**
	 * Finds a UserMessageTypeImpl that matches the id on id, name, or name().
	 * 
	 * @param id
	 *            Value to match against id, name, or name()
	 * @return Matching UserMessageTypeImpl, or null.
	 */
	public static UserMessageType get(final String id) {
		for (final UserMessageTypeImpl type : values()) {
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

	UserMessageTypeImpl(final String id, final String code, final String name, final String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

}
