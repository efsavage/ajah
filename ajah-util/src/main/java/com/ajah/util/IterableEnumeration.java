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
package com.ajah.util;

import java.util.Enumeration;
import java.util.Iterator;

import com.ajah.lang.EmptyEnumeration;

/**
 * Wraps an {@link Enumeration} in the interfaces required to use it in an
 * enhanced for loop.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The type of the enumeration.
 */
public class IterableEnumeration<T> implements Iterable<T>, Iterator<T> {

	final private Enumeration<T> enumeration;

	/**
	 * Required constructor to wrap the enumeration.
	 * 
	 * @param enumeration
	 *            The Enumeration to wrap.
	 */
	public IterableEnumeration(final Enumeration<T> enumeration) {
		this(enumeration, false);
	}

	/**
	 * Required constructor to wrap the enumeration.
	 * 
	 * @param enumeration
	 *            The Enumeration to wrap.
	 * @param nullSafe
	 *            If true, a null enumeration will be replaced with an empty one
	 *            to avoid errors.
	 */
	public IterableEnumeration(final Enumeration<T> enumeration, boolean nullSafe) {
		if (enumeration == null) {
			if (nullSafe) {
				this.enumeration = new EmptyEnumeration<>();
			} else {
				throw new IllegalArgumentException("Enumeration may not be null");
			}
		} else {
			this.enumeration = enumeration;
		}
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return this;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.enumeration.hasMoreElements();
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public T next() {
		return this.enumeration.nextElement();
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove() not supported by underlying class Enumeration");

	}
}
