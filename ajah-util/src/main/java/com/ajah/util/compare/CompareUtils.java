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

import java.util.Collection;
import java.util.Date;

import com.ajah.util.CollectionUtils;
import com.ajah.util.Identifiable;
import com.ajah.util.IdentifiableEnum;
import com.ajah.util.StringUtils;
import com.ajah.util.ToStringable;

/**
 * Utility methods for comparisons.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class CompareUtils {

	/**
	 * Compares two booleans, with true being greater than false.
	 * 
	 * @param first
	 *            First value.
	 * @param second
	 *            Second value.
	 * @return -1 if first is false and second is true, 1 if first is true and
	 *         second is false, otherwise 0.
	 */
	public static int compare(final boolean first, final boolean second) {
		if (first == second) {
			return 0;
		} else if (first) {
			return 1;
		}
		return -1;
	}

	/**
	 * Compares two {@link Date}, checking for nulls first.
	 * 
	 * @see Date#compareTo(Date)
	 * @param first
	 *            The first date, may be null.
	 * @param second
	 *            The second date, may be null.
	 * @param nullsEqual
	 *            Should two null objects be treated as equal (true) or throw an
	 *            exception (false)?
	 * @return The comparison of the two Dates.
	 * @throws IllegalArgumentException
	 *             If Both values are null and nullsEqual is false, as this
	 *             means it cannot be passed along to a comparator even though
	 *             they are "equal".
	 */
	public static int compare(final Date first, final Date second, final boolean nullsEqual) {
		final int retVal = compareNulls(first, second, nullsEqual);
		if (nullsEqual && retVal == 0 && first == null) {
			return retVal;
		}
		if (retVal != 0) {
			return retVal;
		}
		return first.compareTo(second);
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
	public static int compare(final double first, final double second) {
		if (first < second) {
			return -1;
		} else if (first > second) {
			return 1;
		}
		return 0;
	}

	public static int compare(final IdentifiableEnum<String> first, final IdentifiableEnum<String> second, final boolean nullsEqual) {
		final int retVal = compareNulls(first, second, nullsEqual);
		if (nullsEqual && retVal == 0 && first == null) {
			return retVal;
		}
		if (retVal != 0) {
			return retVal;
		}
		return first.getId().compareTo(second.getId());
	}

	/**
	 * Compares two longs.
	 * 
	 * @param first
	 *            First value.
	 * @param second
	 *            Second value.
	 * @return -1 if first < second, 1 if first > second, 0 if equal.
	 */
	public static int compare(final long first, final long second) {
		if (first < second) {
			return -1;
		} else if (first > second) {
			return 1;
		}
		return 0;
	}

	/**
	 * Compares two strings via {@link #compare(String, String, boolean)}, with
	 * nullsEqual set to false.
	 * 
	 * @see String#compareTo(String)
	 * @param first
	 *            The first string, may be null.
	 * @param second
	 *            The second string, may be null.
	 * @return The comparison of the two Strings.
	 * @throws IllegalArgumentException
	 *             If Both values are null, as this means it cannot be passed
	 *             along to a comparator even though they are "equal".
	 */
	public static int compare(final String first, final String second) {
		return compare(first, second, false);
	}

	/**
	 * Compares two strings, checking for nulls first.
	 * 
	 * @see String#compareTo(String)
	 * @param first
	 *            The first string, may be null.
	 * @param second
	 *            The second string, may be null.
	 * @param nullsEqual
	 *            Should two null objects be treated as equal (true) or throw an
	 *            exception (false)?
	 * @return The comparison of the two Strings.
	 * @throws IllegalArgumentException
	 *             If Both values are null and nullsEqual is false, as this
	 *             means it cannot be passed along to a comparator even though
	 *             they are "equal".
	 */
	public static int compare(final String first, final String second, final boolean nullsEqual) {
		final int retVal = compareNulls(first, second, nullsEqual);
		if (nullsEqual && retVal == 0 && first == null) {
			return retVal;
		}
		if (retVal != 0) {
			return retVal;
		}
		return first.compareTo(second);
	}

	/**
	 * Compares two {@link ToStringable}s, checking for nulls first.
	 * 
	 * @see String#compareTo(String)
	 * @param first
	 *            The first ToStringable, may be null.
	 * @param second
	 *            The second ToStringable, may be null.
	 * @param nullsEqual
	 *            Should two null objects be treated as equal (true) or throw an
	 *            exception (false)?
	 * @return The comparison of the two ToStringables.
	 * @throws IllegalArgumentException
	 *             If Both values are null and nullsEqual is false, as this
	 *             means it cannot be passed along to a comparator even though
	 *             they are "equal".
	 */
	public static int compare(final ToStringable first, final ToStringable second, final boolean nullsEqual) {
		final int retVal = compareNulls(first, second, nullsEqual);
		if (nullsEqual && retVal == 0 && first == null) {
			return retVal;
		}
		if (retVal != 0) {
			return retVal;
		}
		return compare(first.toString(), second.toString());
	}

	/**
	 * Compares two identifiables, checking for nulls on the objects and IDs
	 * first.
	 * 
	 * @see Comparable#compareTo(Object)
	 * @param first
	 *            The first object, may be null.
	 * @param second
	 *            The second object, may be null.
	 * @param nullsEqual
	 *            Should two null objects be treated as equal (true) or throw an
	 *            exception (false)?
	 * @return The comparison of the two object.
	 * @throws IllegalArgumentException
	 *             If Both values are null and nullsEqual is false, as this
	 *             means it cannot be passed along to a comparator even though
	 *             they are "equal".
	 */
	public static <K extends Comparable<K>, T extends Identifiable<K>> int compareIds(final T first, final T second, final boolean nullsEqual) {
		int retVal = compareNulls(first, second, nullsEqual);
		if (nullsEqual && retVal == 0 && first == null) {
			return retVal;
		}
		retVal = compareNulls(first.getId(), second.getId(), nullsEqual);
		if (nullsEqual && retVal == 0 && first.getId() == null) {
			return retVal;
		}
		if (retVal != 0) {
			return retVal;
		}
		return first.getId().compareTo(second.getId());
	}

	/**
	 * Compares two strings, checking for nulls first, and ignoring case.
	 * 
	 * @see String#compareToIgnoreCase(String)
	 * @param first
	 *            The first string, may be null.
	 * @param second
	 *            The second string, may be null.
	 * @return The comparison of the two Strings.
	 * @throws IllegalArgumentException
	 *             If Both values are null, as this means it cannot be passed
	 *             along to a comparator even though they are "equal".
	 */
	public static int compareIgnoreCase(final String first, final String second) {
		final int retVal = compareNulls(first, second);
		if (retVal != 0) {
			return retVal;
		}
		return first.compareToIgnoreCase(second);
	}

	/**
	 * Compares objects for null via
	 * {@link #compareNulls(Object, Object, boolean)}, with nullsEqual set to
	 * false.
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
	public static int compareNulls(final Object first, final Object second) {
		return compareNulls(first, second, false);
	}

	/**
	 * Compares two objects, with null being considered lesser than not-null.
	 * 
	 * @param first
	 *            First value.
	 * @param second
	 *            Second value.
	 * @param nullsEqual
	 * @return -1 if first is null and second is not, 1 if first is not null and
	 *         second is, 0 if both are not-null.
	 * @throws IllegalArgumentException
	 *             If Both values are null and nullsEqual is false, as this
	 *             means it cannot be passed along to a comparator even though
	 *             they are "equal".
	 */
	public static int compareNulls(final Object first, final Object second, final boolean nullsEqual) {
		if (first == null) {
			if (second == null) {
				if (nullsEqual) {
					return 0;
				}
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
	 * Compares the presence (not the contents) of two collections, checking for
	 * nulls first. An empty or null collection will sort lower (-1) than a
	 * populated one.
	 * 
	 * @see String#compareTo(String)
	 * @param first
	 *            The first string, may be null.
	 * @param second
	 *            The second string, may be null.
	 * @return The comparison of the two Strings.
	 * @throws IllegalArgumentException
	 *             If Both values are null and nullsEqual is false, as this
	 *             means it cannot be passed along to a comparator even though
	 *             they are "equal".
	 */
	public static int comparePresence(final Collection<?> first, final Collection<?> second) {
		final int retVal = compareNulls(first, second, true);
		if (retVal != 0) {
			return retVal;
		}
		if (CollectionUtils.isEmpty(first)) {
			if (CollectionUtils.isEmpty(second)) {
				// both empty
				return 0;
			}
			// empty, not-empty
			return -1;
		}
		if (CollectionUtils.isEmpty(second)) {
			// not-empty, empty
			return 1;
		}
		// both not-empty
		return 0;
	}

	/**
	 * Compares the presence (not the contents) of two strings, checking for
	 * nulls first. An empty or null string will sort lower (-1) than a
	 * non-blank string. Null values are considered equal to empty strings.
	 * 
	 * @see String#compareTo(String)
	 * @param first
	 *            The first string, may be null.
	 * @param second
	 *            The second string, may be null.
	 * @return The comparison of the two Strings.
	 * @throws IllegalArgumentException
	 *             If Both values are null and nullsEqual is false, as this
	 *             means it cannot be passed along to a comparator even though
	 *             they are "equal".
	 */
	public static int comparePresence(final String first, final String second) {
		final int retVal = compareNulls(first, second, true);
		if (retVal != 0) {
			return retVal;
		}
		if (StringUtils.isBlank(first)) {
			if (StringUtils.isBlank(second)) {
				// both blank
				return 0;
			}
			// blank, not-blank
			return -1;
		}
		if (StringUtils.isBlank(second)) {
			// not-blank, blank
			return 1;
		}
		// not-blank, not-blank
		return 0;
	}

	/**
	 * Compares two comparables, checking for nulls first.
	 * 
	 * @see Comparable#compareTo(Object)
	 * @param first
	 *            The first object, may be null.
	 * @param second
	 *            The second object, may be null.
	 * @param nullsEqual
	 *            Should two null objects be treated as equal (true) or throw an
	 *            exception (false)?
	 * @return The comparison of the two object.
	 * @throws IllegalArgumentException
	 *             If Both values are null and nullsEqual is false, as this
	 *             means it cannot be passed along to a comparator even though
	 *             they are "equal".
	 */
	public static <T2, T1 extends Comparable<T2>> int safeCompare(final T1 first, final T2 second, final boolean nullsEqual) {
		final int retVal = compareNulls(first, second, nullsEqual);
		if (nullsEqual && retVal == 0 && first == null) {
			return retVal;
		}
		if (retVal != 0) {
			return retVal;
		}
		return first.compareTo(second);
	}

}
