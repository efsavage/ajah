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
package com.ajah.util.io.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ajah.util.AjahUtils;
import com.ajah.util.IOUtils;

/**
 * Utilities for basic file operations.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class FileUtils {

	private static Logger log = Logger.getLogger(FileUtils.class.getName());

	/**
	 * Reads a file and returns the lines of it as a list of strings. Handles
	 * opening a closing the file. If there are any errors other than that the
	 * file does not exist, will return an empty or partial list. Will ignore
	 * any line that starts with "#".
	 * 
	 * @param fileName
	 *            The name of the file to load, required.
	 * @return List of lines in the file.
	 * @throws FileNotFoundException
	 *             If the file does not exist.
	 */
	public static List<String> readFileAsLines(final String fileName) throws FileNotFoundException {
		if (fileName == null || fileName.length() < 1) {
			throw new IllegalArgumentException("Filename may not be null or empty");
		}
		final File file = new File(fileName);
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		final List<String> data = new ArrayList<String>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			while (in.ready()) {
				final String line = in.readLine();
				if (line != null && !line.startsWith("#") && line.length() > 0) {
					data.add(line);
				}
			}
		} catch (final IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					log.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
		log.log(Level.FINER, data.size() + " elements loaded.");
		return data;
	}

	/**
	 * Reads a file and returns the lines of it as a list of strings. Handles
	 * opening a closing the file. If there are any errors other than that the
	 * file does not exist, will return an empty or partial list.
	 * 
	 * @param fileName
	 *            The name of the file to load, required.
	 * @return List of lines in the file.
	 * @throws FileNotFoundException
	 *             If the file does not exist.
	 */
	public static String readFile(final String fileName) throws FileNotFoundException {
		if (fileName == null || fileName.length() < 1) {
			throw new IllegalArgumentException("Filename may not be null or empty");
		}
		final File file = new File(fileName);
		return readFile(file);
	}

	public static String readFile(final File file) throws FileNotFoundException {
		AjahUtils.requireParam(file, "file");
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		final StringBuffer data = new StringBuffer();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			while (in.ready()) {
				final String line = in.readLine();
				data.append(line).append("\n");
			}
		} catch (final IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					log.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
		log.log(Level.FINER, data.length() + " bytes loaded.");
		return data.toString();
	}

	public static long copyFile(final File file, final File newFile) {
		InputStream in = null;
		OutputStream out = null;
		long bytes = 0;
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(newFile);
			int read;
			do {
				final byte[] block = new byte[1024];
				read = in.read(block);
				if (read < 0) {
					break;
				}
				bytes += read;
				out.write(block, 0, read);
			} while (read >= 0);
			log.fine("Copied " + bytes + " bytes");
			return bytes;
		} catch (final IOException e) {
			if (e.getMessage().endsWith("(Access is denied)")) {
				// log.info(e.getMessage());
			} else if (e.getMessage().equals("The process cannot access the file because another process has locked a portion of the file")) {
				// log.info(e.getMessage());
			} else {
				log.warning(file.getAbsolutePath());
				log.log(Level.WARNING, e.getMessage(), e);
			}
			return -1;
		} finally {
			IOUtils.safeClose(in);
			IOUtils.safeClose(out);
		}
	}

	public static int write(final File file, final List<String> lines) throws IOException {
		BufferedOutputStream out = null;
		try {
			file.getParentFile().mkdirs();
			out = new BufferedOutputStream(new FileOutputStream(file));
			for (final String line : lines) {
				out.write(line.getBytes());
				out.write('\n');
			}
			log.finest("Wrote " + lines.size() + " lines");
			return lines.size();
		} finally {
			IOUtils.safeClose(out);
		}
	}

	public static int write(final File file, final String string) throws IOException {
		BufferedOutputStream out = null;
		try {
			file.getParentFile().mkdirs();
			out = new BufferedOutputStream(new FileOutputStream(file));
			out.write(string.getBytes());
			log.fine("Wrote " + string.length() + " bytes");
			return string.length();
		} finally {
			IOUtils.safeClose(out);
		}
	}

	public static Long readFileAsLong(final File file, final Long defaultValue) {
		try {
			return Long.valueOf(readFile(file));
		} catch (final NumberFormatException e) {
			log.warning(e.getMessage());
			return defaultValue;
		} catch (final FileNotFoundException e) {
			log.warning(e.getMessage());
			return defaultValue;
		}
	}

}
