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
package com.ajah.util.data;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

import com.ajah.util.AjahUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class HashUtils {

	/**
	 * Calls {@link #getHashedFileName(String, int, int)} with depth of 3 and
	 * breadth of 2, which should provide enough separation for most
	 * applications.
	 * 
	 * @param name
	 *            The name to hash.
	 * @return The hashed name with directory path.
	 */
	public static String getHashedFileName(final String name) {
		return getHashedFileName(name, 3, 2);
	}

	/**
	 * Creates a hashed name with directory path for a given name.
	 * 
	 * Example: abcdefghijklmnop with depth of 3 and breadth of 2 would return
	 * "ab/cd/ef/abcdefghijklmnop". Example: abcdefghijklmnop with depth of 2
	 * and breadth of 5 would return "abcde/efghi/abcdefghijklmnop".
	 * 
	 * @param name
	 *            The name to hash.
	 * @param depth
	 *            The number of subdirectories to split. A higher number will
	 *            yield more directories but fewer files within each directory.
	 * @param breadth
	 *            The number of characters to use when creating the
	 *            subdirectories. A higher number will yield fewer directories
	 *            but more files within each directory.
	 * @return The hashed name with directory path.
	 */
	public static String getHashedFileName(final String name, final int depth, final int breadth) {
		AjahUtils.requireParam(name, "name");
		final StringBuilder hashed = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			hashed.append(name.substring(i * breadth, (i + 1) * breadth));
			hashed.append("/");
		}
		hashed.append(name);
		return hashed.toString();
	}

	/**
	 * Creates an MD5 digest of byte array and returns it as a Base 64 encoded
	 * number.
	 * 
	 * @param bytes
	 *            The bytes to digest.
	 * @return The hexadecimal result of the digest.
	 */
	public static String md5Base64(final byte[] bytes) {
		AjahUtils.requireParam(bytes, "bytes");
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = new byte[32];
			md.update(bytes, 0, bytes.length);
			digest = md.digest();
			return DatatypeConverter.printBase64Binary(digest);
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Creates an MD5 digest of string and returns it as a Base 64 encoded
	 * number.
	 * 
	 * @param string
	 *            The string to digest.
	 * @return The hexadecimal result of the digest.
	 */
	public static String md5Base64(final String string) {
		AjahUtils.requireParam(string, "string");
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = new byte[32];
			md.update(string.getBytes(StandardCharsets.ISO_8859_1), 0, string.length());
			bytes = md.digest();
			return DatatypeConverter.printBase64Binary(bytes);
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Creates an MD5 digest of string and returns it as a hexadecimal number.
	 * 
	 * @param string
	 *            The string to digest.
	 * @return The hexadecimal result of the digest.
	 */
	public static String md5Hex(final String string) {
		AjahUtils.requireParam(string, "string");
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = new byte[32];
			md.update(string.getBytes(StandardCharsets.ISO_8859_1), 0, string.length());
			bytes = md.digest();
			return String.format("%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Creates an MD5 digest of string and returns it as a hexadecimal number.
	 * 
	 * @param string
	 *            The string to digest.
	 * @return The hexadecimal result of the digest.
	 */
	public static String md5Hex(final byte[] data) {
		AjahUtils.requireParam(data, "data");
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data, 0, data.length);
			final byte[] bytes = md.digest();
			return String.format("%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Creates a SHA-1 digest of byte array and returns it as a hexadecimal
	 * number.
	 * 
	 * @param data
	 *            The data to digest.
	 * @return The hexadecimal result of the digest.
	 */
	public static String sha1Hex(final byte[] data) {
		AjahUtils.requireParam(data, "data");
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(data, 0, data.length);
			final byte[] bytes = md.digest();
			return String.format("%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Creates a SHA-1 digest of string and returns it as a hexadecimal number.
	 * 
	 * @param string
	 *            The string to digest.
	 * @return The hexadecimal result of the digest.
	 */
	public static String sha1Hex(final String string) {
		AjahUtils.requireParam(string, "string");
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(string.getBytes(StandardCharsets.UTF_8), 0, string.length());
			final byte[] bytes = md.digest();
			return String.format("%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Creates a SHA-256 digest of string and returns it as a hexadecimal
	 * number.
	 * 
	 * @param string
	 *            The string to digest.
	 * @return The hexadecimal result of the digest.
	 */
	public static byte[] sha256(final String string) {
		AjahUtils.requireParam(string, "string");
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(string.getBytes(StandardCharsets.ISO_8859_1), 0, string.length());
			return md.digest();
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Creates a SHA-256 digest of string and returns it as a hexadecimal
	 * number.
	 * 
	 * @param string
	 *            The string to digest.
	 * @return The hexadecimal result of the digest.
	 */
	public static String sha256Hex(final String string) {
		final byte[] bytes = sha256(string);
		return String.format("%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
	}

}
