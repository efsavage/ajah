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
package com.ajah.util.lang;

import java.util.Map;

/**
 * Simple bean to represent a name/value pair. Useful where a {@link Map} is not
 * the best choice.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The type of the value.
 */
public class NameValuePair<T> {

	private String name;

	/**
	 * Returns the name of the object/value.
	 * 
	 * @return The name of the object/value.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the object/value.
	 * 
	 * @param name
	 *            The name of the object/value.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the object/value.
	 * 
	 * @return The object/value.
	 */
	public T getValue() {
		return this.value;
	}

	/**
	 * Sets the object/value.
	 * 
	 * @param value
	 *            The object/value.
	 */
	public void setValue(final T value) {
		this.value = value;
	}

	private T value;

	/**
	 * Constructs this bean with the two fields. Either field may be null.
	 * 
	 * @param name
	 *            The name of the object/value, may be null.
	 * @param value
	 *            The object/value, may be null.
	 */
	public NameValuePair(final String name, final T value) {
		this.name = name;
		this.value = value;
	}

}
