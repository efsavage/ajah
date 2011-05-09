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
 * 
 */
public class MathUtils {

	/**
	 * Reduces a value by a percentage, with a minimum change. Examples:
	 * 
	 * Reducing 100 by 5% with a minimum of 1 would yield 95.
	 * 
	 * Reducing 100 by 7% with a minimum of 10 would yield 90.
	 * 
	 * Reducing 1 by 10% with a minimum of 2 would yield -2.
	 * 
	 * @param value
	 * @param percentageChange
	 * @param minChange
	 * @return The lesser of the value minus the value * percentage, or the
	 *         value minus the minChange.
	 */
	public static int reduce(int value, double percentageChange, int minChange) {
		int reduction = (int) (value * percentageChange);
		if (reduction > minChange) {
			return value - minChange;
		}
		return value - reduction;
	}

}