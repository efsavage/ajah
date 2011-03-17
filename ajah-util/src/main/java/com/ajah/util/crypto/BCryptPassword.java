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
package com.ajah.util.crypto;

/**
 * {@link BCrypt} implementation of {@link Password}.
 * 
 * Note: Does not store the raw password.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class BCryptPassword implements Password {

	private final String hash;

	/**
	 * Accepts raw or hashed version of a password.
	 * 
	 * @param value
	 *            Password, or hash.
	 * @param hashed
	 *            true if the value is hashed (will not be re-hashed)
	 */
	public BCryptPassword(String value, boolean hashed) {
		if (hashed) {
			this.hash = value;
		} else {
			this.hash = BCrypt.hashpw(value, BCrypt.gensalt());
		}
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