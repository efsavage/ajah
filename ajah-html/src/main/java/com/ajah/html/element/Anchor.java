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
import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.ajah.util.StringUtils;

/**
 * a element
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Anchor extends AbstractNestableHtmlCoreElement<Anchor> {

	/**
	 * _blank
	 */
	public static final String TARGET_BLANK = "_blank";

	private String href;
	private String text;
	private String target;

	/**
	 * Public constructor.
	 * 
	 * @param url
	 *            The URL for the links href, nullable.
	 * @param text
	 *            The text of the link, nullable.
	 */
	public Anchor(final URL url, final String text) {
		this.href = url.toExternalForm();
		this.text = text;
	}

	/**
	 * Returns "a"
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#getName()
	 */
	@Override
	public String getName() {
		return "a";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Anchor getThis() {
		return this;
	}

	@Override
	protected void renderAttributes(final Writer out) throws IOException {
		write(out, "href", this.href);
		write(out, "target", this.target);
	}

	@Override
	protected void renderBeforeChildren(final Writer out, final int depth) throws IOException {
		if (StringUtils.isBlank(this.text)) {
			out.write(this.text);
		}
	}

}
