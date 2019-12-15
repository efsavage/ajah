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
 * Utilities that do math.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class MathUtils {

	/**
	 * Adds an array of longs to another array of longs. The resulting array
	 * will be as long as the longer of the two parameters.
	 * 
	 * @param array1
	 *            The first array to add
	 * @param array2
	 *            The second array to add
	 * @param strict
	 *            If true, will throw an {@link IllegalArgumentException} if the
	 *            arrays are a different length.
	 * @return The aggregate array,
	 */
	public static long[] add(final long[] array1, final long[] array2, final boolean strict) {
		AjahUtils.requireParam(array1, "array1");
		AjahUtils.requireParam(array2, "array2");
		if (strict && array1.length != array2.length) {
			throw new IllegalArgumentException("Strict mode enabled: the two arrays must be of the same length");
		}
		final long[] result = new long[Math.max(array1.length, array2.length)];
		for (int i = 0; i < result.length; i++) {
			if (array1.length < i) {
				if (array2.length < i) {
					// Shouldn't happen
					continue;
				}
				continue;
			} else if (array2.length < i) {
				continue;
			} else {
				result[i] = array1[i] + array2[i];
			}
		}
		return result;
	}

	/**
	 * Adds one array to another.
	 * 
	 * @param to
	 *            The array to add to. This array will be changed by this
	 *            method.
	 * @param from
	 *            The array to add from. This array will not be changed by this
	 *            method.
	 */
	public static void addTo(final long[] to, final long[] from) {
		for (int i = 0; i < to.length; i++) {
			to[i] = to[i] + from[i];
		}
	}

	/**
	 * Divides a divident by divisor , with a default value if the divisor is
	 * zero.
	 * 
	 * @param dividend
	 *            The dividend.
	 * @param divisor
	 *            The divisor.
	 * @param defaultDivisor
	 *            The defaultDivisor, if the divisor is zero.
	 * @return divident/divisor or divident/defaultDivisor
	 */
	public static long divideZero(final long dividend, final long divisor, final long defaultDivisor) {
		if (divisor == 0) {
			return dividend / defaultDivisor;
		}
		return dividend / divisor;
	}

	/**
	 * Multiplies an object number by a primitive int and returns a Long.
	 * 
	 * @param operand1
	 *            The first operand, required.
	 * @param operand2
	 *            The second operand.
	 * @return The result of the multiplication as a Long.
	 */
	public static Long multiplyForLong(final Number operand1, final int operand2) {
		if (operand1 instanceof Double) {
			return Long.valueOf(Math.round(operand1.doubleValue() * operand2));
		}
		throw new IllegalArgumentException(operand1.getClass() + " not supported");
	}

	/**
	 * Reduces a value by a percentage, with a minimum change. Examples:
	 * Reducing 100 by 5% with a minimum of 1 would yield 95. Reducing 100 by 7%
	 * with a minimum of 10 would yield 90. Reducing 1 by 10% with a minimum of
	 * 2 would yield -2.
	 * 
	 * @param value
	 * @param percentageChange
	 * @param minChange
	 * @return The lesser of the value minus the value * percentage, or the
	 *         value minus the minChange.
	 */
	public static int reduce(final int value, final double percentageChange, final int minChange) {
		final int reduction = (int) (value * percentageChange);
		if (reduction > minChange) {
			return value - minChange;
		}
		return value - reduction;
	}

}
