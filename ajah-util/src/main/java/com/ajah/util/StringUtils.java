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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
	 * Performs a {@link String#split(String)} but {@link String#trim()}s all
	 * values and omits blank values.
	 * 
	 * @param string
	 *            The string to split. If null, an empty list will be returned.
	 * @param regex
	 *            The pattern to split on. This should not contain whitespace.
	 * @return List of trimmed, non-blank tokens.
	 */
	public static List<String> cleanSplit(final String string, final String regex) {
		if (isBlank(string)) {
			return Collections.emptyList();
		}
		final List<String> list = new ArrayList<>();
		for (final String token : string.split(regex)) {
			if (!isBlank(token)) {
				list.add(token.trim());
			}
		}
		return list;
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
	 * Joins a list of strings, separating them by delimeter if they are not
	 * blank.
	 * 
	 * @param delimiter
	 *            The delimiter to use between strings.
	 * @param strings
	 *            The list of strings to join.
	 * @return A joined list of strings, may be empty or null;
	 */
	public static String join(final String delimiter, final Collection<String> strings) {
		if (strings == null || strings.size() < 1) {
			return null;
		}
		StringBuilder retVal = null;
		for (final String string : strings) {
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

	/**
	 * Joins a list of ints, separating them by delimiter if they are not blank.
	 * 
	 * @param delimiter
	 *            The delimiter to use between numbers.
	 * @param numbers
	 *            The list of numbers to join.
	 * @return A joined list of numbers, may be empty or null;
	 */
	public static String join(final String delimiter, final int... numbers) {
		if (numbers == null || numbers.length < 1) {
			return null;
		}
		StringBuilder retVal = null;
		for (final int number : numbers) {
			if (retVal == null) {
				retVal = new StringBuilder();
			} else {
				retVal.append(delimiter);
			}
			retVal.append(number);
		}
		return retVal == null ? null : retVal.toString();
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
	public static String join(final String delimiter, final String... strings) {
		if (strings == null || strings.length < 1) {
			return null;
		}
		StringBuilder retVal = null;
		for (final String string : strings) {
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
	@Deprecated
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
	 *            String to be chopped, may be null.
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
	 * Returns rightmost characters of a string.
	 * 
	 * @param string
	 *            String to be chopped, may be null.
	 * @param length
	 *            The maximum length of the string to return.
	 * @return The leftmost characters of a string. If the string was null, or
	 *         shorter than the specified lenght, the original string will be
	 *         returned.
	 */
	public static String right(final String string, final int length) {
		if (string == null || string.length() < length) {
			return string;
		}
		final int fullLength = string.length();
		return string.substring(fullLength - length, fullLength);
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
	 * Trims a string, accepting null values. If a null value is passed, returns
	 * an empty string. Useful for concatenating so you don't get literal "null"
	 * strings.
	 * 
	 * @param string
	 *            The string to trim, may be null.
	 * @return The trimmed string.
	 */
	public static String safeTrim(final String string) {
		if (string == null) {
			return "";
		}
		return string.trim();
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
	 * Limits a string to a set of choices, with a default value if there is no
	 * match.
	 * 
	 * @param string
	 *            The string to match.
	 * @param validStrings
	 *            The array of valid strings.
	 * @param defaultValue
	 *            The default value if there is no match.
	 * @return The string if it is valid, otherwise the default value.
	 */
	public static String whitelist(final String string, final String[] validStrings, final String defaultValue) {
		AjahUtils.requireParam(validStrings, "validStrings");
		if (string == null) {
			return defaultValue;
		}
		for (final String validString : validStrings) {
			if (string.equals(validString)) {
				return string;
			}
		}
		return defaultValue;
	}

	/**
	 * Wraps each string in a collection with another string, often quotes or
	 * parentheses.
	 * 
	 * @param strings
	 *            The strings to wrap.
	 * @param wrapper
	 *            The wrapper to prepend and append.
	 * @return A new collection of the same size, with the wrapper prepended and
	 *         appended to each member.
	 */
	public static List<String> wrap(List<String> strings, String wrapper) {
		if (strings == null) {
			return null;
		}
		List<String> wrapped = new ArrayList<>(strings.size());
		for (String string : strings) {
			wrapped.add(wrapper + string + wrapper);
		}
		return wrapped;
	}

}
