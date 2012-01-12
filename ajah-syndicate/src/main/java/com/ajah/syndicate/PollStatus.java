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
package com.ajah.syndicate;

import com.ajah.util.Identifiable;

/**
 * Denotes the polling behavior of a {@link FeedSource}..
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum PollStatus implements Identifiable<String> {

	/**
	 * Feed should actively be polled.
	 */
	ACTIVE("0", "Active", "Active", "Active"),
	/**
	 * Feed has experienced an error, but should be retried at some point.
	 */
	ERROR_TMP("-1", "error-tmp", "Error (Temporary)", "FeedSource has experienced an error that it might recover from."),
	/**
	 * Feed has experienced an error that is not recoverable and must be
	 * manually reactivated.
	 */
	ERROR_PERM("-2", "error-perm", "Error (Permanent)", "FeedSource has experienced an unrecoverable error or too many temporary errors."),
	/**
	 * Feed should not be polled.
	 */
	BLOCKED("-3", "blocked", "Blocked", "Blocked");

	private PollStatus(String id, String code, String name, String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String description;

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
	 * The short, display-friendly code of the status. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the status. Cannot be null.
	 */
	public String getCode() {
		return this.code;
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
	 * The display-friendly description of the status.
	 * 
	 * @return The display-friendly description of the status. May be null.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Finds a PollStatus that matches the id on id, name, or name().
	 * 
	 * @param id
	 *            Value to match against id, name, or name()
	 * @return Matching PollStatus, or null.
	 */
	public static PollStatus get(String id) {
		for (PollStatus type : values()) {
			if (type.getId().equals(id) || type.getCode().equals(id) || type.name().equals(id)) {
				return type;
			}
		}
		return null;
	}

	@Override
	public void setId(String id) {
		throw new UnsupportedOperationException();
	}

}
