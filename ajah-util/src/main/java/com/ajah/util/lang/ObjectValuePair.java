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
package com.ajah.util.lang;

import java.util.Map;

/**
 * Simple bean to represent a object/value pair. Useful where a {@link Map} is
 * not the best choice.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <O>
 *            The type of the object.
 * @param <V>
 *            The type of the value.
 */
public class ObjectValuePair<O, V> {

	private O object;

	private V value;

	/**
	 * Constructs this bean with the two fields. Either field may be null.
	 * 
	 * @param object
	 *            The object of the object/value, may be null.
	 * @param value
	 *            The object/value, may be null.
	 */
	public ObjectValuePair(final O object, final V value) {
		this.object = object;
		this.value = value;
	}

	/**
	 * Returns the object of the object/value.
	 * 
	 * @return The object of the object/value.
	 */
	public O getObject() {
		return this.object;
	}

	/**
	 * Returns the object/value.
	 * 
	 * @return The object/value.
	 */
	public V getValue() {
		return this.value;
	}

	/**
	 * Sets the object of the object/value.
	 * 
	 * @param object
	 *            The object of the object/value.
	 */
	public void setObject(final O object) {
		this.object = object;
	}

	/**
	 * Sets the object/value.
	 * 
	 * @param value
	 *            The object/value.
	 */
	public void setValue(final V value) {
		this.value = value;
	}

}
