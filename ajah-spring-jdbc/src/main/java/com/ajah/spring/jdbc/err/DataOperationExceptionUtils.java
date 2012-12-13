/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.java.Log;

import org.springframework.dao.DataAccessException;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class DataOperationExceptionUtils {

	static final Pattern mysqlUnknownColumn = Pattern.compile("Unknown column '(.*?)' in 'field list'");
	static final Pattern mysqlUnknownTable = Pattern.compile("Table '(.*?)' doesn't exist'");

	/**
	 * Translates a Spring JDBC exception to an Ajah exception.
	 * 
	 * @param e
	 *            The Spring JDBC exception.
	 * @param tableName
	 * @return The Equivalent Ajah exception;
	 */
	public static DataOperationException translate(final DataAccessException e, final String tableName) {
		if (e instanceof org.springframework.dao.DuplicateKeyException) {
			return new DuplicateKeyException(e);
		} else if (e instanceof org.springframework.jdbc.BadSqlGrammarException) {
			if (e.getCause() != null && e.getCause().getMessage().matches(mysqlUnknownColumn.pattern())) {
				final Matcher matcher = mysqlUnknownColumn.matcher(e.getCause().getMessage());
				matcher.find();
				final String columnName = matcher.group(1);
				return new UnknownColumnException(columnName, tableName, e);
			} else if (e.getCause() != null && e.getCause().getMessage().matches(mysqlUnknownTable.pattern())) {
				final Matcher matcher = mysqlUnknownTable.matcher(e.getCause().getMessage());
				matcher.find();
				final String fullTableName = matcher.group(1);
				return new UnknownTableException(fullTableName, e);
			}
			System.out.println(e.getCause().getMessage());
			return new QuerySyntaxException(e);
		}
		log.warning("Unknown: " + e.getClass().getName());
		return new UnknownDataOperationException(e);
	}

}
