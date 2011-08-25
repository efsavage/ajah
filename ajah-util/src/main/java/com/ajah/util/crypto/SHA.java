package com.ajah.util.crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

	public static String sha1Hex(final String string) {
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
