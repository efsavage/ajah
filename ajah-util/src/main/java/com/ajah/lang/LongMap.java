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
package com.ajah.lang;

import java.util.HashMap;

/**
 * This classes makes storing maps of {@link Long}s and longs easier.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The key, under which the value will be stored.
 */
public class LongMap<K> extends HashMap<K, Long> {

	/**
	 * Fetches a value and returns the primitive equivalent, which is zero if
	 * the true value is null.
	 * 
	 * @param key
	 *            The first key.
	 * @return The value, may be null if not previously set.
	 */
	public long getLong(final K key) {
		Long value = get(key);
		if (value == null) {
			return 0;
		}
		return value.longValue();
	}

	public void setLong(K key, long value) {
		put(key, new Long(value));
	}

}
