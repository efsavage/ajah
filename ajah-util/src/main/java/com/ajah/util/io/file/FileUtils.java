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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities for basic file operations.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class FileUtils {

	private static Logger log = Logger.getLogger(FileUtils.class.getName());

	/**
	 * Reads a file and returns the lines of it as a list of strings. Handles
	 * opening a closing the file. If there are any errors other than that the
	 * file does not exist, will return an empty or partial list.
	 * 
	 * Will ignore any line that starts with "#".
	 * 
	 * @param fileName
	 *            The name of the file to load, required.
	 * @return List of lines in the file.
	 * @throws FileNotFoundException
	 *             If the file does not exist.
	 */
	public static List<String> readFile(final String fileName) throws FileNotFoundException {
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
				if (!line.startsWith("#") && line.length() > 0) {
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

}