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
package com.ajah.html.element;

import java.io.IOException;
import java.io.Writer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.html.HtmlElement;

/**
 * CDATA (text) element
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode()
public class CData implements HtmlElement<CData> {

	private String value;

	/**
	 * Creates a text element for a string.
	 * 
	 * @param value
	 *            The text to render.
	 */
	public CData(final String value) {
		this.value = value;
	}

	/**
	 * Writes the value attribute.
	 * 
	 * @see com.ajah.html.HtmlElement#render(java.io.Writer, int)
	 */
	@Override
	public void render(final Writer out, final int depth) throws IOException {
		out.write(this.value);
	}

}
