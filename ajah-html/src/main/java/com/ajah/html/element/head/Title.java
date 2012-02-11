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
package com.ajah.html.element.head;

import java.io.IOException;
import java.io.Writer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.html.HtmlElement;
import com.ajah.util.StringUtils;

/**
 * title element
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Title implements HtmlElement<Title> {

	private String text;

	/**
	 * Public constructor.
	 * 
	 * @param text
	 *            The text value of this element.
	 */
	public Title(String text) {
		this.text = text;
	}

	/**
	 * @see com.ajah.html.HtmlElement#render(java.io.Writer, int)
	 */
	@Override
	public void render(Writer out, int depth) throws IOException {
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		if (!StringUtils.isBlank(this.text)) {
			out.write("<title>");
			out.write(this.text);
			out.write("</title>\r\n");
		}
	}

}
