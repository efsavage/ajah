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

/**
 * Utilities for dealing with Strings.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public class StringUtils {

	/**
	 * The Non-breaking space character.
	 */
	public static final char NBSP = (char) 160;

	/**
	 * Shorthand method for passing varargs to methods requiring arrays.
	 * 
	 * @param strings
	 *            The array of strings.
	 * @return The same object, but cast as an array automatically by Java.
	 */
	public static String[] asArray(final String... strings) {
		return strings;
	}

	/**
	 * Capitalizes first letter of a String.
	 * 
	 * @see Character#toTitleCase(char)
	 * @param string
	 *            String to capitalized
	 * @return String, capitalized, may be null if null is passed in.
	 */
	public static String capitalize(final String string) {
		if (StringUtils.isBlank(string)) {
			return string;
		}
		return Character.toTitleCase(string.charAt(0)) + string.substring(1);
	}

	/**
	 * Checks a string with {@link String#endsWith(String)} against an array of
	 * candidate strings.
	 * 
	 * @param string
	 *            The string to check.
	 * @param suffixes
	 *            The array of suffixes to use.
	 * @return true if the string ends with any of the suffixes, otherwise
	 *         false.
	 */
	public static boolean endsWith(final String string, final String[] suffixes) {
		if (isBlank(string) || suffixes == null || suffixes.length == 0) {
			return false;
		}
		for (final String suffix : suffixes) {
			if (string.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Looks for null or empty strings.
	 * 
	 * @param string
	 *            String to be tested, may be null
	 * @return true if string is null or zero-length
	 */
	public static boolean isBlank(final String string) {
		if (string == null) {
			return true;
		}
		if (string.indexOf(NBSP) >= 0) {
			return isBlank(string.replace(NBSP, ' '));
		}
		return string.length() < 1 || string.trim().length() < 1;
	}

	/**
	 * Calls {@link #join(String[], String)} with a comma for a delimiter. Array
	 * of Strings, may be empty or null.
	 * 
	 * @param array
	 *            The array to be joined, may be empty or null.
	 * @return The joined array. If array was null or empty or contained only
	 *         null or empty Strings, returns null.
	 */
	public static String join(final String[] array) {
		return join(array, ",");
	}

	/**
	 * Joins an array of Strings into a single String with the specified
	 * delimeter.
	 * 
	 * @param array
	 *            Array of Strings, may be empty or null.
	 * @param delimiter
	 *            The delimiter to put between the joined Strings, may be empty
	 *            or null.
	 * @return The joined array with delimiter in between joined Strings. If
	 *         array was null or empty or contained only null or empty Strings,
	 *         returns null.
	 */
	public static String join(final String[] array, final String delimiter) {
		if (array == null || array.length == 0) {
			return null;
		} else if (array.length == 1) {
			return array[0];
		}
		final StringBuffer buf = new StringBuffer();
		boolean first = delimiter != null;
		for (int i = 0; i < array.length; i++) {
			if (!StringUtils.isBlank(array[i])) {
				if (!first) {
					buf.append(delimiter);
				} else {
					first = false;
				}
				buf.append(array[i]);
			}
		}
		if (buf.length() > 0) {
			return buf.toString();
		}
		return null;
	}

	/**
	 * Returns leftmost characters of a string.
	 * 
	 * @param string
	 *            String to be tested, may be null.
	 * @param length
	 *            The maximum length of the string to return.
	 * @return The leftmost characters of a string. If the string was null, or
	 *         shorter than the specified lenght, the original string will be
	 *         returned.
	 */
	public static String left(final String string, final int length) {
		if (string == null || string.length() < length) {
			return string;
		}
		return string.substring(0, length);
	}

	/**
	 * Returns length of string, 0 if null.
	 * 
	 * @param string
	 *            String to be tested, may be null.
	 * @return length of string, 0 if null.
	 */
	public static int safeLength(final String string) {
		return string == null ? 0 : string.length();
	}

	/**
	 * Returns toString() for the object passed, if it is not null, otherwise
	 * returns null.
	 * 
	 * @see Object#toString()
	 * @param object
	 *            The object to check/toString()
	 * @return object.toString() or null
	 */
	public static String safeToString(final Object object) {
		if (object == null) {
			return null;
		}
		return object.toString();
	}

	/**
	 * Converts camelCase text to regular text. Example: "canOfSoda" converts to
	 * "can of soda". Source: <a href=
	 * "http://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java"
	 * >Stack Overflow</a>
	 * 
	 * @param string
	 * @return String, de-camelcased.
	 */
	public static String splitCamelCase(final String string) {
		return string.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}

	/**
	 * Converts a string into a "clean" token suitable for URLs. It will be
	 * converted to lowercase, have non-word characters replaced with hyphens,
	 * and be split if it apepars to be camelcase.
	 * 
	 * @see #splitCamelCase(String)
	 * @param string
	 *            The string to convert.
	 * @return The converted string.
	 */
	public static String toCleanUrlToken(final String string) {
		if (StringUtils.isBlank(string)) {
			throw new IllegalArgumentException("Non-blank string required");
		}
		return splitCamelCase(string.trim()).toLowerCase().replaceAll("[\\W_]+", "-");
	}

	/**
	 * Truncates a string if it is longer than desired.
	 * 
	 * @param string
	 *            The string to truncate.
	 * @param maxLength
	 *            The maximum length of the string, must be greater than 1.
	 * @return The string truncated to maxLength characters if necessary,
	 *         otherwise will return the original string, including null.
	 */
	public static String truncate(final String string, final int maxLength) {
		if (StringUtils.isBlank(string)) {
			return string;
		}
		if (maxLength < 1) {
			throw new IllegalArgumentException("maxLength must be greater than 0");
		}
		if (string.length() > maxLength) {
			return string.substring(0, maxLength);
		}
		return string;
	}

	/**
	 * Joins a list of strings, separating them by delimeter if they are not
	 * blank.
	 * 
	 * @param delimiter
	 *            The delimiter to use between strings.
	 * @param strings
	 *            The list of strings to join.
	 * @return A joined list of strings, may be empty or null;
	 */
	public static String join(String delimiter, String... strings) {
		if (strings == null || strings.length < 1) {
			return null;
		}
		StringBuilder retVal = null;
		for (String string : strings) {
			if (StringUtils.isBlank(string)) {
				continue;
			}
			if (retVal == null) {
				retVal = new StringBuilder();
			} else {
				retVal.append(delimiter);
			}
			retVal.append(string);
		}
		return retVal == null ? null : retVal.toString();
	}

}
