/*
 *  Copyright 2017 Eric F. Savage, code@efsavage.com
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

import com.ajah.util.StringUtils;

/**
 * style element for directly inserting CSS.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode()
public class Style extends AbstractNestableHtmlCoreElement<Style> {

	private String text;
	private String type = "text/css";

	/**
	 * Returns "style"
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#getName()
	 */
	@Override
	public String getName() {
		return "style";
	}

	@Override
	public Style getThis() {
		return this;
	}

	@Override
	protected void renderAttributes(final Writer out) throws IOException {
		write(out, "type", this.type);
	}

	@Override
	protected void renderBeforeChildren(final Writer out, final int depth) throws IOException {
		if (!StringUtils.isBlank(this.text)) {
			out.write("\r\n" + this.text);
		}
	}

}
