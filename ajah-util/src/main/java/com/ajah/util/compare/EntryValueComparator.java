/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
import java.util.Map;
import java.util.Map.Entry;

/**
 * Compares two {@link Map} {@link Entry}s by their value. If the values are
 * equal and the key is {@link Comparable}, will use the key as a tiebreaker.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 * @param <V>
 * @since 1.0.5
 */
public class EntryValueComparator<K, V extends Comparable<V>> implements Comparator<Entry<K, V>> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int compare(final Entry<K, V> first, final Entry<K, V> second) {
		int retVal = CompareUtils.compareNulls(first, second);
		if (retVal == 0) {
			retVal = CompareUtils.compareNulls(first.getValue(), second.getValue());
		}
		if (retVal == 0) {
			retVal = -first.getValue().compareTo(second.getValue());
		}
		if (retVal == 0 && Comparable.class.isAssignableFrom(first.getKey().getClass())) {
			retVal = ((Comparable) first.getKey()).compareTo(second.getKey());
		}
		return retVal;
	}
}
