/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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

import com.ajah.util.Created;

/**
 * Compares two entities by created date.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class CreatedComparator implements Comparator<Created> {

	/**
	 * An ascending (earliest-first) comparator.
	 */
	public static final CreatedComparator ASCENDING = new CreatedComparator(true);
	/**
	 * Ad descending (latest-first) comparator.
	 */
	public static final CreatedComparator DESCENDING = new CreatedComparator(false);

	private final boolean ascending;

	/**
	 * Private constructor.
	 * 
	 * @param ascending
	 *            Sort ascending? I.E. Earliest date first
	 */
	private CreatedComparator(boolean ascending) {
		this.ascending = ascending;
	}

	/**
	 * @see CompareUtils#compare(long, long)
	 * @throws IllegalArgumentException
	 *             If Both values are null, as this means it cannot be passed
	 *             along to a comparator even though they are "equal".
	 */
	@Override
	public int compare(final Created first, final Created second) {
		final int retVal = CompareUtils.compareNulls(first, second);
		if (retVal != 0) {
			return retVal;
		}
		if (this.ascending) {
			return CompareUtils.compare(first.getCreated(), second.getCreated(), true);
		}
		return -CompareUtils.compare(first.getCreated(), second.getCreated(), true);
	}

}
