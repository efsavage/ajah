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
package com.ajah.user.email;

import java.io.Serializable;

import com.ajah.util.FromStringable;
import com.ajah.util.ToStringable;

/**
 * A wrapper around a String for typesafe email IDs.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class EmailId implements Serializable, ToStringable, FromStringable, Comparable<EmailId> {

	private static final long serialVersionUID = 7751132583460887962L;

	private final String id;

	/**
	 * Simple string constructor.
	 * 
	 * @param id
	 *            UID of email, cannot be null.
	 */
	public EmailId(final String id) {
		this.id = id;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final EmailId other) {
		return this.id.compareTo(other.getId());
	}

	/**
	 * Compares the wrapped string values.
	 * 
	 * @param other
	 *            The other UserId
	 * @return The results of {@link String#compareTo(String)}
	 */
	public boolean equals(final EmailId other) {
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
