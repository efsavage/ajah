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
package com.ajah.user.invitation;

import com.ajah.util.IdentifiableEnum;

/**
 * Valid states of {@link Invitation} entities.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum InvitationStatus implements IdentifiableEnum<String> {

	/**
	 * Unsent.
	 */
	UNSENT("0", "unsent", "Unsent", "Invitation has not been sent yet.", false, false, false),
	/**
	 * Sent.
	 */
	SENT("1", "sent", "Sent", "Sent.", true, false, false),
	/**
	 * Recipient opened the invitation (exactly what that means will vary by
	 * channel).
	 */
	OPENED("2", "opened", "Opened", "Opened.", true, false, false),
	/**
	 * Recipient clicked/acknowledged the invitation (exactly what that means
	 * will vary by channel).
	 */
	CLICKED("3", "clicked", "Clicked", "Clicked.", true, false, false),
	/**
	 * Accepted.
	 */
	ACCEPTED("4", "accepted", "Accepted", "Accepted.", true, false, false),
	/**
	 * Rejected.
	 */
	REJECTED("-4", "rejected", "Rejected", "Rejected.", false, false, false),
	/**
	 * Expired.
	 */
	EXPIRED("-3", "expired", "Expired", "Expired.", false, false, false),
	/**
	 * Error.
	 */
	ERROR("-2", "err", "Error", "Error.", false, true, false),
	/**
	 * Deleted.
	 */
	DELETED("-1", "del", "Deleted", "Deleted.", false, false, true);

	/**
	 * Finds a InvitationStatus that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching InvitationStatus, or null.
	 */
	public static InvitationStatus get(final String string) {
		for (final InvitationStatus type : values()) {
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
	private final boolean active;
	private final boolean error;
	private final boolean deleted;

	InvitationStatus(final String id, final String code, final String name, final String description, final boolean active, final boolean error, final boolean deleted) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.active = active;
		this.error = error;
		this.deleted = deleted;
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
	 * Does this status mean that the entity is active?
	 * 
	 * @return true if active, otherwise false
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Does this status mean that the entity has been deleted?
	 * 
	 * @return true if deleted, otherwise false
	 */
	public boolean isDeleted() {
		return this.deleted;
	}

	/**
	 * Does this status mean that the entity is in an error state?
	 * 
	 * @return true if in error state, otherwise false
	 */
	public boolean isError() {
		return this.error;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}
}
