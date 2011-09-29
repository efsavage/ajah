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
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Iterates over a {@link BufferedReader}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class BufferedReaderIterator implements Iterator<String> {

	private static final Logger log = Logger.getLogger(BufferedReaderIterator.class.getName());

	private BufferedReader bufferedReader;

	/**
	 * Creates an iterator over a {@link LineReader}.
	 * 
	 * @param bufferedReader
	 *            The line reader to iterate over.
	 */
	public BufferedReaderIterator(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	/**
	 * Returns value of {@link BufferedReader#ready()}. Catches
	 * {@link IOException} and returns false.
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		try {
			return this.bufferedReader.ready();
		} catch (IOException e) {
			log.log(Level.WARNING, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Returns value of {@link BufferedReader#ready()}. Catches
	 * {@link IOException} and returns null.
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public String next() {
		try {
			return this.bufferedReader.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Throws {@link UnsupportedOperationException}.
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove() is not supported, collection is read-only");
	}

}
