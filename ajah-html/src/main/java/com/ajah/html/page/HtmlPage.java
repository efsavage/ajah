/*
 *  Copyright 2012-2017 Eric F. Savage, code@efsavage.com
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
package com.ajah.html.page;

import java.io.IOException;
import java.io.OutputStream;

import com.ajah.html.HtmlVersion;
import com.ajah.html.element.Body;
import com.ajah.html.element.Div;
import com.ajah.html.element.Html;
import com.ajah.html.element.Paragraph;
import com.ajah.html.element.UnorderedList;
import com.ajah.html.element.head.Head;
import com.ajah.html.element.head.Meta;
import com.ajah.html.element.head.Title;
import com.ajah.util.AjahUtils;

/**
 * This is a helper object that adds convenience methods for constructing an
 * &lt;html> element.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class HtmlPage {

	HtmlVersion version = HtmlVersion.HTML5;
	private final Html html;
	private final Body body = new Body();
	private final Head head = new Head();

	/**
	 * Public constructor.
	 * 
	 * @param version
	 *            The version of this document, required.
	 */
	public HtmlPage(final HtmlVersion version) {
		AjahUtils.requireParam(version, "version");
		this.version = version;
		this.html = new Html(version);
		this.html.add(this.head);
		this.html.add(this.body);
	}

	/**
	 * Adds a {@link Paragraph} element to this page's {@link Body} element.
	 * 
	 * @param div
	 *            The div element to add to the page's body.
	 * @return This page.
	 */
	public HtmlPage add(final Div div) {
		this.body.add(div);
		return this;
	}

	/**
	 * Adds a {@link Paragraph} element to this page's {@link Body} element.
	 * 
	 * @param paragraph
	 *            The paragraph to add to the page's body.
	 * @return This page.
	 */
	private HtmlPage add(final Paragraph paragraph) {
		this.body.add(paragraph);
		return this;
	}

	/**
	 * Adds a new {@link UnorderedList} to the body.
	 * 
	 * @param unorderedList
	 *            The list to add.
	 */
	public void add(final UnorderedList unorderedList) {
		this.body.add(unorderedList);
	}

	/**
	 * Creates a new {@link Paragraph} and calls {@link #add(Paragraph)}.
	 * 
	 * @param paragraphText
	 *            The text of the paragraph.
	 * @return This page.
	 */
	public HtmlPage addParagraph(final String paragraphText) {
		return add(new Paragraph().text(paragraphText));
	}

	/**
	 * Adds a new {@link Meta} element to this page's {@link Head} element.
	 * 
	 * @param property
	 *            The value of the property attribute.
	 * @param content
	 *            The value of the content attribute.
	 * @return This page.
	 */
	public HtmlPage meta(final String property, final String content) {
		this.head.add(new Meta(property, null, content));
		return this;
	}

	/**
	 * Adds a new {@link Meta} "refresh" element to this page's {@link Head}
	 * element.
	 * 
	 * @param delay
	 *            The delay before redirecting.
	 * @param url
	 *            The url to redirect to.
	 * @return This page.
	 */
	public HtmlPage metaRefresh(final int delay, final String url) {
		this.head.add(new Meta(null, "refresh", delay + "; url=" + url));
		return this;
	}

	/**
	 * Returns the value of {@link Html#render()}.
	 * 
	 * @return The value of {@link Html#render()}
	 */
	public String render() {
		return this.html.render();
	}

	/**
	 * @param out
	 * @throws IOException
	 */
	public void render(final OutputStream out) throws IOException {
		out.write(this.html.render().getBytes());
	}

	/**
	 * Adds a new {@link Title} element to this page's {@link Head} element.
	 * 
	 * @param title
	 *            The text of the title element.
	 * @return This page.
	 */
	public HtmlPage title(final String title) {
		this.head.add(new Title(title));
		return this;
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
		this.head.css(selector, property, value);
	}

}
