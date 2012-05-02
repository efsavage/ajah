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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.java.Log;

import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;
import com.ajah.util.config.Config;

/**
 * Crypto/hash utilities.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
@Log
public class Crypto {

	/**
	 * Accepts a hexadecimal encoded version of the encrypted data and decrypts
	 * it.
	 * 
	 * @param encrypted
	 *            hexadecimal encoded version of the encrypted data
	 * @return Decrypted version.
	 * @throws UnsupportedOperationException
	 *             If there is a cryptographic error.
	 */
	public static String fromAES(final String encrypted) {
		final String keyString = Config.i.get("crypto.key.aes", null);
		final SecretKeySpec skeySpec = new SecretKeySpec(new BigInteger(keyString, 16).toByteArray(), "AES");
		try {
			final Cipher cipher = Cipher.getInstance("AES");

			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return new String(cipher.doFinal(new BigInteger(encrypted, 16).toByteArray()));
		} catch (final InvalidKeyException e) {
			throw new UnsupportedOperationException(e);
		} catch (final IllegalBlockSizeException e) {
			throw new UnsupportedOperationException(e);
		} catch (final BadPaddingException e) {
			throw new UnsupportedOperationException(e);
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		} catch (final NoSuchPaddingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Returns HmacSHA1 of input.
	 * 
	 * @param secret
	 *            String to process.
	 * @return Digest of hash.
	 */
	public static byte[] getHmacSha1(final byte[] secret) {
		try {
			final String keyString = Config.i.get("crypto.key.hmacsha1", null);
			if (StringUtils.isBlank(keyString)) {
				throw new IllegalArgumentException("crypto.key.hmacsha1 not defined");
			}
			final SecretKey key = new SecretKeySpec(keyString.getBytes(), "HmacSHA1");
			final Mac m = Mac.getInstance("HmacSHA1");
			m.init(key);
			m.update(secret);
			return m.doFinal();
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		} catch (final InvalidKeyException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Returns HmacSHA1 of input.
	 * 
	 * @param secret
	 *            String to process.
	 * @return Digest of hash.
	 */
	public static String getHmacSha1Hex(final String secret) {
		AjahUtils.requireParam(secret, "secret");
		return new BigInteger(getHmacSha1(secret.getBytes())).toString(16);
	}

	private static void keyGen() {
		final SecureRandom sr = new SecureRandom();
		final byte[] keyBytes = new byte[128];
		sr.nextBytes(keyBytes);

		final Scanner scanner = new Scanner(System.in);
		System.out.print("Algorithm: [HmacSHA1] ");
		final String algorithm = scanner.nextLine();
		if (StringUtils.isBlank(algorithm) || algorithm.equals("HmacSHA1")) {
			System.out.print("Secret: ");
			final String secret = scanner.nextLine();
			System.out.print(getHmacSha1Hex(secret));
		}
	}

	private static void listProviders() {
		log.info("Supported providers:");
		for (final Provider provider : Security.getProviders()) {
			System.out.println("\t" + provider);
			for (final Provider.Service service : provider.getServices()) {
				System.out.println("\t\t" + service.getAlgorithm());
			}
		}
	}

	/**
	 * Command line utility for testing passwords and such.
	 * 
	 * @param args
	 *            No args, program is interactive.
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		if (args.length < 1) {
			return;
		}
		if ("list".equals(args[0])) {
			listProviders();
			return;
		} else if ("keygen".equals(args[0])) {
			keyGen();
		} else if ("to-aes".equals(args[0])) {
			final String secret = "turtle";
			final String encrypted = toAES(secret);
			System.out.println(encrypted);
			final String decrypted = fromAES(encrypted);
			System.out.println(decrypted);
		}
	}

	/**
	 * Encodes a value in aes and returns the hexadecimal encoded version of the
	 * encrypted data.
	 * 
	 * @param secret
	 *            Data to encrypt.
	 * @return Hexadecimal encoded version of the encrypted data.
	 * @throws UnsupportedOperationException
	 *             If there is a cryptographic error.
	 */
	public static String toAES(final String secret) {
		final String keyString = Config.i.get("crypto.key.aes", null);
		AjahUtils.requireParam(keyString, "crypto.key.aes");
		final SecretKeySpec skeySpec = new SecretKeySpec(new BigInteger(keyString, 16).toByteArray(), "AES");
		try {
			final Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			final byte[] encrypted = cipher.doFinal(secret.getBytes());
			return new BigInteger(encrypted).toString(16);
		} catch (final InvalidKeyException e) {
			throw new UnsupportedOperationException(e);
		} catch (final IllegalBlockSizeException e) {
			throw new UnsupportedOperationException(e);
		} catch (final BadPaddingException e) {
			throw new UnsupportedOperationException(e);
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		} catch (final NoSuchPaddingException e) {
			throw new UnsupportedOperationException(e);
		}

	}

}
