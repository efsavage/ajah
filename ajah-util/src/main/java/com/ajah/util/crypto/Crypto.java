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

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;
import com.ajah.util.config.Config;

/**
 * Crypto/hash utilities.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class Crypto {

	/**
	 * Returns HmacSHA1 of input.
	 * 
	 * @param secret
	 *            String to process.
	 * @return Digest of hash.
	 */
	public static byte[] getHmacSha1(byte[] secret) {
		try {
			String keyString = Config.i.get("crypto.key.hmacsha1", null);
			if (StringUtils.isBlank(keyString)) {
				throw new IllegalArgumentException("crypto.key.hmacsha1 not defined");
			}
			SecretKey key = new SecretKeySpec(keyString.getBytes(), "HmacSHA1");
			Mac m = Mac.getInstance("HmacSHA1");
			m.init(key);
			m.update(secret);
			return m.doFinal();
		} catch (NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(e);
		} catch (InvalidKeyException e) {
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
	public static String getHmacSha1Hex(String secret) {
		AjahUtils.requireParam(secret, "secret");
		return new BigInteger(getHmacSha1(secret.getBytes())).toString(16);
	}

	/**
	 * Command line utility for testing passwords and such.
	 * 
	 * @param args
	 *            No args, program is interactive.
	 */
	public static void main(String[] args) {
		SecureRandom sr = new SecureRandom();
		byte[] keyBytes = new byte[20];
		sr.nextBytes(keyBytes);
		System.out.println(new BigInteger(keyBytes).toString(16));

		Scanner scanner = new Scanner(System.in);
		System.out.print("Algorithm: [HmacSHA1] ");
		String algorithm = scanner.nextLine();
		if (StringUtils.isBlank(algorithm) || algorithm.equals("HmacSHA1")) {
			System.out.print("Secret: ");
			String secret = scanner.nextLine();
			System.out.print(getHmacSha1Hex(secret));
		}
	}

}