/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.spring.jdbc.err;

/**
 * Thrown when a query contain a value that is out of the range of the column it
 * should be stored in.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class OutOfRangeValueException extends QuerySyntaxException {

	/**
	 * Column name constructor.
	 * 
	 * @param columnName
	 *            The name of the empty column.
	 * @param tableName
	 * @param e
	 */
	public OutOfRangeValueException(final String columnName, final String tableName, final Throwable e) {
		super(tableName + "." + columnName, e);
	}

}
