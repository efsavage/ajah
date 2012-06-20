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
package com.ajah.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple cache backed by a {@link HashMap}. Stores objects with timestamps so
 * that they can be expired.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 * @param <V>
 */
public class HashCache<K, V> {

	private final Map<K, CacheEntry<V>> map = new HashMap<>();

	/**
	 * Returns a cached value if available and if it is not older than the
	 * specified maximum age.
	 * 
	 * @param key
	 *            They key of the value to fetch.
	 * @param maxAge
	 *            The maximum age of the cached value, in milliseconds.
	 * @return The cached value if available and if it is not older than the
	 *         specified maximum age, otherwise null.
	 */
	public V get(final K key, final long maxAge) {
		final CacheEntry<V> entry = this.map.get(key);
		if (entry == null || (entry.getCreated() + maxAge) < System.currentTimeMillis()) {
			return null;
		}
		return entry.getObject();
	}

	/**
	 * Stores a value.
	 * 
	 * @param key
	 *            They key to store the value under.
	 * @param value
	 *            The value to store.
	 */
	public void store(final K key, final V value) {
		this.map.put(key, new CacheEntry<>(value, System.currentTimeMillis()));
	}

}
