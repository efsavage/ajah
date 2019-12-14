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
 * Select.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode()
public class Select extends AbstractNestableHtmlCoreElement<Select> implements Input<Select> {

	private final String name;
	private Label label;

	/**
	 * Will create a {@link Label} element as a sibling.
	 * 
	 * @param label
	 *            Text to use to create a sibling Label.
	 * @param name
	 *            Name attribute.
	 */
	public Select(final String label, final String name) {
		this.id = name;
		this.name = name;
		this.label = new Label(this, label).css("select");
	}

	@Override
	public Select getThis() {
		return this;
	}

	/**
	 * Renders attributes and also the sibling Label.
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#render(java.io.Writer,
	 *      int)
	 */
	@Override
	public void render(final Writer out, final int depth) throws IOException {
		if (this.label != null) {
			this.label.render(out, depth);
		}
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		out.write("<select");
		writeCore(out);
		write(out, "name", this.name);
		out.write(">");
		if (depth >= 0) {
			out.write("\r\n");
		}
		renderChildren(out, depth);
		out.write("</select>");
		if (depth >= 0) {
			out.write("\r\n");
		}
	}
}
