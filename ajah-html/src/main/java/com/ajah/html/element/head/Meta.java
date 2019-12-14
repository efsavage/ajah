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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.html.HtmlElement;
import com.ajah.util.StringUtils;

/**
 * meta element
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */

@Data
@EqualsAndHashCode()
@AllArgsConstructor
public class Meta implements HtmlElement<Meta> {

	private String property;
	private String httpEquiv;
	private String content;

	/**
	 * @see com.ajah.html.HtmlElement#render(java.io.Writer, int)
	 */
	@Override
	public void render(final Writer out, final int depth) throws IOException {
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		out.write("<meta ");
		if (!StringUtils.isBlank(this.property)) {
			out.write("property=\"" + this.property + "\"");
		}
		if (!StringUtils.isBlank(this.httpEquiv)) {
			out.write("http-equiv=\"" + this.httpEquiv + "\"");
		}
		if (!StringUtils.isBlank(this.content)) {
			out.write("content=\"" + this.content + "\"");
		}
		out.write(" />\r\n");
	}

}
