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
package com.ajah.util.reflect;

import java.lang.reflect.Field;
import java.util.Date;

import com.ajah.util.AjahUtils;
import com.ajah.util.FromStringable;
import com.ajah.util.Identifiable;
import com.ajah.util.ToStringable;

/**
 * Java reflection utilities.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class IntrospectionUtils {

	/**
	 * Checks to see if the field's type is a {@link String}.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type is a String
	 */
	public static boolean isString(final Field field) {
		AjahUtils.requireParam(field, "field");
		return String.class.isAssignableFrom(field.getType());
	}

	/**
	 * Checks to see if the field's type is a {@link Date}.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type is a Date
	 */
	public static boolean isDate(final Field field) {
		AjahUtils.requireParam(field, "field");
		return Date.class.isAssignableFrom(field.getType());
	}

	/**
	 * Checks to see if the field's type implements {@link ToStringable}.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type implements ToStringable
	 */
	public static boolean isToStringable(final Field field) {
		AjahUtils.requireParam(field, "field");
		return ToStringable.class.isAssignableFrom(field.getType());
	}

	/**
	 * Checks to see if the field's type implements {@link Identifiable}.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type implements Identifiable
	 */
	public static boolean isIdentifiable(final Field field) {
		AjahUtils.requireParam(field, "field");
		return Identifiable.class.isAssignableFrom(field.getType());
	}

	/**
	 * Checks to see if the field's type is an {@link Enum}. Note: There is an
	 * easier method that does this already, {@link Class#isEnum()}, this method
	 * is just included here for consistency.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type is an Enum
	 */
	public static boolean isEnum(final Field field) {
		AjahUtils.requireParam(field, "field");
		return field.getType().isEnum();
	}

	/**
	 * Checks to see if the field's type implements {@link FromStringable}.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type implements FromStringable
	 */
	public static boolean isFromStringable(final Field field) {
		AjahUtils.requireParam(field, "field");
		return FromStringable.class.isAssignableFrom(field.getType());
	}

	/**
	 * Checks to see if the field's type is an int.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type is an int
	 */
	public static boolean isInt(final Field field) {
		AjahUtils.requireParam(field, "field");
		return int.class.isAssignableFrom(field.getType()) || Integer.class.isAssignableFrom(field.getType());
	}

	/**
	 * Checks to see if the field's type is a long.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type is a long
	 */
	public static boolean isLong(final Field field) {
		AjahUtils.requireParam(field, "field");
		return long.class.isAssignableFrom(field.getType()) || Long.class.isAssignableFrom(field.getType());
	}

	/**
	 * Checks to see if the field's type implements {@link Identifiable} and is
	 * an {@link Enum}.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type implements Identifiable and is an Enum
	 */
	public static boolean isIdentifiableEnum(final Field field) {
		return isIdentifiable(field) && isEnum(field);
	}

	/**
	 * Checks to see if the field's type is a boolean.
	 * 
	 * @param field
	 *            The field to check the type of, required.
	 * @return true if the field's type is a long
	 */
	public static boolean isBoolean(Field field) {
		AjahUtils.requireParam(field, "field");
		return boolean.class.isAssignableFrom(field.getType());
	}

}
