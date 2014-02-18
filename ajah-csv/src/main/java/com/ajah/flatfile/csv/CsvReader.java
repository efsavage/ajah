/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.flatfile.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Provides a way to easily get data from a CSV file.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class CsvReader implements AutoCloseable, Iterable<CsvRow>, Iterator<CsvRow> {

	CSVReader reader = null;
	List<String> fieldNames = new ArrayList<>();
	String[] next;

	/**
	 * Creates a reader from a file reference. The file will remain open as long
	 * as this reader is open. The first line of the file must contain the
	 * column headings.
	 * 
	 * @param file
	 *            The file to open and read from.
	 * @throws IOException
	 *             If the file could not be opened.
	 */
	public CsvReader(final File file) throws IOException {
		this.reader = new CSVReader(new FileReader(file));
		final String[] headers = this.reader.readNext();
		for (final String rawHeader : headers) {
			final String header = rawHeader.trim();
			if (this.fieldNames.contains(header)) {
				throw new IllegalArgumentException("Field " + header + " is defined twice");
			}
			this.fieldNames.add(header.trim());
		}
		this.next = this.reader.readNext();
	}

	@Override
	public void close() throws IOException {
		if (this.reader != null) {
			this.reader.close();
		}
	}

	@Override
	public boolean hasNext() {
		return this.next != null;
	}

	/**
	 * This is here so we can use it in enhanced for loops.
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<CsvRow> iterator() {
		return this;
	}

	@Override
	public CsvRow next() {
		final CsvRow row = new CsvRow(this.fieldNames, this.next);
		try {
			this.next = this.reader.readNext();
		} catch (final IOException e) {
			throw new IllegalArgumentException(e);
		}
		return row;
	}

	/**
	 * Unsupported.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
