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
package com.ajah.flatfile;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import com.ajah.util.StringUtils;

/**
 * Reads a flat data file into a structured iterator.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class FlatFileReader implements Closeable, Iterable<FlatFileRow>, Iterator<FlatFileRow> {

	private final BufferedReader reader;
	@Getter
	private final List<FlatFileColumn> columns = new ArrayList<>();
	private final Map<String, FlatFileColumn> map = new HashMap<>();
	private FlatFileRow row = null;
	@Getter
	private final FlatFileFormat format;
	@Getter
	@Setter
	private boolean flushEveryLine;

	/**
	 * Constructs a reader from a file.
	 * 
	 * @param format
	 *            The format of the file.
	 * @param file
	 *            The file.
	 * @throws IOException
	 *             If the file could not be read.
	 */
	public FlatFileReader(FlatFileFormat format, File file) throws IOException {
		this(format, new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8")));
	}

	/**
	 * Constructs a reader from a file.
	 * 
	 * @param format
	 *            The format of the file.
	 * @param inputStream
	 *            The input stream for the file.
	 * @throws IOException
	 *             If the file could not be read.
	 */
	public FlatFileReader(FlatFileFormat format, InputStream inputStream) throws IOException {
		this(format, new BufferedReader(new InputStreamReader(inputStream)));
	}

	/**
	 * Constructs a reader from a file.
	 * 
	 * @param format
	 *            The format of the file.
	 * @param bufferedReader
	 *            The buffered reader for the file.
	 * @throws IOException
	 *             If the file could not be read.
	 */
	public FlatFileReader(FlatFileFormat format, BufferedReader bufferedReader) throws IOException {
		this.format = format;
		this.reader = bufferedReader;
		String header = this.reader.readLine();
		if (header != null) {
			createColumns(header);
		}
	}

	private void createColumns(String header) {
		log.fine("Adding columns");
		switch (this.format) {
		case CSV: {
			if (header.contains("\"")) {
				throw new UnsupportedOperationException("Header contains double quote: " + header);
			}
			String[] names = header.split(",");
			for (String name : names) {
				addColumn(name);
			}
			break;
		}
		case TAB: {
			String[] names = header.split("\t");
			for (String name : names) {
				addColumn(name);
			}
			break;
		}
		default:
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void close() throws IOException {
		this.reader.close();
	}

	@Override
	public Iterator<FlatFileRow> iterator() {
		return this;
	}

	private void addColumn(String name) {
		if (this.map.get(name) != null) {
			throw new IllegalArgumentException("Duplicate column name " + name);
		}
		FlatFileColumn column = new FlatFileColumn(name, null, false);
		this.columns.add(column);
		this.map.put(column.getName(), column);
		log.fine("Created column \"" + name + "\"");
	}

	@Override
	public boolean hasNext() {
		try {
			return this.reader.ready();
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public FlatFileRow next() {
		this.row = new FlatFileRow(this.map, this);
		String line;
		try {
			line = this.reader.readLine();
		} catch (IOException e) {
			return null;
		}
		switch (this.format) {
		case CSV: {
			int currentPos = 0;
			int fieldStartPos = 0;
			boolean inField = false;
			char[] chars = line.toCharArray();
			int len = chars.length;
			for (int i = 0; i < this.columns.size() && currentPos < len; i++) {
				char c = chars[currentPos];
				if (c == ',' || c == '"') {
				} else {
					if (inField) {
						fieldStartPos = currentPos;
						currentPos++;
						inField = true;
					}

				}
			}
			if (line.contains("\"\"")) {
				throw new UnsupportedOperationException("Line contains double quote: " + line);
			}
			String[] values = line.split(",");
			for (int i = 0; i < values.length; i++) {
				if (StringUtils.isBlank(values[i])) {
					this.row.set(this.columns.get(i).getName(), "");
				} else if (values[i].charAt(0) == '"' && values[i].charAt(values[i].length() - 1) == '"') {
					this.row.set(this.columns.get(i).getName(), values[i].substring(1, values[i].length() - 2));
				} else {
					this.row.set(this.columns.get(i).getName(), values[i]);
				}
			}
			break;
		}
		case TAB: {
			String[] values = line.split("\t");
			for (int i = 0; i < values.length; i++) {
				this.row.set(this.columns.get(i).getName(), values[i]);
			}
			break;
		}
		default:
			throw new UnsupportedOperationException();
		}
		return this.row;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Determines if this reader has a column available.
	 * 
	 * @param column
	 *            The column name to check.
	 * @return true if the column appears in the file, otherwise false.
	 */
	public boolean hasColumn(String column) {
		return this.map.get(column) != null;
	}

	/**
	 * Validates that required columns are present in the file.
	 * 
	 * @param requiredColumns
	 *            The array of required columns, may be empty or null.
	 * @throws MissingColumnException
	 *             Thrown if columns are missing.
	 */
	public void require(String[] requiredColumns) throws MissingColumnException {
		if (requiredColumns == null || requiredColumns.length < 1) {
			return;
		}
		ArrayList<String> missingColumns = new ArrayList<>();
		for (String requiredColumn : requiredColumns) {
			if (!hasColumn(requiredColumn)) {
				missingColumns.add(requiredColumn);
			}
		}
		if (missingColumns.size() > 0) {
			throw new MissingColumnException(StringUtils.join(",", missingColumns), missingColumns);
		}
	}

	/**
	 * Validates that the file only contains supported columns.
	 * 
	 * @param supportedColumns
	 *            The columns supported by whatever is processing the file.
	 * @throws UnsupportedColumnException
	 *             If the file contains unsupported columns.
	 */
	public void validate(String[] supportedColumns) throws UnsupportedColumnException {
		if (supportedColumns == null || supportedColumns.length < 1) {
			throw new UnsupportedColumnException(StringUtils.join(",", this.map.keySet()), this.map.keySet());
		}
		Set<String> unsupportedColumns = new HashSet<>();
		columnLoop: for (String column : this.map.keySet()) {
			for (String supportedColumn : supportedColumns) {
				if (column.equals(supportedColumn)) {
					continue columnLoop;
				}
			}
			unsupportedColumns.add(column);
		}
		if (unsupportedColumns.size() > 0) {
			throw new UnsupportedColumnException(StringUtils.join(",", unsupportedColumns), unsupportedColumns);
		}
	}

}
