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

/**
 * Basic implementations of ResetPasswordRequestStatus.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum ResetPasswordRequestStatusImpl implements ResetPasswordRequestStatus {

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

	private ResetPasswordRequestStatusImpl(String id, String code, String name, String description, boolean redeemable) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.redeemable = redeemable;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String description;
	private final boolean redeemable;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRedeemable() {
		return this.redeemable;
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
	public String getCode() {
		return this.code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Finds a ResetPasswordRequestStatusImpl that matches the id on id, name,
	 * or name().
	 * 
	 * @param id
	 *            Value to match against id, name, or name()
	 * @return Matching ResetPasswordRequestStatusImpl, or null.
	 */
	public static ResetPasswordRequestStatus get(String id) {
		for (ResetPasswordRequestStatusImpl type : values()) {
			if (type.getId().equals(id) || type.getCode().equals(id) || type.name().equals(id)) {
				return type;
			}
		}
		return null;
	}

}