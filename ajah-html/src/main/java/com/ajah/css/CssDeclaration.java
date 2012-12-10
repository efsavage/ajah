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
package com.ajah.css;

import lombok.Data;

import com.ajah.util.compare.CompareUtils;

/**
 * Represents a CSS declaration (property/value within a rule).
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class CssDeclaration implements Comparable<CssDeclaration> {

	private final CssRule rule;
	private final CssProperty property;
	private String value;

	/**
	 * Public constructor.
	 * 
	 * @param rule
	 *            The rule this declaration belongs to, required.
	 * @param property
	 *            The property this declaration represents, required.
	 * @param value
	 *            The value of the property.
	 */
	public CssDeclaration(final CssRule rule, final CssProperty property, final String value) {
		this.rule = rule;
		this.property = property;
		this.value = value;
		rule.add(this);
	}

	@Override
	public int compareTo(final CssDeclaration other) {
		int retVal = this.property.getName().compareTo(other.getProperty().getName());
		if (retVal == 0) {
			retVal = CompareUtils.compare(this.value, other.getValue());
		}
		return retVal;
	}

	/**
	 * Returns the valid, usable version of this declaration.
	 * 
	 * @return The valid, usable version of this document
	 */
	@Override
	public String toString() {
		return toString(true);
	}

	/**
	 * Returns the valid, usable version of this declaration.
	 * 
	 * @param semicolon
	 *            Append a semicolon at the end?
	 * @return The valid, usable version of this document
	 */
	public String toString(final boolean semicolon) {
		return this.property.getName() + ':' + this.value + (semicolon ? ";" : "");
	}

}
