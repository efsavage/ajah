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

import java.io.IOException;
import java.io.Writer;

/**
 * Any element (tag) that might appear in an HTML document.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * @param <T>
 *            Self-reference to type of implementing class, for fluid methods.
 * 
 */
public interface HtmlElement<T> {

	/**
	 * Write this element to the writer. Recursively calls render on child
	 * elements.
	 * 
	 * @param out
	 *            The writer to write to (such as a JspWriter)
	 * @throws IOException
	 */
	void render(Writer out) throws IOException;

}