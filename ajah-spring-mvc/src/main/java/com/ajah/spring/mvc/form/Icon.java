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
package com.ajah.spring.mvc.form;

/**
 * List of icons that can be referenced.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public enum Icon {

	/**
	 * No icon.
	 */
	NONE(""),
	/**
	 * Right arrow, black.
	 */
	ARROW_RIGHT("icon-chevron-right"),
	/**
	 * Right arrow, white.
	 */
	ARROW_RIGHT_WHITE("icon-chevron-right icon-white");

	private final String bootstrapClass;

	private Icon(String bootstrapClass) {
		this.bootstrapClass = bootstrapClass;

	}

	/**
	 * The css class that corresponds with this icon in Twitter bootstrap.
	 * 
	 * @return the bootstrapClass The classname from Twitter bootstrap.
	 */
	public String getBootstrapClass() {
		return this.bootstrapClass;
	}
}
