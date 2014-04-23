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
import java.util.logging.Level;

import lombok.extern.java.Log;

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
@Log
public class ListMap<K, V> extends HashMap<K, List<V>> {

	/**
	 * Returns the list found at the specified key or an empty list. Avoids
	 * {@link NullPointerException} for iterators.
	 * 
	 * If no list is found, {@link Collections#emptyList()} will be returned. If
	 * an empty, mutable list is desired, use {@link #getList(Object, Class)}.
	 * 
	 * @param key
	 *            The key to look up the value list.
	 * @return The value list, may be empty, will not be null.
	 */
	public List<V> getList(final K key) {
		final List<V> list = get(key);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	/**
	 * Returns the list found at the specified key or an empty list. Avoids
	 * {@link NullPointerException} for iterators.
	 * 
	 * @param key
	 *            The key to look up the value list.
	 * @param listClass
	 *            The class of list to return if one needs to be created.
	 * @return The value list, may be empty, will not be null.
	 */
	public List<V> getList(final K key, final Class<? extends List<V>> listClass) {
		List<V> list = get(key);
		if (list == null) {
			try {
				list = listClass.newInstance();
				put(key, list);
			} catch (InstantiationException | IllegalAccessException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return list;
	}

	/**
	 * Adds a value to the list for a given key, creating that list if
	 * necessary.
	 * 
	 * @param key
	 *            The key to look up the value list.
	 * @param value
	 *            The value to add to the list.
	 */
	public void putValue(final K key, final V value) {
		List<V> list = get(key);
		if (list == null) {
			list = new ArrayList<>();
			put(key, list);
		}
		list.add(value);
	}

}
