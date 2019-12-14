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
package com.ajah.util.io;

/**
 * Enumeration to define some general levels of Compactness. Useful for things
 * like minifying files.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum Compact {

	/**
	 * No compaction.
	 */
	NONE(0),
	/**
	 * The lightest level of compaction (removing blank lines, extra whitespace,
	 * etc.)
	 */
	LOW(1),
	/**
	 * A good level of compaction.
	 */
	MED(2),
	/**
	 * A high level of compaction but still somewhat readable.
	 */
	HIGH(3),
	/**
	 * Maximum compaction.
	 */
	MAX(4);

	/**
	 * Match a compactness level based on its name, and if none is found, return
	 * a default value.
	 * 
	 * @param value
	 *            The value to search for, a case-insensitive match with the
	 *            enum name.
	 * @param defaultValue
	 *            The value to return if no match is found, may be null.
	 * @return The matching Compact, or the default value supplied, which may be
	 *         null.
	 */
	public static Compact get(final String value, final Compact defaultValue) {
		for (final Compact compact : values()) {
			if (compact.name().equalsIgnoreCase(value)) {
				return compact;
			}
		}
		return defaultValue;
	}

	private final int level;

	Compact(final int level) {
		this.level = level;
	}

	/**
	 * Tell whether the specified Compact argument is more or equally compact as
	 * this one.
	 * 
	 * @param other
	 *            The compact to compare to.
	 * @return true if this compact is less or equally compact than the
	 *         parameter.
	 */
	public boolean ge(final Compact other) {
		return this.level >= other.level;
	}

	/**
	 * Tell whether the specified Compact argument is less or equally compact as
	 * this one.
	 * 
	 * @param other
	 *            The compact to compare to.
	 * @return true if this compact is less or equally compact than the
	 *         parameter.
	 */
	public boolean le(final Compact other) {
		return this.level <= other.level;
	}

	/**
	 * Tell whether the specified Compact argument is less compact than this
	 * one.
	 * 
	 * @param other
	 *            The compact to compare to.
	 * @return true if this compact is less compact than the parameter.
	 */
	public boolean lt(final Compact other) {
		return this.level < other.level;
	}

}
