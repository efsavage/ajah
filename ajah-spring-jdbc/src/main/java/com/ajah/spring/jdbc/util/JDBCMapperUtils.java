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
package com.ajah.spring.jdbc.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.springframework.core.annotation.AnnotationUtils;

import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class JDBCMapperUtils {

	private static final Map<Class<?>, String> tableNameCache = new HashMap<>();
	private static final Map<String, Map<Field, String>> columnNameCache = new HashMap<>();

	/**
	 * Converts a field name to a table column name, per standard database
	 * naming convention.
	 * 
	 * @param fieldName
	 *            The name of the field to derive the name from.
	 * @return Column name
	 */
	public static String getColumnName(final String fieldName) {
		return StringUtils.splitCamelCase(fieldName).replaceAll("\\W+", "_").toLowerCase();
	}

	/**
	 * Converts a field name to a table column name, per standard database
	 * naming convention.
	 * 
	 * @param tableName
	 *            The table the column will be in.
	 * @param field
	 *            The field to derive the name from.
	 * @return Column name
	 */
	public static String getColumnName(final String tableName, final Field field) {
		if (columnNameCache.get(tableName) == null) {
			columnNameCache.put(tableName, new HashMap<Field, String>());
		}
		String columnName = columnNameCache.get(tableName).get(field);
		if (columnName != null) {
			return columnName;
		}

		columnName = getColumnName(field.getName());
		if (field.getName().equals("id")) {
			columnName = tableName + "_id";
		} else if (field.getType().isEnum()) {
			// Enums are stored as-is, even if they implement other interfaces
		} else if (Date.class.isAssignableFrom(field.getType())) {
			columnName += "_date";
		} else if (LocalDate.class.isAssignableFrom(field.getType())) {
			columnName += "_date";
		} else if (!field.getType().isEnum() && Identifiable.class.isAssignableFrom(field.getType())) {
			columnName += "_id";
		}
		columnNameCache.get(tableName).put(field, columnName);
		return columnName;
	}

	/**
	 * Converts a class name to a table name, per standard database naming
	 * convention.
	 * 
	 * Will ignore "Impl" at the end of class names.
	 * 
	 * @param clazz
	 *            Class to derive name from.
	 * @return Table name
	 */
	public static String getTableName(final Class<?> clazz) {
		if (tableNameCache.get(clazz) != null) {
			return tableNameCache.get(clazz);
		}
		if (AnnotationUtils.isAnnotationDeclaredLocally(Table.class, clazz)) {
			final Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
			if (!StringUtils.isBlank(table.name())) {
				return table.name();
			}
		}
		String simpleName = clazz.getSimpleName();
		if (simpleName.endsWith("Impl")) {
			simpleName = simpleName.substring(0, simpleName.length() - 4);
		}
		final String name = StringUtils.splitCamelCase(simpleName).replaceAll("\\W+", "_").toLowerCase();
		tableNameCache.put(clazz, name);
		return name;
	}
}
