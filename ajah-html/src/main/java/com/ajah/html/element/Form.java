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
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.html.dtd.FormMethod;

/**
 * Form element.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Form extends AbstractNestableHtmlCoreElement<Form> {

	private String action;
	private FormMethod method = FormMethod.GET;
	private List<Input<?>> inputs = new ArrayList<>();

	/**
	 * Public constructor.
	 * 
	 * @param method
	 *            The method attribute.
	 */
	public Form(FormMethod method) {
		this.method = method;
	}

	/**
	 * Renders this element and child inputs.
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#render(java.io.Writer,
	 *      int)
	 */
	@Override
	public void render(Writer out, int depth) throws IOException {
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		out.write("<form");
		writeCore(out);
		write(out, "action", this.action);
		write(out, "method", this.method.name().toLowerCase());
		out.write(">");
		if (depth >= 0) {
			out.write("\r\n");
		}
		for (Input<?> input : this.inputs) {
			for (int i = 0; i <= depth; i++) {
				out.write("\t");
			}
			out.write("<div>");
			if (depth >= 0) {
				out.write("\r\n");
				input.render(out, depth + 2);
			} else {
				input.render(out, depth);
			}
			for (int i = 0; i <= depth; i++) {
				out.write("\t");
			}
			out.write("</div>");
			if (depth >= 0) {
				out.write("\r\n");
			}
		}
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		out.write("</form>");
		if (depth >= 0) {
			out.write("\r\n");
		}
	}

	/**
	 * Returns "form"
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#getName()
	 */
	@Override
	public String getName() {
		return "form";
	}

	@Override
	Form getThis() {
		return this;
	}

}