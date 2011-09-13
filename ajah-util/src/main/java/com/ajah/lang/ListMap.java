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
package com.ajah.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Extension of {@link HashMap} that has a methods to make dealing with the
 * value lists easier.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 * @param <K>
 * @param <V>
 */
public class ListMap<K, V> extends HashMap<K, List<V>> {

	/**
	 * Adds a value to the list for a given key, creating that list if
	 * necessary.
	 * 
	 * @param key
	 *            The key to look up the value list.
	 * @param value
	 *            The value to add to the list.
	 */
	public void putValue(K key, V value) {
		List<V> list = get(key);
		if (list == null) {
			list = new ArrayList<V>();
			put(key, list);
		}
		list.add(value);
	}

	/**
	 * Returns the list found at the specified key or an empty list. Avoids
	 * {@link NullPointerException} for iterators.
	 * 
	 * @param key
	 *            The key to look up the value list.
	 * @return The value list, may be empty, will not be null.
	 */
	public List<V> getList(K key) {
		List<V> list = get(key);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

}
