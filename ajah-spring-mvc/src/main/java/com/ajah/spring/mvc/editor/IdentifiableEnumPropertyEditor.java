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

package com.ajah.spring.mvc.editor;

import java.beans.PropertyEditorSupport;

import com.ajah.util.Identifiable;

/**
 * Generic class that will map an enum based on its {@link Identifiable#getId()}
 * method.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 * @param <E>
 */
public class IdentifiableEnumPropertyEditor<E extends Identifiable<?>> extends PropertyEditorSupport {

	private final E[] values;

	/**
	 * Pass the result of Enum.getValues() because I can't seem to find a way to
	 * derive this from the type parameter.
	 * 
	 * @param values
	 *            The values of the enum.
	 */
	public IdentifiableEnumPropertyEditor(final E[] values) {
		this.values = values;
	}

	/**
	 * Gets the property value as a string suitable for presentation to a human
	 * to edit.
	 * 
	 * @return The property value as a string suitable for presentation to a
	 *         human to edit.
	 *         <p>
	 *         Returns null if the value can't be expressed as a string.
	 *         <p>
	 *         If a non-null value is returned, then the PropertyEditor should
	 *         be prepared to parse that string back in setAsText().
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getAsText() {
		return (getValue() != null) ? ((E) getValue()).getId().toString() : null;
	}

	/**
	 * Sets the property value by parsing a given String. May raise
	 * java.lang.IllegalArgumentException if either the String is badly
	 * formatted or if this kind of property can't be expressed as text.
	 * 
	 * @param text
	 *            The string to be parsed.
	 */
	@Override
	public void setAsText(final String text) throws java.lang.IllegalArgumentException {
		for (final E candidate : this.values) {
			if (candidate.getId().toString().equals(text)) {
				setValue(candidate);
				return;
			}
		}
		throw new java.lang.IllegalArgumentException(text);
	}

}
