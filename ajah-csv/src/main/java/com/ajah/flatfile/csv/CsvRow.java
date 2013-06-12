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

import java.util.List;

import lombok.extern.java.Log;

import com.ajah.util.StringUtils;

/**
 * Represents a row from a CSV file, functions similarly to a map.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class CsvRow {

	private List<String> fieldNames;
	private String[] fieldValues;

	/**
	 * Creates a row with a set of names and values.
	 * 
	 * @param fieldNames
	 *            The names of the fields (from the header row).
	 * @param fieldValues
	 *            The values of the fields (from the row).
	 */
	public CsvRow(List<String> fieldNames, String[] fieldValues) {
		this.fieldNames = fieldNames;
		this.fieldValues = fieldValues;
	}

	/**
	 * Returns the field value for a given name.
	 * 
	 * @param fieldName
	 *            The name to request.
	 * @return The value of the field, may be null or empty.
	 * @throws IllegalArgumentException
	 *             if the name requested is not a valid header.
	 */
	public String get(String fieldName) {
		int index = this.fieldNames.indexOf(fieldName);
		if (index < 0) {
			throw new IllegalArgumentException(fieldName + " is not in " + this.fieldNames.toString());
		}
		return this.fieldValues[index];
	}

	/**
	 * Returns the field value for a given name as an int. If the field is empty
	 * or not a number, returns a default value.
	 * 
	 * @param fieldName
	 *            The name of the field to return.
	 * @param defaultValue
	 *            The default value to return if the value is not a number.
	 * @return The value of the field as an int.
	 */
	public int getInt(String fieldName, int defaultValue) {
		int index = this.fieldNames.indexOf(fieldName);
		if (index < 0) {
			throw new IllegalArgumentException(fieldName + " is not in " + this.fieldNames.toString());
		}
		if (StringUtils.isBlank(this.fieldValues[index])) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(this.fieldValues[index]);
		} catch (NumberFormatException e) {
			log.finest("Invalid value " + this.fieldValues[index] + " returning " + defaultValue);
			return defaultValue;
		}
	}

	/**
	 * Returns the field value for a given name as an double. If the field is
	 * empty or not a number, returns a default value.
	 * 
	 * @param fieldName
	 *            The name of the field to return.
	 * @param defaultValue
	 *            The default value to return if the value is not a number.
	 * @return The value of the field as an double.
	 */
	public double getDouble(String fieldName, double defaultValue) {
		int index = this.fieldNames.indexOf(fieldName);
		if (index < 0) {
			throw new IllegalArgumentException(fieldName + " is not in " + this.fieldNames.toString());
		}
		if (StringUtils.isBlank(this.fieldValues[index])) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(this.fieldValues[index]);
		} catch (NumberFormatException e) {
			log.finest("Invalid value " + this.fieldValues[index] + " returning " + defaultValue);
			return defaultValue;
		}
	}

	/**
	 * Determines if the row defines the specified field.
	 * 
	 * @param fieldName
	 *            The name of the field.
	 * @return true if the field is defined(it may still be empty), otherwise
	 *         false.
	 */
	public boolean has(String fieldName) {
		return this.fieldNames.indexOf(fieldName) >= 0;
	}
	
}
