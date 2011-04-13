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

import com.ajah.html.dtd.InputType;

/**
 * Basic Input class. May become obsolete as other Input classes are implmented.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InputImpl extends AbstractNestableHtmlCoreElement<InputImpl> implements Input<InputImpl> {

	/**
	 * @param name
	 *            Name attribute.
	 * @param value
	 *            Value attribute.
	 * @param type
	 *            Type attribute.
	 */
	public InputImpl(String name, String value, InputType type) {
		this.id = name;
		this.name = name;
		this.value = value;
		this.type = type;
		setCssClass(type.name().toLowerCase());
	}

	/**
	 * Will create a {@link Label} element as a sibling.
	 * 
	 * @param label
	 *            Text to use to create a sibling Label.
	 * @param name
	 *            Name attribute.
	 * @param value
	 *            Value attribute.
	 * @param type
	 *            Type attribute.
	 */
	public InputImpl(String label, String name, String value, InputType type) {
		this(name, value, type);
		this.label = new Label(this, label).css(type.name().toLowerCase());
	}

	private final String name;
	private Label label;
	private final InputType type;
	private String value;

	/**
	 * Renders attributes and also the sibling Label.
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#render(java.io.Writer,
	 *      int)
	 */
	@Override
	public void render(Writer out, int depth) throws IOException {
		if (this.label != null) {
			this.label.render(out, depth);
		}
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		out.write("<input");
		writeCore(out);
		write(out, "name", this.name);
		write(out, "value", this.value);
		write(out, "type", this.type.name().toLowerCase());
		out.write(" />");
		if (depth >= 0) {
			out.write("\r\n");
		}
	}

	@Override
	InputImpl getThis() {
		return this;
	}
}