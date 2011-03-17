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

import com.ajah.html.HtmlElement;
import com.ajah.html.Nestable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Extension of AbstractHtmlCoreElement that implements Nestable and allows for
 * child elements.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            Type of element (mainly for fluid methods like css())
 * 
 */

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractNestableHtmlCoreElement<T> extends AbstractHtmlCoreElement<T> implements Nestable {

	protected List<HtmlElement<?>> children = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void add(HtmlElement<?> element) {
		if (this.children == null) {
			this.children = new ArrayList<HtmlElement<?>>();
		}
		this.children.add(element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Writer out) throws IOException {
		out.write("<");
		out.write(getName());
		write(out);
		out.write(">");
		for (HtmlElement<?> child : this.children) {
			child.render(out);
		}
		out.write("</");
		out.write(getName());
		out.write(">\r\n");
	}

}