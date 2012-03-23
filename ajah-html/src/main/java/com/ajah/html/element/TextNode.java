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
package com.ajah.html.element;

import java.io.IOException;
import java.io.Writer;

import lombok.Data;

import com.ajah.html.HtmlElement;
import com.ajah.util.StringUtils;

/**
 * Text node, suitable for putting raw text in elements like Divs.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class TextNode implements HtmlElement<TextNode> {

	private String text;

	/**
	 * Public constructor with text.
	 * 
	 * @param text
	 *            The text contained in this node.
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * @see com.ajah.html.HtmlElement#render(java.io.Writer, int)
	 */
	@Override
	public void render(Writer out, int depth) throws IOException {
		if (!StringUtils.isBlank(this.text)) {
			for (int i = 0; i < depth; i++) {
				out.write("\t");
			}
			out.write(this.text);
			out.write("\r\n");
		}
	}

}
