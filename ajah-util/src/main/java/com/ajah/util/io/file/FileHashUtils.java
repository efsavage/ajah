/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;

import com.ajah.util.lang.NameValuePair;

/**
 * Set of utilities for hashing files.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class FileHashUtils {

	/**
	 * Splits a hash signature into a directory structure so that there will not
	 * be too many files in a single directory.
	 * 
	 * Examples:
	 * 
	 * <code>getHashedFileName("123abcdefhjkl",3,2)</code> will return
	 * "12/3a/bc/123abcdefhjkl"
	 * 
	 * <code>getHashedFileName("123abcdefhjkl",2,5)</code> will return
	 * "123ab/cdefh/123abcdefhjkl"
	 * 
	 * @param hash
	 *            The hash to split.
	 * @param depth
	 *            The number of subdirectories.
	 * @param breadth
	 *            The length of each split sequence.
	 * @return The filename with parent directories.
	 */
	public static String getHashedFileName(final String hash, final int depth, final int breadth) {
		final StringBuilder name = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			name.append(hash.substring(i * breadth, (i + 1) * breadth));
			name.append("/");
		}
		name.append(hash);
		return name.toString();
	}

	/**
	 * Reads a file and returns the MD5 checksum of it.
	 * 
	 * @param file
	 *            The file to checksum.
	 * @param blockSize
	 *            The size of the block to read. Higher value may result in more
	 *            memory and faster performance.
	 * @return The hexadecimal representation of the MD5 checksum of the file's
	 *         data. Will return null of {@link NoSuchAlgorithmException}
	 *         occurs.
	 * @throws IOException
	 *             If there was an error reading the file.
	 */
	public static String md5Hex(final File file, final int blockSize) throws IOException {
		final long start = System.currentTimeMillis();
		if (!file.canRead()) {
			return null;
		}

		try (InputStream fis = new FileInputStream(file)) {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			try (InputStream is = new DigestInputStream(fis, md)) {
				int read;
				do {
					final byte[] block = new byte[blockSize];
					read = is.read(block);
				} while (read >= 0);
				// read stream to EOF as normal...
				final byte[] digest = md.digest();
				final String hash = String.format("%0" + (digest.length << 1) + "x", new BigInteger(1, digest));
				// log.info(hash);
				final long duration = System.currentTimeMillis() - start;
				// if (duration > 50) {
				log.fine(file.length() + " took " + duration + "ms");
				// }
				return hash;
			}
		} catch (final NoSuchAlgorithmException e) {
			log.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Will return a list of individually hashed chunks of a file.
	 * 
	 * @param file
	 *            The file to hash/split.
	 * @param blockSize
	 *            The size of the block to split the file into.
	 * @return A list of {@link NameValuePair} where the name is the hash and
	 *         the value is the number of bytes.
	 */
	public static List<NameValuePair<Integer>> md5HexChunks(final File file, final int blockSize) {
		final List<NameValuePair<Integer>> nvps = new ArrayList<>();
		final long start = System.currentTimeMillis();
		if (!file.canRead()) {
			return null;
		}
		try (InputStream fis = new FileInputStream(file)) {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			try (InputStream is = new DigestInputStream(fis, md)) {
				int read;
				do {
					final long fragmentStart = System.currentTimeMillis();
					final byte[] block = new byte[blockSize];
					read = is.read(block);
					if (read < 0) {
						break;
					}
					final byte[] digest = md.digest();
					final String hash = String.format("%0" + (digest.length << 1) + "x", new BigInteger(1, digest));
					final NameValuePair<Integer> nvp = new NameValuePair<>(hash, Integer.valueOf(read));
					log.fine(FileHashUtils.getHashedFileName(hash, 3, 2));

					final long fragmentDuration = System.currentTimeMillis() - fragmentStart;
					log.fine(nvp.getValue() + " bytes hashing to " + nvp.getName() + " took " + fragmentDuration + "ms");
					nvps.add(nvp);
				} while (true);
				final long duration = System.currentTimeMillis() - start;
				log.fine(nvps.size() + " fragments  took " + duration + "ms");
				return nvps;
			}
		} catch (final NoSuchAlgorithmException e) {
			log.log(Level.WARNING, e.getMessage(), e);
			return null;
		} catch (final IOException e) {
			if (e.getMessage().endsWith("(Access is denied)")) {
				// log.info(e.getMessage());
			} else if (e.getMessage().equals("The process cannot access the file because another process has locked a portion of the file")) {
				// log.info(e.getMessage());
			} else {
				log.warning(file.getAbsolutePath());
				log.log(Level.WARNING, e.getMessage(), e);
			}
			return null;
		}
	}

}
