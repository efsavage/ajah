/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.data;

import com.ajah.util.FromStringable;
import com.ajah.util.ToStringable;

/**
 * This class is just a wrapper around {@link String} with the intent of
 * asserting that the value of the wrapped text is HTML. This is for type
 * safety.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @since 1.0.5
 * 
 */
public class HtmlString implements ToStringable, FromStringable {

	/**
	 * Wraps the supplied string value with a new instance of {@link HtmlString}
	 * .
	 * 
	 * @param string
	 *            The string to wrap, may be null.
	 * @return New instance of {@link HtmlString} wrapping supplied string.
	 */
	public static HtmlString valueOf(final String string) {
		return new HtmlString(string);
	}

	final String string;

	final String sha1;

	/**
	 * Wraps the supplied string value.
	 * 
	 * @param string
	 */
	public HtmlString(final String string) {
		this.string = string;
		if (this.string != null) {
			this.sha1 = HashUtils.sha1Hex(this.string);
		} else {
			this.sha1 = null;
		}

	}

	/**
	 * Return the sha1 of the wrapped String.
	 * 
	 * @return the sha1
	 */
	public String getSha1() {
		return this.sha1;
	}

	/**
	 * Returns the wrapped string, may be null.
	 * 
	 * @return the string
	 */
	public String getString() {
		return this.string;
	}

	@Override
	public String toString() {
		return this.string;
	}

}
