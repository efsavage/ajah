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

/**
 * An arbitrary set of types for a selector, attempting to handle the most
 * common types to make other logical operations easier.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum CssSelectorType {

	/**
	 * Simple element, e.g. "td"
	 */
	ELEMENT(1),
	/**
	 * Unknown selector format
	 */
	UNKNOWN(100),
	/**
	 * Simple descendent selector, e.g. "table tbody td"
	 */
	ELEMENT_DESCENDENT(100),
	/**
	 * Simple general class, e.g. ".headline"
	 */
	SIMPLE_CLASS(100),
	/**
	 * Simple general id, e.g. "#footer"
	 */
	SIMPLE_ID(100),
	/**
	 * Element and ID, e.g. "h3#intro"
	 */
	ELEMENT_ID(100),
	/**
	 * Element and clas, e.g. "span#warning"
	 */
	ELEMENT_CLASS(100);

	private final int specificity;

	CssSelectorType(final int priority) {
		this.specificity = priority;
	}

	/**
	 * Returns the specificity of the selector.
	 * 
	 * @return the specificity of the selector.
	 */
	public int getSpecificity() {
		return this.specificity;
	}

}
