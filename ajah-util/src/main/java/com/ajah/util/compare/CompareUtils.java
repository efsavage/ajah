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
package com.ajah.util.compare;

/**
 * 
 * Utility methods for comparisons.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class CompareUtils {

	/**
	 * Compares two longs.
	 * 
	 * @param first
	 *            First value.
	 * @param second
	 *            Second value.
	 * @return -1 if first < second, 1 if first > second, 0 if equal.
	 */
	public static int compare(long first, long second) {
		if (first < second) {
			return -1;
		} else if (first > second) {
			return 1;
		}
		return 0;
	}

	/**
	 * Compares two objects, with null being considered lesser than not-null.
	 * 
	 * @param first
	 *            First value.
	 * @param second
	 *            Second value.
	 * @return -1 if first is null and second is not, 1 if first is not null and
	 *         second is, 0 if both are not-null.
	 * @throws IllegalArgumentException
	 *             If Both values are null, as this means it cannot be passed
	 *             along to a comparator even though they are "equal".
	 */
	public static int compareNulls(Object first, Object second) {
		if (first == null) {
			if (second == null) {
				throw new IllegalArgumentException("Both values are null");
			}
			return -1;
		}
		if (second == null) {
			return 1;
		}
		return 0;
	}

	/**
	 * Compares two doubles.
	 * 
	 * @param first
	 *            First value.
	 * @param second
	 *            Second value.
	 * @return -1 if first < second, 1 if first > second, 0 if equal.
	 */
	public static int compare(double first, double second) {
		if (first < second) {
			return -1;
		} else if (first > second) {
			return 1;
		}
		return 0;
	}

	public static int compare(String first, String second) {
		if (first == null) {
			if (second == null) {
				throw new IllegalArgumentException("Both values are null");
			}
			System.err.println("Null!");
			return -1;
		}
		if (second == null) {
			System.err.println("Null!");
			return 1;
		}
		return first.compareTo(second);
	}

}