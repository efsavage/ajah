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
package com.ajah.util.data.format;

import com.ajah.util.AjahUtils;
import com.ajah.util.FromStringable;
import com.ajah.util.ToStringable;
import com.ajah.util.Validate;

/**
 * Wrapper class for valid email addresses.
 * 
 * @author Eric F. Savage, code@efsavage.com
 * 
 */
public class EmailAddress implements ToStringable, FromStringable {

	private final String address;

	/**
	 * Contructs with an email, calls {@link Validate#isEmail(String)} to
	 * validate, throwing {@link IllegalArgumentException} on failure.
	 * 
	 * @see Validate#isEmail(String)
	 * @param address
	 *            Email address, must be valid and non-null
	 * @throws IllegalArgumentException
	 *             for an invalid or null email
	 */
	public EmailAddress(final String address) {
		AjahUtils.requireParam(address, "address");
		if (!Validate.isEmail(address)) {
			throw new IllegalArgumentException("Invalid email address format: " + address);
		}
		this.address = address;
	}

	/**
	 * Returns address set in constructor.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.address;
	}

}
