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
 * Checkbox input.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Checkbox extends AbstractNestableHtmlCoreElement<Checkbox> implements Input<Checkbox> {

	private final String name;
	private Label label;
	private String value;
	private boolean checked;

	/**
	 * @param name
	 *            Name attribute.
	 * @param value
	 *            Value attribute.
	 * @param checked
	 *            Checked attribute.
	 */
	public Checkbox(String name, String value, boolean checked) {
		this.id = name;
		this.name = name;
		this.value = value;
		this.checked = checked;
		setCssClass("checkbox");
	}

	/**
	 * Will create a {@link Label} element as a sibling.
	 * 
	 * @param label
	 *            Text to base new Label element on.
	 * @param name
	 *            Name attribute.
	 * @param value
	 *            Value attribute.
	 * @param checked
	 *            Checked attribute.
	 */
	public Checkbox(String label, String name, String value, boolean checked) {
		this(name, value, checked);
		this.label = new Label(this, label).css("checkbox");
	}

	/**
	 * Renders attributes and also the sibling Label.
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#render(java.io.Writer,
	 *      int)
	 */
	@Override
	public void render(Writer out, int depth) throws IOException {
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		out.write("<input ");
		write(out);
		write(out, "name", this.name);
		write(out, "value", this.value);
		write(out, "type", "checkbox");
		write(out, "checked", this.checked ? "checked" : null);
		out.write(" />");
		out.write("\r\n");
		this.label.render(out, depth);
	}

	@Override
	Checkbox getThis() {
		return this;
	}

}