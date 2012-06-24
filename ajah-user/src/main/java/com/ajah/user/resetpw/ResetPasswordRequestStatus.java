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
package com.ajah.user.resetpw;

import com.ajah.util.Identifiable;

/**
 * All reset password requests must be in some state, which determines the
 * permitted operations. Common statuses might be NEW, SENT, REDEEMED
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum ResetPasswordRequestStatus implements Identifiable<String> {

	/**
	 * New user, unverified/unpaid.
	 */
	NEW("0", "New", "New", "New", true),
	/**
	 * This request was successfully redeemed.
	 */
	REDEEMED("1", "Active", "Active", "Active", false),
	/**
	 * This request expired and cannot be redeemed.
	 */
	EXPIRED("2", "Expired", "Expired", "Expired", false);

	/**
	 * Finds a ResetPasswordRequestStatus that matches the id on id, name, or
	 * name().
	 * 
	 * @param id
	 *            Value to match against id, name, or name()
	 * @return Matching ResetPasswordRequestStatus, or null.
	 */
	public static ResetPasswordRequestStatus get(final String id) {
		for (final ResetPasswordRequestStatus type : values()) {
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
	private final boolean redeemable;

	private ResetPasswordRequestStatus(final String id, final String code, final String name, final String description, final boolean redeemable) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.redeemable = redeemable;
	}

	/**
	 * The short, display-friendly code of the status. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the status. Cannot be null.
	 */
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
	public String getName() {
		return this.name;
	}

	/**
	 * Can this request be used to reset the password?
	 * 
	 * @return True if this request be used to reset the password, otherwise
	 *         false.
	 */
	public boolean isRedeemable() {
		return this.redeemable;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
