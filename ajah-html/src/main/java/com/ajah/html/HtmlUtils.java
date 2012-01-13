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
package com.ajah.html;

/**
 * 
 * Utilities for dealing with HTML.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class HtmlUtils {

	/**
	 * Converts plaintext into HTML. Currently just translates \n to <br />
	 * .
	 * 
	 * @param plainText
	 *            The text to translate.
	 * @return The translated html.
	 */
	public static String toBodyHtml(String plainText) {
		// TODO handle other formatting (bulleted lists, paragraphs, etc.)
		String htmlText = plainText.trim().replaceAll("\n", "<br />\n");
		return htmlText;
	}

}
