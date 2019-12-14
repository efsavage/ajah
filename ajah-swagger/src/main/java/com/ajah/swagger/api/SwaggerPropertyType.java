/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.swagger.api;

import com.ajah.util.IdentifiableEnum;

/**
 * Valid types of SwaggerProperty entities.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum SwaggerPropertyType implements IdentifiableEnum<String> {

	/**
	 * Special.
	 */
	DEFINITION("0", "def", "Definition", "Definition."),
	/**
	 * Special.
	 */
	INTEGER("1", "integer", "INTEGER", "INTEGER."),
	/**
	 * LONG.
	 */
	LONG("2", "long", "LONG", "LONG."),
	/**
	 * FLOAT.
	 */
	FLOAT("3", "float", "FLOAT", "FLOAT."),
	/**
	 * DOUBLE.
	 */
	DOUBLE("4", "double", "DOUBLE", "DOUBLE."),
	/**
	 * STRING.
	 */
	STRING("5", "string", "STRING", "STRING."),
	/**
	 * BYTE.
	 */
	BYTE("6", "byte", "BYTE", "BYTE."),
	/**
	 * BOOLEAN.
	 */
	BOOLEAN("7", "boolean", "BOOLEAN", "BOOLEAN."),
	/**
	 * DATE.
	 */
	DATE("8", "date", "DATE", "DATE."),
	/**
	 * DATE_TIME.
	 */
	DATE_TIME("9", "date_time", "DATE_TIME", "DATE_TIME."),
	/**
	 * DATE_TIME.
	 */
	LIST("10", "list", "List (Ordered Items)", "List."),
	/**
	 * DATE_TIME.
	 */
	SET("11", "set", "Set (Unique Items)", "Set.");

	/**
	 * Finds a SwaggerPropertyType that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching SwaggerPropertyType, or null.
	 */
	public static SwaggerPropertyType get(final String string) {
		for (final SwaggerPropertyType type : values()) {
			if (type.getId().equals(string) || type.getCode().equals(string) || type.name().equals(string) || type.getName().equals(string)) {
				return type;
			}
		}
		return null;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String description;

	SwaggerPropertyType(final String id, final String code, final String name, final String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	/**
	 * The short, display-friendly code of the type. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the type. Cannot be null.
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * The display-friendly description of the type.
	 * 
	 * @return The display-friendly description of the type. May be null.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * The internal ID of the type.
	 * 
	 * @return The internal ID of the type. Cannot be null.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The display-friendly name of the type. If no name is applicable, it
	 * should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the type. Cannot be null.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
