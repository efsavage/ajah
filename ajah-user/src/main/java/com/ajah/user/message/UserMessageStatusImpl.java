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
 * Basic implementations of UserMessageStatus.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum UserMessageStatusImpl implements UserMessageStatus {

	/**
	 * UserMessage was not sent because of an error.
	 */
	ERROR("-3", "Error", "Error", "Error"),
	/**
	 * UserMessage was saved but not sent yet.
	 */
	DRAFT("-2", "Draft", "Draft", "Draft"),
	/**
	 * UserMessage was deleted.
	 */
	DELETED("-1", "Deleted", "Deleted", "Deleted"),
	/**
	 * Unread message.
	 */
	UNREAD("0", "Unread", "Unread", "Unread"),
	/**
	 * UserMessage was read.
	 */
	READ("1", "Read", "Read", "Read"),
	/**
	 * UserMessage was replied to.
	 */
	REPLY("2", "Replied", "Replied", "Replied"),
	/**
	 * UserMessage was forwarded.
	 */
	FORWARD("3", "Forwarded", "Forwarded", "Forwarded"),
	/**
	 * UserMessage is in the process of being sent.
	 */
	SENDING("4", "Sending", "Sending", "Sending"),
	/**
	 * UserMessage was sent.
	 */
	SENT("5", "Sent", "Sent", "Sent");

	/**
	 * Finds a UserMessageStatusImpl that matches the id on id, name, or name().
	 * 
	 * @param id
	 *            Value to match against id, name, or name()
	 * @return Matching UserMessageStatusImpl, or null.
	 */
	public static UserMessageStatus get(final String id) {
		for (final UserMessageStatusImpl type : values()) {
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

	private UserMessageStatusImpl(final String id, final String code, final String name, final String description) {
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
