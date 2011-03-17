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

import com.ajah.html.HtmlCoreElement;
import com.ajah.html.attr.ScriptAttribute;
import com.ajah.html.dtd.Direction;
import com.ajah.util.StringUtils;

/**
 * Basic implementation of HtmlCoreElement that most entities will use.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
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

	protected void write(Writer out) throws IOException {
		write(out, "id", getId());
		write(out, "class", getCssClass());
	}

	protected void write(Writer out, String name, String value) throws IOException {
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
	abstract String getName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T css(String _cssClass) {
		if (getCssClass() != null) {
			setCssClass(getCssClass() + " " + _cssClass);
		} else {
			setCssClass(_cssClass);
		}
		return getThis();
	}

	/**
	 * Returns a reference to this object, mostly used for fluid methods.
	 * 
	 * @return A reference to this object, mostly used for fluid methods. Can
	 *         never be null.
	 */
	abstract T getThis();

}