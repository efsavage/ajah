/*
 *  Copyright 2012-2013 Eric F. Savage, code@efsavage.com
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
import com.ajah.util.IdentifiableEnum;

/**
 * Generic class that will map an enum based on its {@link Identifiable#getId()}
 * method.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 * @param <E>
 */
public class IdentifiableEnumPropertyEditor<E extends IdentifiableEnum<?>> extends PropertyEditorSupport {

	private final E[] values;
	private final boolean matchId;
	private final boolean matchEnumName;
	private final boolean matchName;
	private final boolean matchCode;
	private final boolean caseSensitive;

	/**
	 * Pass the result of Enum.getValues() because I can't seem to find a way to
	 * derive this from the type parameter.
	 * 
	 * Matches on ID only, case-sensitive. See
	 * {@link #IdentifiableEnumPropertyEditor(IdentifiableEnum[], boolean, boolean, boolean, boolean, boolean)}
	 * for other options.
	 * 
	 * @param values
	 *            The values of the enum.
	 */
	public IdentifiableEnumPropertyEditor(final E[] values) {
		this(values, true, false, false, false, true);
	}

	/**
	 * Pass the result of Enum.getValues() because I can't seem to find a way to
	 * derive this from the type parameter.
	 * 
	 * This is the most flexible option, with all matching enabled.
	 * 
	 * @param values
	 *            The values of the enum.
	 * @param caseSensitive
	 *            Should matching be case sensitive?
	 */
	public IdentifiableEnumPropertyEditor(final E[] values, boolean caseSensitive) {
		this(values, true, true, true, true, caseSensitive);
	}

	/**
	 * Pass the result of Enum.getValues() because I can't seem to find a way to
	 * derive this from the type parameter.
	 * 
	 * @param values
	 *            The values of the enum.
	 * @param matchId
	 *            Match on the enum's ID?
	 * @param matchEnumName
	 *            Match on the enum's name() value?
	 * @param matchName
	 *            Match on the enum's getName() value?
	 * @param matchCode
	 *            Match on the enum's code ID?
	 * @param caseSensitive
	 *            Should matching be case sensitive?
	 */
	public IdentifiableEnumPropertyEditor(final E[] values, boolean matchId, boolean matchEnumName, boolean matchName, boolean matchCode, boolean caseSensitive) {
		this.values = values;
		this.matchId = matchId;
		this.matchEnumName = matchEnumName;
		this.matchName = matchName;
		this.matchCode = matchCode;
		this.caseSensitive = caseSensitive;
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
			if (this.matchId && equals(candidate.getId().toString(), text)) {
				setValue(candidate);
				return;
			}
			if (this.matchEnumName && equals(candidate.name(), text)) {
				setValue(candidate);
				return;
			}
			if (this.matchName && equals(candidate.getName(), text)) {
				setValue(candidate);
				return;
			}
			if (this.matchCode && equals(candidate.getCode(), text)) {
				setValue(candidate);
				return;
			}
		}
		throw new java.lang.IllegalArgumentException(text);
	}

	/**
	 * Matches strings, is aware of the caseSensitive property.
	 * 
	 * @param first
	 *            The first string to match.
	 * @param second
	 *            The second string to match.
	 * @return The results of {@link String#equals(Object)} or
	 *         {@link String#equalsIgnoreCase(String)} as appropriate.
	 */
	private boolean equals(String first, String second) {
		if (this.caseSensitive) {
			return first.equals(second);
		}
		return first.equalsIgnoreCase(second);
	}

}
