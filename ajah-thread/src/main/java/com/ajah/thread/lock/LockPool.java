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
package com.ajah.thread.lock;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages "virtual" objects to lock on during synchronization. For example, if
 * there multiple copies of an object with the same ID, you can lock on the
 * <em>value</em> of the ID rather than objects themselves.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The type of the value to lock on.
 * 
 */
public class LockPool<T> {

	final ConcurrentHashMap<T, PooledLock<T>> pool = new ConcurrentHashMap<>();

	/**
	 * Returns a object derived from the specified value to lock on.
	 * 
	 * @param lockValue
	 *            The value to lock.
	 * @return The lockable object.
	 */
	public synchronized PooledLock<T> get(final T lockValue) {
		if (!this.pool.containsKey(lockValue)) {
			this.pool.put(lockValue, new PooledLock<>(lockValue));
		}
		return this.pool.get(lockValue);
	}

}
