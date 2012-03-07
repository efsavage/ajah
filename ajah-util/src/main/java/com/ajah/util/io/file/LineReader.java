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
import java.util.Iterator;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class LineReader extends BufferedReader implements Iterable<Line> {

	/**
	 * Creates a {@link BufferedReader} over a {@link FileReader} for the given
	 * file.
	 * 
	 * @param file
	 *            The file to read.
	 * @throws FileNotFoundException
	 *             If the file could not be located.
	 */
	public LineReader(final File file) throws FileNotFoundException {
		super(new FileReader(file));
	}

	/**
	 * Creates a {@link BufferedReader} over a {@link FileReader} for the given
	 * file name.
	 * 
	 * @param fileName
	 *            The name of the file to read.
	 * @throws FileNotFoundException
	 *             If the file could not be found.
	 */
	public LineReader(final String fileName) throws FileNotFoundException {
		super(new FileReader(fileName));
	}

	/**
	 * Returns a {@link BufferedReaderIterator} of this file.
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Line> iterator() {
		return new LineIterator(this);
	}

}
