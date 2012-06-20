/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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

import java.util.Comparator;

/**
 * A comparator for chaining other comparators. Will evaluate child comparators
 * in order
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The typ of object to compare
 */
public class CompositeComparator<T> implements Comparator<T> {

	private final Comparator<T>[] comparators;

	/**
	 * Creates an instance with child comparators.
	 * 
	 * @param comparators
	 */
	@SafeVarargs
	public CompositeComparator(final Comparator<T>... comparators) {
		if (comparators == null || comparators.length == 0) {
			throw new IllegalArgumentException("Must include at least one child comparator");
		}
		this.comparators = comparators;
	}

	@Override
	public int compare(final T first, final T second) {
		int retVal = 0;
		for (final Comparator<T> comparator : this.comparators) {
			retVal = comparator.compare(first, second);
			if (retVal != 0) {
				return retVal;
			}
		}
		return retVal;
	}

}
