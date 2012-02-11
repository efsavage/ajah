/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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
import com.ajah.html.element.Html;
import com.ajah.html.element.Paragraph;
import com.ajah.html.element.head.Head;
import com.ajah.html.element.head.Title;

/**
 * This is a helper object that adds convenience methods for constructing an
 * &lt;html> element.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class HtmlPage {

	HtmlVersion version = HtmlVersion.HTML5;
	private final Html html;
	private final Body body = new Body();
	private final Head head = new Head();

	public HtmlPage(HtmlVersion version) {
		this.version = version;
		this.html = new Html(version);
		this.html.add(head);
		this.html.add(body);
	}

	public HtmlPage title(String title) {
		head.add(new Title(title));
		return this;
	}

	public HtmlPage addParagraph(String paragraphText) {
		return add(new Paragraph().text(paragraphText));
	}

	/**
	 * @param text
	 * @return
	 */
	private HtmlPage add(Paragraph paragraph) {
		this.body.add(paragraph);
		return this;
	}

	/**
	 * @param out
	 * @throws IOException
	 */
	public void render(OutputStream out) throws IOException {
		out.write(this.html.render().getBytes());
	}

}
