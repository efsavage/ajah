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
 * Basic implementations of FeedSourceType.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum FeedSourceType implements Identifiable<String> {

	/**
	 * RSS.
	 */
	RSS("1", "rss", "RSS", "RSS"),
	/**
	 * Unknown type.
	 */
	UNKNOWN("0", "unknown", "Unknown", "Unknown");

	private FeedSourceType(String id, String code, String name, String description) {
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
	 * The internal ID of the type.
	 * 
	 * @return The internal ID of the type. Cannot be null.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The short, display-friendly code of the type. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the type. Cannot be null.
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * The display-friendly name of the type. If no name is applicable, it
	 * should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the type. Cannot be null.
	 */
	public String getName() {
		return this.name;
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
	 * Finds a FeedSourceType that matches the id on id, name, or name().
	 * 
	 * @param id
	 *            Value to match against id, name, or name()
	 * @return Matching FeedSourceType, or null.
	 */
	public static FeedSourceType get(String id) {
		for (FeedSourceType type : values()) {
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
