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
package com.ajah.crypto;

/**
 * HmacSHA1 implementation of {@link Password}. Note: Does not store the raw
 * password.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public class HmacSha1Password implements Password {

	private final String hash;
	private final int originalLength;

	/**
	 * Accepts raw or hashed version of a password.
	 * 
	 * @param value
	 *            Password, or hash.
	 * @param hashed
	 *            true if the value is hashed (will not be re-hashed)
	 */
	public HmacSha1Password(final String value, final boolean hashed) {
		if (hashed) {
			this.hash = value;
		} else {
			this.hash = Crypto.getHmacSha1Hex(value);
		}
		this.originalLength = value.length();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOriginalLength() {
		return this.originalLength;
	}

	/**
	 * Returns hash.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.hash;
	}

}
