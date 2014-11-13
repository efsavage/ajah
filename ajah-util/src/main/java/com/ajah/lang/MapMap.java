/*
 *  Copyright 2012-14 Eric F. Savage, code@efsavage.com
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This classes makes storing maps of maps easier.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K1>
 *            The first key, under which the second key's maps will be stored.
 * @param <K2>
 *            The second key, under which the values will be stored.
 * @param <V>
 *            The value.
 */
public class MapMap<K1, K2, V> extends ConcurrentHashMap<K1, Map<K2, V>> {

	/**
	 * Fetches a value based on both keys. Avoids the possible NPE caused by
	 * fetching the inner map, then the value.
	 * 
	 * @param key1
	 *            The first key.
	 * @param key2
	 *            The second key.
	 * @return The value, may be null if not previously set.
	 */
	public V get(final K1 key1, final K2 key2) {
		final Map<K2, V> innerMap = get(key1);
		if (innerMap == null) {
			return null;
		}
		return innerMap.get(key2);
	}

	/**
	 * Puts a value under two keys. If no map exists for the first key, will
	 * create one.
	 * 
	 * @param key1
	 *            The first key.
	 * @param key2
	 *            The second key.
	 * @param value
	 *            The value.
	 */
	public void put(final K1 key1, final K2 key2, final V value) {
		Map<K2, V> innerMap = get(key1);
		if (innerMap == null) {
			innerMap = new ConcurrentHashMap<>();
			put(key1, innerMap);
		}
		innerMap.put(key2, value);
	}

}
