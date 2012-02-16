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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilities for dealing with {@link Identifiable} entities.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class IdentifiableUtils {

	/**
	 * Removes any matching entities as determined by their IDs.
	 * 
	 * @param target
	 *            The collection to remove from.
	 * @param toRemove
	 *            The collection of items to remove.
	 */
	public static <K extends Comparable<K>, I extends Identifiable<K>> void removeAll(Collection<I> target, Collection<I> toRemove) {
		if (target == null || toRemove == null) {
			return;
		}
		List<I> idMatches = new ArrayList<>();
		for (I idRemove : toRemove) {
			for (I idCandidate : target) {
				if (idCandidate.getId().compareTo(idRemove.getId()) == 0) {
					idMatches.add(idCandidate);
				}
			}
		}
		target.removeAll(idMatches);
	}

	/**
	 * Removes a matching entity as determined by its IDs.
	 * 
	 * @param target
	 *            The collection to remove from.
	 * @param toRemove
	 *            The item to remove.
	 */
	public static <K extends Comparable<K>, I extends Identifiable<K>> void remove(Collection<I> target, I toRemove) {
		if (target == null || toRemove == null) {
			return;
		}
		List<I> idMatches = new ArrayList<>();
		for (I idCandidate : target) {
			if (idCandidate.getId().compareTo(toRemove.getId()) == 0) {
				idMatches.add(idCandidate);
			}
		}
		target.removeAll(idMatches);
	}

	/**
	 * Makes a map from list of {@link Identifiable}s.
	 * 
	 * @param collection
	 *            The collection to build from.
	 * @return A map with all of the elements of the collection, using the ID of
	 *         the objects as the key.
	 */
	public static <K extends Comparable<K>, I extends Identifiable<K>> Map<K, I> toMap(Collection<I> collection) {
		Map<K, I> map = new HashMap<>();
		if (CollectionUtils.isEmpty(collection)) {
			return map;
		}
		for (I identifiable : collection) {
			map.put(identifiable.getId(), identifiable);
		}
		return map;
	}

}
