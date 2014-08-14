/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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

import java.net.URL;

/**
 * ul element
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class UnorderedList extends AbstractNestableHtmlCoreElement<UnorderedList> {

	/**
	 * Returns "ul"
	 * 
	 * @see com.ajah.html.element.AbstractNestableHtmlCoreElement#getName()
	 */
	@Override
	public String getName() {
		return "ul";
	}

	@Override
	public UnorderedList getThis() {
		return this;
	}

	/**
	 * Creates a new {@link Anchor} within a new {@link ListItem} and adds it to
	 * this element's children.
	 * 
	 * @param url
	 *            The URL of the link.
	 * @param text
	 *            The text to display for the link.
	 */
	public void addLink(URL url, String text) {
		ListItem li = new ListItem();
		Anchor a = new Anchor(url, text);
		li.add(a);
		add(li);
	}

}
