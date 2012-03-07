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

import java.util.List;
import java.util.Random;

/**
 * Utilities for creating random data.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public class RandomUtils {

	private static Random random = new Random();

	/**
	 * Returns a randomized, lowercased String consisting of only a-z
	 * characters, all with equal weighting.
	 * 
	 * @param minLength
	 *            The minimum length of the desired string.
	 * @param maxLength
	 *            The maximum length of the desired string.
	 * @return A string within the minimum and maximum lengths specified
	 *         consisting of random alphabetical characters.
	 */
	public static String getRandomAlphaString(final int minLength, final int maxLength) {
		final char[] chars = new char[getRandomNumber(minLength, maxLength)];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char) ('a' + getRandomNumber(0, 26));
		}
		return String.valueOf(chars);
	}

	/**
	 * Returns a random element from a list.
	 * 
	 * @param <T>
	 *            Type of element.
	 * @param list
	 *            The list to find a random element in.
	 * @return A random element from the list, or null if the list is null or
	 *         empty.
	 */
	public static <T> T getRandomElement(final List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(getRandomNumber(0, list.size() - 1));
	}

	/**
	 * Returns a random number between the from and to parameters.
	 * 
	 * @see Random#nextDouble()
	 * @param from
	 *            The lowest allowable number.
	 * @param to
	 *            The highest allowable number.
	 * @return Random number between from and to.
	 */
	public static int getRandomNumber(final int from, final int to) {
		return from + (int) Math.floor(random.nextDouble() * ((to + 1) - from));
	}

	/**
	 * Returns a random number between the from and to parameters.
	 * 
	 * @see Random#nextDouble()
	 * @param from
	 *            The lowest allowable number.
	 * @param to
	 *            The highest allowable number.
	 * @return Random number between from and to.
	 */
	public static long getRandomNumber(final long from, final long to) {
		return from + (long) (random.nextDouble() * (to - from));
	}
}
