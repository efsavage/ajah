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
import java.util.logging.Level;

import lombok.extern.java.Log;

/**
 * Utility methods for arrays.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class ArrayUtils {

	/**
	 * Checks to see if an array contains a given object.
	 * 
	 * @see Object#equals(Object)
	 * @param array
	 *            The array to check.
	 * @param member
	 *            The object to look for in the array.
	 * @return true if the parameters are not null and the object was found,
	 *         otherwise false.
	 */
	public static <T> boolean contains(final T[] array, final T member) {
		if (array == null || array.length == 0 || member == null) {
			return false;
		}
		for (final T t : array) {
			if (t.equals(member)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Exception-free way to get an element from an array. Will return null if
	 * the array is null or if the array is not large enough to contain the
	 * index requested.
	 * 
	 * @param <T>
	 *            The class of object to return.
	 * @param array
	 *            The array to look at, may be null.
	 * @param index
	 *            The index of the array to return.
	 * @return The object to return, may be null.
	 */
	public static <T> T get(final T[] array, final int index) {
		if (array == null || index < 0 || array.length < index + 1) {
			return null;
		}
		try {
			return array[index];
		} catch (final ArrayIndexOutOfBoundsException e) {
			// This should not be able to happen, it's here for emphasis.
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Find the largest value in the array and returns the index of it. If two
	 * values are equal, will return the first index.
	 * 
	 * @param values
	 *            The array of integers.
	 * @return Index of the first occurrence of the largest value.
	 * @throws IllegalArgumentException
	 *             if values is null.
	 */
	public static int indexOfLargest(final int[] values) {
		AjahUtils.requireParam(values, "values");
		int index = 0;
		final int largest = values[0];
		for (int i = 1; i < values.length; i++) {
			if (values[i] > largest) {
				index = i;
			}
		}
		return index;
	}

	/**
	 * Determines if an array has any actual objects in it. Will return true if:
	 * <ul>
	 * <li>The array is null.</li>
	 * <li>The array's length is zero.</li>
	 * <li>The array's contains only nulls.</li>
	 * </ul>
	 * 
	 * @param array
	 *            The array to test.
	 * @return Returns true if the array is empty as specified above, otherwise
	 *         false.
	 */
	public static boolean isEmpty(final Object[] array) {
		if (array == null || array.length < 1) {
			return true;
		}
		for (final Object object : array) {
			if (object != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * A variation of {@link #isEmpty(Object[])} for Strings that checks for
	 * empty/blank strings.
	 * 
	 * @see StringUtils#isBlank(String)
	 * @param array
	 *            The array to test.
	 * @return Returns true if the array is empty as specified above, otherwise
	 *         false.
	 */
	public static boolean isEmpty(final String[] array) {
		if (array == null || array.length < 1) {
			return true;
		}
		for (final String string : array) {
			if (!StringUtils.isBlank(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Joins the result of calling {@link #toString()} on each element of an
	 * array.
	 * 
	 * @param array
	 *            The array, may be null or empty.
	 * @return The joined result of calling {@link #toString()} on each element
	 *         of an array, or null if the supplied array was null or empty.
	 */
	public static String joinToString(final Object[] array) {
		if (array == null || array.length == 0) {
			return null;
		}
		final StringBuilder string = new StringBuilder();
		for (final Object object : array) {
			string.append(object.toString());
		}
		return string.toString();
	}

	/**
	 * Returns the length of an array, or zero if he array is null.
	 * 
	 * @param array
	 *            The array to find the length of, may be null.
	 * @return The length of an array, or zero if he array is null.
	 */
	public static int safeLength(final Object[] array) {
		if (array == null) {
			return 0;
		}
		return array.length;
	}

	/**
	 * Sums an array of bytes.
	 * 
	 * @param array
	 *            Byte array.
	 * @return The sum of the values of the array. Returns 0 if the array is 0
	 *         or empty.
	 */
	public static long sum(final byte[] array) {
		if (array == null || array.length == 0) {
			return 0;
		}
		int retVal = array[0];
		for (int i = 1; i < array.length; i++) {
			retVal += array[i];
		}
		return retVal;
	}

	/**
	 * Sums an array of integers.
	 * 
	 * @param array
	 *            Integer array.
	 * @return The sum of the values of the array. Returns 0 if the array is 0
	 *         or empty.
	 */
	public static long sum(final int[] array) {
		if (array == null || array.length == 0) {
			return 0;
		}
		int retVal = array[0];
		for (int i = 1; i < array.length; i++) {
			retVal += array[i];
		}
		return retVal;
	}

	/**
	 * Trims each element in an array of strings, and removes any null or blank
	 * values.
	 * 
	 * @param array
	 *            The array to trim. May be null or empty.
	 * @return The trimmed array, which may be shorter than the original. If a
	 *         null or empty array is passed in, the same value will be
	 *         returned.
	 */
	public static String[] trim(final String[] array) {
		if (array == null || array.length == 0) {
			return array;
		}
		final ArrayList<String> trimmed = new ArrayList<>();
		for (final String string : array) {
			if (!StringUtils.isBlank(string)) {
				trimmed.add(string.trim());
			}
		}
		return trimmed.toArray(new String[trimmed.size()]);
	}

}
