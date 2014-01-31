/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.servlet.util;

/**
 * A list of headers that are valid to send back on a response.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public enum ResponseHeader {

	/**
	 * Access-Control-Allow-Origin
	 */
	ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
	/**
	 * Expires
	 */
	EXPIRES("Expires"),
	/**
	 * X-Frame-Options
	 */
	X_FRAME_OPTIONS("X-Frame-Options"),
	/**
	 * X-Content-Type-Options
	 */
	X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options");

	private final String header;

	private ResponseHeader(String header) {
		this.header = header;

	}

	/**
	 * Returns the actual header value to use.
	 * 
	 * @return the header
	 */
	public String getHeader() {
		return this.header;
	}
}
