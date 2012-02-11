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

/**
 * Label element.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Label extends AbstractNestableHtmlCoreElement<Label> {

	/**
	 * @param input
	 *            Input this Label is attached to (via "for" attribute)
	 * @param value
	 *            Value of this label.
	 */
	public Label(Input<?> input, String value) {
		this.input = input;
		this.value = value;
	}

	private Input<?> input;
	private String value;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Writer out, int depth) throws IOException {
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		out.write("<label");
		writeCore(out);
		write(out, "for", this.input.getId());
		out.write(">");
		out.write(this.value);
		out.write("</label>");
		if (depth >= 0) {
			out.write("\r\n");
		}
	}

	/**
	 * Returns "label"
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#getName()
	 */
	@Override
	public String getName() {
		return "label";
	}

	@Override
	public
	Label getThis() {
		return this;
	}

}