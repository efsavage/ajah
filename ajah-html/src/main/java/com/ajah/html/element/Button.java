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

import com.ajah.html.dtd.ButtonType;

/**
 * button element
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Button extends AbstractNestableHtmlCoreElement<Button> implements Input<Button> {

	private String text;
	private ButtonType type;

	/**
	 * Returns "button"
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#getName()
	 */
	@Override
	public String getName() {
		return "button";
	}

	@Override
	public Button getThis() {
		return this;
	}

	@Override
	protected void renderBeforeChildren(final Writer out) throws IOException {
		out.write(this.text);
	}

	/**
	 * Fluent alias to {@link #setText(String)}.
	 * 
	 * @param newText
	 *            The text to set.
	 * @return The current instance.
	 */
	public Button text(final String newText) {
		setText(newText);
		return this;
	}

	@Override
	protected void renderAttributes(final Writer out) throws IOException {
		if (this.type != null) {
			write(out, "type", this.type.name().toLowerCase());
		}
	}

	/**
	 * Fluent alias to {@link #setType(ButtonType)}.
	 * 
	 * @param newType
	 *            The type to set.
	 * @return The current instance.
	 */
	public Button type(final ButtonType newType) {
		setType(newType);
		return this;
	}

}
