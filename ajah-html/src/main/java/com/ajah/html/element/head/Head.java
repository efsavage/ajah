/*
 *  Copyright 2011-2017 Eric F. Savage, code@efsavage.com
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
package com.ajah.html.element.head;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.ajah.css.CssRule;
import com.ajah.html.element.AbstractNestableHtmlCoreElement;
import com.ajah.html.element.Style;
import com.ajah.util.CollectionUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * head element
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */

@Data
@EqualsAndHashCode()
public class Head extends AbstractNestableHtmlCoreElement<Head> {

	private String titleTag;
	private List<CssRule> cssRules = null;
	private Style styleChild = null;

	/**
	 * Returns "head"
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#getName()
	 */
	@Override
	public String getName() {
		return "head";
	}

	@Override
	public Head getThis() {
		return this;
	}

	@Override
	protected void renderChildren(final Writer out, final int depth) throws IOException {
		if (!CollectionUtils.isEmpty(cssRules)) {
			if (styleChild == null) {
				StringBuilder styleBody = new StringBuilder();
				for (CssRule cssRule : cssRules) {
					styleBody.append(cssRule.getRaw());
					styleBody.append("\r\n");
				}
				styleChild = new Style();
				styleChild.setText(styleBody.toString());
				add(styleChild);
			}
		}
		super.renderChildren(out, depth);
	}

	/**
	 * Adds a new CSS declaration to the page.
	 * 
	 * @param selector
	 *            The selector to add a style for.
	 * @param property
	 *            The CSS property to to add/set.
	 * @param value
	 *            The value of the CSS property to to add/set.
	 */
	public void css(String selector, String property, String value) {
		if (this.cssRules == null) {
			this.cssRules = new ArrayList<>();
		}
		CssRule cssRule = new CssRule(selector + " { " + property + ": " + value + "}", null);
		this.cssRules.add(cssRule);
	}

}
