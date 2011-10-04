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

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class HashUtils {

	public static String sha1Hex(final String string) {
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] bytes = new byte[40];
			md.update(string.getBytes("iso-8859-1"), 0, string.length());
			bytes = md.digest();
			return String.format("%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		} catch (final UnsupportedEncodingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public static String md5Hex(final String string) {
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = new byte[32];
			md.update(string.getBytes("iso-8859-1"), 0, string.length());
			bytes = md.digest();
			return String.format("%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		} catch (final UnsupportedEncodingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

}
