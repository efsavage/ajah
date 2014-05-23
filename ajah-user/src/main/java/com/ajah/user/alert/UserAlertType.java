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
 * Valid states of UserAlert entities.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum UserAlertType implements IdentifiableEnum<String> {

	/**
	 * Error.
	 */
	ERROR("-1", "error", "Error", "A message than an error occured."),
	/**
	 * Success.
	 */
	SUCCESS("1", "success", "Success", "A message confirming success."),
	/**
	 * Warning.
	 */
	WARNING("-2", "warning", "Warning", "A message about a warning or mild error."),
	/**
	 * Invitation.
	 */
	INVITE("3", "invite", "Invitation", "An invitation from another user."),
	/**
	 * Friend.
	 */
	FRIEND("4", "friend", "Friend", "A message about a friend."),
	/**
	 * Achievement.
	 */
	ACHIEVEMENT("5", "achieve", "Achievement", "A message about a achievement being earned."),
	/**
	 * New Message
	 */
	MESSAGE("6", "message", "Message", "A notification about a new chat/email message being recieved.."),
	/**
	 * Info.
	 */
	INFO("2", "info", "Info", "A message containing useful info.");

	/**
	 * Finds a UserAlertType that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching UserAlertType, or null.
	 */
	public static UserAlertType get(final String string) {
		for (final UserAlertType type : values()) {
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

	private UserAlertType(final String id, final String code, final String name, final String description) {
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
