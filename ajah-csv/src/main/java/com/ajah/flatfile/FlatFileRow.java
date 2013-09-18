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

	private Map<String, FlatFileColumn> columns;
	private Map<FlatFileColumn, String> values = new HashMap<>();

	/**
	 * Create an empty row based on a column set.
	 * 
	 * @param columns
	 *            The columns in the row.
	 */
	public FlatFileRow(Map<String, FlatFileColumn> columns) {
		this.columns = columns;
	}

	/**
	 * Fetches the value of a column for this row.
	 * 
	 * @param name
	 *            The name of the column.
	 * @return The value of the column, or the default value if one is
	 *         configured.
	 */
	public String get(String name) {
		FlatFileColumn column = this.columns.get(name);
		if (column != null) {
			String value = this.values.get(column);
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
	 * Sets a column to a long value.
	 * 
	 * @param column
	 *            The name of the column.
	 * @param value
	 *            The value to set.
	 */
	public void set(String column, long value) {
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
	public void set(String column, String value) {
		FlatFileColumn flatFileColumn = this.columns.get(column);
		if (column != null) {
			this.values.put(flatFileColumn, value);
		} else {
			throw new IllegalArgumentException("Invalid column name: " + column);
		}
	}

	/**
	 * Sets a column to a int value.
	 * 
	 * @param column
	 *            The name of the column.
	 * @param value
	 *            The value to set.
	 */
	public void set(String column, int value) {
		// TODO Store as a number for formatting
		set(column, String.valueOf(value));
	}

	/**
	 * Fetches the value of a column as a double.
	 * 
	 * @see Double#parseDouble(String)
	 * @see #get(String)
	 * @param column
	 *            The name of the column
	 * @return The value, parsed as a double. Returns 0 if blank.
	 */
	public double getDouble(String column) {
		String value = get(column);
		if (StringUtils.isBlank(value)) {
			return 0;
		}
		return Double.parseDouble(get(column));
	}

	/**
	 * Sets a column to a Date value.
	 * 
	 * @param name
	 *            The name of the column.
	 * @param date
	 *            The value to set.
	 */
	public void set(String name, Date date) {
		set(name, dateTimeFormat.format(date));
	}

}
