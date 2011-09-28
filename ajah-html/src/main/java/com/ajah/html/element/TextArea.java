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
 * Textarea.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TextArea extends AbstractNestableHtmlCoreElement<TextArea> implements Input<TextArea> {

	private final String name;
	private Label label;
	private final InputType type;
	private String value;
	private final boolean html;

	/**
	 * @param name
	 *            Name attribute.
	 * @param value
	 *            Value attribute.
	 * @param type
	 *            Type attribute.
	 * @param html
	 *            Should this textarea allow HTML?
	 */
	public TextArea(String name, String value, InputType type, boolean html) {
		this.id = name;
		this.name = name;
		this.value = value;
		this.type = type;
		this.html = html;
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
	 * @param html
	 *            Should this textarea allow HTML?
	 */
	public TextArea(String label, String name, String value, InputType type, boolean html) {
		this(name, value, type, html);
		this.label = new Label(this, label).css(type.name().toLowerCase());
	}

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
		if (depth >= 0) {
			out.write("\r\n");
		}
		out.write("<textarea");
		writeCore(out);
		write(out, "name", this.name);
		out.write(">");
		out.write(this.value);
		out.write("</textarea>");
	}

	@Override
	TextArea getThis() {
		return this;
	}
}
