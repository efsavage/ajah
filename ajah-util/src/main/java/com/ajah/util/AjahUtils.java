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
package com.ajah.util;

import java.util.Collections;
import java.util.Date;

/**
 * The lowest-of-low level utilities, basically "things I wish were in the JDK".
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class AjahUtils {

	/**
	 * Array of lowercase characters (a-z).
	 */
	public static final char[] LOWER_CHARS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * Check a parameter and throw an exception if its value is not greater than
	 * or equal to the required value.Commonly used at the very beginning of a
	 * method for things that should never be zero or negative.
	 * 
	 * @param parameter
	 *            Object to check.
	 * @param name
	 *            Name of the parameter (for the error message)
	 * @param min
	 *            Minimum value of the parameter.
	 * @throws IllegalArgumentException
	 *             If parameter is below the minimum
	 */
	public static void requireParam(final long parameter, final String name, final int min) throws IllegalArgumentException {
		if (parameter < min) {
			throw new IllegalArgumentException(name + " is " + parameter + " but must be at least " + min);
		}
	}

	/**
	 * Check a parameter and throw an exception if its null. Commonly used at
	 * the very beginning of a method for things that should never be null. If
	 * the object is a String, it will treat empty String the same as a null.
	 * 
	 * @param parameter
	 *            Object to check.
	 * @param name
	 *            Name of the parameter (for the error message)
	 * @throws IllegalArgumentException
	 *             If parameter is null
	 * @throws IllegalArgumentException
	 *             If parameter is a String and is empty
	 */
	public static void requireParam(final Object parameter, final String name) throws IllegalArgumentException {
		if (parameter == null) {
			throw new IllegalArgumentException(name + " cannot be null");
		}
		if (parameter instanceof String && ((String) parameter).length() < 1) {
			throw new IllegalArgumentException(name + " cannot be empty");
		}
	}

	/**
	 * Checks for a null array. If the parameter is null, returns an empty array
	 * so enhanced for loops don't NPE. This will create a new, empty array, so
	 * if you're concerned about creating extra objects, don't use this where
	 * that will happen often.
	 * 
	 * @param array
	 *            Array to check, may be null.
	 * @return The original parameter, or an empty array.
	 */
	public static String[] safeArray(final String[] array) {
		if (array != null) {
			return array;
		}
		return new String[0];
	}

	/**
	 * Parses a String for an integer value via {@link #safeInt(String, int)}
	 * with defaultValue of zero. Returns 0 if the string is null or empty or a
	 * parsing error occurs.
	 * 
	 * @param string
	 *            The string to parse.
	 * @return The parsed integer, or 0.
	 */
	public static int safeInt(final String string) {
		return safeInt(string, 0);
	}

	/**
	 * Parses a String for an integer value. Returns a defaultValue if the
	 * string is null or empty or a parsing error occurs.
	 * 
	 * @param string
	 *            The string to parse.
	 * @param defaultValue
	 *            The value to return if a number cannot be parsed from the
	 *            String.
	 * @return The parsed integer, or the defaultValue.
	 */
	public static int safeInt(final String string, final int defaultValue) {
		if (StringUtils.isBlank(string)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(string);
		} catch (final NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Checks for a null iterable object. If the parameter is null, returns an
	 * empty list so enhanced for loops don't NPE.
	 * 
	 * @param iterable
	 *            Iterable to check, may be null.
	 * @return The original parameter, or an empty list.
	 */
	public static <T> Iterable<T> safeIterable(final Iterable<T> iterable) {
		if (iterable != null) {
			return iterable;
		}
		return Collections.emptyList();
	}

	/**
	 * Handles a Long and returns a valid value. Uses
	 * {@link #safeLong(Long, long, long, long)} with the defaultValue set to
	 * minValue.
	 * 
	 * @param value
	 *            The candidate value.
	 * @param minValue
	 *            The minimum value to return (does not apply to defaultValue)
	 * @param maxValue
	 *            The maximum value to return (does not apply to defaultValue)
	 * @return The value, or the minimum value if that is null, or the minimum
	 *         or maximum value if the candidate does not fall between them.
	 */
	public static long safeLong(final Long value, final long minValue, final long maxValue) {
		return safeLong(value, minValue, minValue, maxValue);
	}

	/**
	 * Handles a Long and returns a valid value.
	 * 
	 * @param value
	 *            The candidate value.
	 * @param defaultValue
	 *            The value if the candidate is null.
	 * @param minValue
	 *            The minimum value to return (does not apply to defaultValue)
	 * @param maxValue
	 *            The maximum value to return (does not apply to defaultValue)
	 * @return The value, or the default value if that is null, or the minimum
	 *         or maximum value if the candidate does not fall between them.
	 */
	public static long safeLong(final Long value, final long defaultValue, final long minValue, final long maxValue) {
		if (value == null) {
			return defaultValue;
		}
		final long retVal = value.longValue();
		if (retVal < minValue) {
			return minValue;
		}
		if (retVal > maxValue) {
			return maxValue;
		}
		return retVal;
	}

	/**
	 * Returns {@link #toString()} on the parameter if the parameter is not
	 * null, otherwise returns null.
	 * 
	 * @param object
	 *            The object to call {@link #toString()} on.
	 * @return The object's {@link #toString()} return value, or null.
	 */
	public static String safeToString(final Object object) {
		if (object == null) {
			return null;
		}
		return object.toString();
	}

	/**
	 * Converts a Java Date into a unix-compatible timestamp (seconds
	 * precision).
	 * 
	 * @param date
	 *            Date to convert, may be null.
	 * @return Value of Date in seconds, will return null if null is passed in.
	 */
	public static Long toUnix(final Date date) {
		if (date == null) {
			return null;
		}
		return Long.valueOf(date.getTime() / 1000);
	}

	private AjahUtils() {
		// Empty, private constructor
	}

}
