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
package com.ajah.html;

/**
 * Identifies an element as able to contain other elements.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface Nestable {

	/**
	 * Nest a child element in this element. Preserves order.
	 * 
	 * @param <R>
	 *            Type of the value being added, and returned.
	 * 
	 * @param element
	 *            Element to be added
	 * @return Returns the value added for fluid-style coding.
	 */
	public <R extends HtmlElement<R>> R add(final R element);

}
