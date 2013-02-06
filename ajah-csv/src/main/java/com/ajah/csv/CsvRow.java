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
package com.ajah.csv;

import java.util.List;

/**
 * Represents a row from a CSV file, functions similarly to a map.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
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
}
