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

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ajah.util.AjahUtils;

/**
 * Simple Utility class for creating SHA values.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class SHA {

	/**
	 * Compute the SHA-1 digest of a string and return it as a hexadecimal
	 * value.
	 * 
	 * @param string
	 *            The string to hash, required.
	 * @return THe a hexadecimal value of the string.
	 */
	public static String sha1Hex(final String string) {
		AjahUtils.requireParam(string, "string");
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] bytes = null;
			md.update(string.getBytes(StandardCharsets.ISO_8859_1), 0, string.length());
			bytes = md.digest();
			return String.format("%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		}
	}

}
