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
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import com.ajah.html.HtmlCoreElement;
import com.ajah.html.attr.ScriptAttribute;
import com.ajah.html.dtd.Direction;
import com.ajah.util.StringUtils;

/**
 * Basic implementation of HtmlCoreElement that most entities will use.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * @param <T>
 *            Type of element (mainly for fluid methods like css())
 * 
 */

@Data
public abstract class AbstractHtmlCoreElement<T> implements HtmlCoreElement<T> {

	protected String id;

	protected String cssClass;

	protected String style;

	protected String title;

	protected String lang;

	protected Direction dir;

	protected ScriptAttribute onClick;

	protected ScriptAttribute onDblClick;

	protected ScriptAttribute onMouseDown;

	protected ScriptAttribute onMouseUp;

	protected ScriptAttribute onMouseOver;

	protected ScriptAttribute onMouseMove;

	protected ScriptAttribute onMouseOut;

	protected ScriptAttribute onKeyPress;

	protected ScriptAttribute onKeyDown;

	protected ScriptAttribute onKeyUp;

	private Map<String, String> dataElements;

	protected void writeCore(final Writer out) throws IOException {
		write(out, "id", getId());
		write(out, "class", getCssClass());
		if (this.dataElements != null) {
			for (final String key : this.dataElements.keySet()) {
				write(out, "data-" + key, this.dataElements.get(key));
			}
		}
	}

	protected void write(final Writer out, final String name, final String value) throws IOException {
		if (!StringUtils.isBlank(value)) {
			out.write(" ");
			out.write(name);
			out.write("=\"");
			out.write(value);
			out.write("\"");
		}
	}

	/**
	 * Returns the name of the element as it should appear in the output.
	 * 
	 * Example: The name of Div is "div".
	 * 
	 * @return The name of the element as it should appear in the output. Should
	 *         never be null.
	 */
	public abstract String getName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T css(final String _cssClass) {
		if (getCssClass() != null) {
			setCssClass(getCssClass() + " " + _cssClass);
		} else {
			setCssClass(_cssClass);
		}
		return getThis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T data(final String key, final String value) {
		addDataElement(key, value);
		return getThis();
	}

	private synchronized void addDataElement(final String key, final String value) {
		if (this.dataElements == null) {
			this.dataElements = new HashMap<>();
		}
		this.dataElements.put(key, value);
	}

	/**
	 * Returns a reference to this object, mostly used for fluid methods.
	 * 
	 * @return A reference to this object, mostly used for fluid methods. Can
	 *         never be null.
	 */
	public abstract T getThis();

}
