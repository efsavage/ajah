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
import java.util.List;
import java.util.Map;

/**
 * Utilities for dealing with members of the Collections API.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class CollectionUtils {

	/**
	 * Checks to see if a collection is null or empty.
	 * 
	 * @param collection
	 *            Collection to test, may be null.
	 * @return true if the collection is null or empty, otherwise false.
	 */
	public static boolean isEmpty(final Collection<?> collection) {
		return collection == null || collection.size() < 1;
	}

	/**
	 * Returns null if the supplied collection is empty.
	 * 
	 * @param collection
	 *            The collection to test.
	 * @return The original collection if not empty, otherwise null.
	 */
	public static <T, C extends Collection<T>> C nullIfEmpty(final C collection) {
		if (isEmpty(collection)) {
			return null;
		}
		return collection;
	}

	/**
	 * Shorthand method for adding an item to a list, and creating that list if
	 * necessary.
	 * 
	 * @param list
	 *            The list to add to.
	 * @param item
	 *            The item to add.
	 * @return The original list that was passed in, or a new list.
	 */
	private static <T> List<T> safeAdd(final List<T> list, final T item) {
		List<T> retVal = list;
		if (retVal == null) {
			retVal = new ArrayList<>();
		}
		retVal.add(item);
		return retVal;
	}

	/**
	 * Returns the size of a collection, or zero if the collection is null.
	 * 
	 * @param collection
	 *            The collection to inspect, may be null.
	 * @return The size of the collection, zero if the collection is null.
	 */
	public static int safeSize(final Collection<?> collection) {
		if (collection == null) {
			return 0;
		}
		return collection.size();
	}

	/**
	 * Removes the entries of a map that have null values.
	 * 
	 * @param map
	 *            The map to strip.
	 * @return The map that was passed in.
	 */
	public static <K, V> Map<K, V> stripNulls(final Map<K, V> map) {
		List<Object> toRemove = null;
		for (final Object key : map.keySet()) {
			if (map.get(key) == null) {
				toRemove = safeAdd(toRemove, key);
			}
		}
		if (toRemove != null) {
			for (final Object key : toRemove) {
				map.remove(key);
			}
		}
		return map;
	}
}
