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
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.html.HtmlElement;
import com.ajah.html.Nestable;
import com.ajah.util.StringUtils;

/**
 * Extension of AbstractHtmlCoreElement that implements Nestable and allows for
 * child elements.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * @param <T>
 *            Type of element (mainly for fluid methods like css())
 * 
 */

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractNestableHtmlCoreElement<T> extends AbstractHtmlCoreElement<T> implements Nestable {

	private static final Logger log = Logger.getLogger(AbstractNestableHtmlCoreElement.class.getName());

	/**
	 * Creates an element and a {@link CData} child element with the value
	 * specified.
	 * 
	 * @param cData
	 *            Value to create the {@link CData} with.
	 */
	public AbstractNestableHtmlCoreElement(String cData) {
		this.children = new ArrayList<>();
		this.children.add(new CData(cData));
	}

	/**
	 * Default no-arg constructor.
	 */
	public AbstractNestableHtmlCoreElement() {
		// Empty
	}

	protected List<HtmlElement<?>> children = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <R extends HtmlElement<R>> R add(R element) {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(element);
		return element;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Writer out, int depth) throws IOException {
		for (int i = 0; i < depth; i++) {
			out.write("\t");
		}
		out.write("<");
		out.write(getName());
		writeCore(out);
		if (!StringUtils.isBlank(getStyle())) {
			out.write(" style=\"" + getStyle() + "\"");
		}
		renderAttributes(out);
		out.write(">");
		if (depth >= 0 && this.children != null) {
			out.write("\r\n");
		}
		renderBeforeChildren(out);
		if (this.children != null) {
			for (HtmlElement<?> child : this.children) {
				if (depth >= 0) {
					child.render(out, depth + 1);
				} else {
					child.render(out, depth);
				}
			}
		}
		if (depth >= 0 && this.children != null) {
			out.write("\r\n");
			for (int i = 0; i < depth; i++) {
				out.write("\t");
			}
		}
		out.write("</");
		out.write(getName());
		out.write(">");
		if (depth >= 0) {
			out.write("\r\n");
		}
	}

	/**
	 * Returns a rendering of this element (and children) as a string.
	 * 
	 * @see AbstractNestableHtmlCoreElement#render(Writer, int)
	 * @return A rendering of this element (and children) as a string.
	 */
	public String render() {
		try {
			Writer writer = new StringWriter();
			render(writer, 0);
			return writer.toString();
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Called after the standard attributes are written, while still in the
	 * opening tag. Does nothing unless overridden.
	 * 
	 * @param out
	 *            The writer to write to.
	 * @throws IOException
	 *             If the writer cannot be written to.
	 */
	protected void renderAttributes(Writer out) throws IOException {
		// Empty
	}

	/**
	 * Called immediately after the opening tag is completed. Does nothing
	 * unless overridden.
	 * 
	 * @param out
	 *            The writer to write to.
	 * @throws IOException
	 *             If the writer cannot be written to.
	 */
	protected void renderBeforeChildren(Writer out) throws IOException {
		// Empty
	}

}
