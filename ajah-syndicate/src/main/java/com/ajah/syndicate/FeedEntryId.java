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

import java.io.Serializable;

import com.ajah.util.FromStringable;
import com.ajah.util.ToStringable;

/**
 * A wrapper around a String for typesafe entry IDs.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class EntryId implements Serializable, ToStringable, FromStringable, Comparable<EntryId> {

	private final String id;

	/**
	 * Simple string constructor.
	 * 
	 * @param id
	 *            UID of entry, cannot be null.
	 */
	public EntryId(final String id) {
		this.id = id;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final EntryId other) {
		return this.id.compareTo(other.getId());
	}

	/**
	 * Compares the wrapped string values.
	 * 
	 * @param other
	 *            The other EntryId
	 * @return The results of {@link String#compareTo(String)}
	 */
	public boolean equals(final EntryId other) {
		return this.id.equals(other.id);
	}

	/**
	 * Returns the String passed into the constructor.
	 * 
	 * @return The String passed into the constructor.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Returns consistent hashcode based on (but different from) the wrapped
	 * string.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 9525 + this.id.hashCode();
	}

	/**
	 * Returns the String passed into the constructor.
	 */
	@Override
	public String toString() {
		return this.id;
	}

}
