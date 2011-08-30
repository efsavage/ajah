package com.ajah.util.crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
