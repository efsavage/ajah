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
 * A wrapped version of {@link StringBuilder} that handles compactness. Similar
 * concept to loggers and log levels.
 * 
 * Note: We cannot extend {@link StringBuilder} because it is final.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class AjahStringBuilder {

	final StringBuilder builder = new StringBuilder();
	private final Compact compact;

	/**
	 * Public constructor.
	 * 
	 * @param compact
	 *            The compactness of this builder.
	 */
	public AjahStringBuilder(final Compact compact) {
		this.compact = compact;
	}

	/**
	 * Append this character if this builder not more compact than the target.
	 * 
	 * @param c
	 *            The character to append.
	 * @param targetCompact
	 *            The target compactness level.
	 */
	public void append(final char c, final Compact targetCompact) {
		if (this.compact.le(targetCompact)) {
			this.builder.append(c);
		}
	}

	/**
	 * Append these chars if this builder not more compact than the target.
	 * 
	 * @param chars
	 *            The chars to append.
	 * @param targetCompact
	 *            The target compactness level.
	 */
	public void append(final char[] chars, final Compact targetCompact) {
		if (this.compact.le(targetCompact)) {
			this.builder.append(chars);
		}
	}

	/**
	 * @see StringBuilder#append(String)
	 * @param string
	 *            The string to append, regardless of compactness.
	 */
	public void append(final String string) {
		this.builder.append(string);
	}

	@Override
	public String toString() {
		return this.builder.toString();
	}
}
