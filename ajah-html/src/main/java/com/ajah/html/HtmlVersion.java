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
 * The version of HTML to use, currently only used for the prologue, but could
 * be used for enforcing things like well-formedness.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum HtmlVersion {

	/**
	 * HTML 5
	 */
	HTML5("<!doctype html>");

	private final String prologue;

	HtmlVersion(final String prologue) {
		this.prologue = prologue;
	}

	/**
	 * Returns the doctype/prologue of the document.
	 * 
	 * @return The doctype/prologue of the document.
	 */
	public String getPrologue() {
		return this.prologue;
	}

}
