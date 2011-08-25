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
 * @author Eric F. Savage <code@efsavage.com>
 */
public class AjahUtils {

	/**
	 * Array of lowercase characters (a-z).
	 */
	public static final char[] LOWER_CHARS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * Check a parameter and throw an exception if its null. Commonly used at
	 * the very beginning of a method for things that should never be null. If
	 * the object is a String, it will treat empty String the same as a null.
	 * 
	 * @param parameter
	 *            Object to check.
	 * @param name
	 * @throws IllegalArgumentException
	 *             If parameter is null
	 * @throws IllegalArgumentException
	 *             If parameter is a String and is empty
	 */
	public static void requireParam(final Object parameter, final String name) {
		if (parameter == null) {
			throw new IllegalArgumentException(name + " cannot be null");
		}
		if (parameter instanceof String && ((String) parameter).length() < 1) {
			throw new IllegalArgumentException(name + " cannot be empty");
		}
	}

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
	public static void requireParam(final long parameter, final String name, final int min) {
		if (parameter < min) {
			throw new IllegalArgumentException(name + " is " + parameter + " but must be at least " + min);
		}
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

}
