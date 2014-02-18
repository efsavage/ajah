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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ajah.util.StringUtils;

/**
 * Represents a row of a flat-file.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class FlatFileRow {

	private static DateFormat dateTimeFormat = DateFormat.getDateTimeInstance();

	private final Map<String, FlatFileColumn> columns;
	private final Map<FlatFileColumn, String> values = new HashMap<>();

	private FlatFileReader reader;
	private FlatFileWriter writer;

	/**
	 * Create an empty row based on a column set.
	 * 
	 * @param columns
	 *            The columns in the row.
	 * @param reader
	 *            The reader that created this row.
	 */
	public FlatFileRow(final Map<String, FlatFileColumn> columns, final FlatFileReader reader) {
		this.columns = columns;
		this.reader = reader;
	}

	/**
	 * Create an empty row based on a column set.
	 * 
	 * @param columns
	 *            The columns in the row.
	 * @param writer
	 *            The writer that created this row.
	 */
	public FlatFileRow(final Map<String, FlatFileColumn> columns, final FlatFileWriter writer) {
		this.columns = columns;
		this.writer = writer;
	}

	/**
	 * Fetches the value of a column for this row.
	 * 
	 * @param name
	 *            The name of the column.
	 * @return The value of the column, or the default value if one is
	 *         configured.
	 */
	public String get(final String name) {
		final FlatFileColumn column = this.columns.get(name);
		if (column != null) {
			final String value = this.values.get(column);
			if (!StringUtils.isBlank(value)) {
				return value;
			}
			if (!StringUtils.isBlank(column.getDefaultValue())) {
				return column.getDefaultValue();
			}
		}
		return "";
	}

	/**
	 * Fetches the value of a column as a BigDecimal.
	 * 
	 * @see BigDecimal#BigDecimal(String)
	 * @see #get(String)
	 * @param column
	 *            The name of the column
	 * @return The value, parsed as a BigDecimal. Value is 0 if blank.
	 * @throws NumberFormatException
	 *             If the number could not be parsed.
	 */
	public BigDecimal getBigDecimal(final String column) {
		final String value = get(column);
		if (StringUtils.isBlank(value)) {
			return new BigDecimal(0);
		}
		return new BigDecimal(value);
	}

	/**
	 * Fetches the value of a column as a double.
	 * 
	 * @see Double#parseDouble(String)
	 * @see #get(String)
	 * @param column
	 *            The name of the column
	 * @return The value, parsed as a double. Returns 0 if blank.
	 * @throws NumberFormatException
	 *             If the number could not be parsed.
	 */
	public double getDouble(final String column) {
		final String value = get(column);
		if (StringUtils.isBlank(value)) {
			return 0;
		}
		return Double.parseDouble(value);
	}

	/**
	 * Fetches the value of a column as an integer.
	 * 
	 * @see Integer#parseInt(String)
	 * @see #get(String)
	 * @param column
	 *            The name of the column
	 * @return The value, parsed as an int. Returns 0 if blank.
	 * @throws NumberFormatException
	 *             If the number could not be parsed.
	 */
	public int getInt(final String column) {
		final String value = get(column);
		if (StringUtils.isBlank(value)) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	/**
	 * Fetches the value of a column as an integer with a default value.
	 * 
	 * @see Integer#parseInt(String)
	 * @see #get(String)
	 * @param column
	 *            The name of the column
	 * @param defaultValue
	 *            The default value if an integer cannot be parsed.
	 * @return The value, parsed as an int. Returns the default value if blank
	 *         or if it cannot be parsed as a number.
	 */
	public int getInt(final String column, final int defaultValue) {
		final String value = get(column);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (final NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * The reader for this row.
	 * 
	 * @return the reader that created this row.
	 */
	public FlatFileReader getReader() {
		return this.reader;
	}

	/**
	 * The writer for this row.
	 * 
	 * @return the writer that created this row.
	 */
	public FlatFileWriter getWriter() {
		return this.writer;
	}

	/**
	 * Sets a column to a Date value.
	 * 
	 * @param name
	 *            The name of the column.
	 * @param date
	 *            The value to set.
	 */
	public void set(final String name, final Date date) {
		set(name, dateTimeFormat.format(date));
	}

	/**
	 * Sets a column to a int value.
	 * 
	 * @param column
	 *            The name of the column.
	 * @param value
	 *            The value to set.
	 */
	public void set(final String column, final int value) {
		// TODO Store as a number for formatting
		set(column, String.valueOf(value));
	}

	/**
	 * Sets a column to a long value.
	 * 
	 * @param column
	 *            The name of the column.
	 * @param value
	 *            The value to set.
	 */
	public void set(final String column, final long value) {
		// TODO Store as a number for formatting
		set(column, String.valueOf(value));
	}

	/**
	 * Sets a column to a string value.
	 * 
	 * @param column
	 *            The name of the column.
	 * @param value
	 *            The value to set.
	 */
	public void set(final String column, final String value) {
		final FlatFileColumn flatFileColumn = this.columns.get(column);
		if (column != null) {
			this.values.put(flatFileColumn, value);
		} else {
			throw new IllegalArgumentException("Invalid column name: " + column);
		}
	}

}
