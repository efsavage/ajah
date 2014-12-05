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

import java.util.Date;

import lombok.Getter;

/**
 * Simple wrapper to provide an object to lock on, with some stored information
 * useful for debugging.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The type of the value being locked.
 * 
 */
public class PooledLock<T> {

	@Getter
	private final T lockValue;
	@Getter
	private final Date created;

	/**
	 * Public constructor.
	 * 
	 * @param lockValue
	 *            The value to be locked.
	 */
	public PooledLock(final T lockValue) {
		this.lockValue = lockValue;
		this.created = new Date();
	}

}
