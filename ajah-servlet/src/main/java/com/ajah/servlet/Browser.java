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
package com.ajah.servlet;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum Browser {

	IE, FIREFOX, CHROME, OPERA, UNKNOWN;

	/**
	 * Looks at user agent string and extracts browser.
	 * 
	 * @param userAgent
	 *            The user agent string
	 * @return The browser, if one can be deduced, or {@link #UNKNOWN}.
	 */
	public static Browser get(String userAgent) {
		if (userAgent.contains("(KHTML, like Gecko) Chrome/")) {
			return CHROME;
		} else if (userAgent.contains("(KHTML,like Gecko) Chrome/")) {
			return CHROME;
		} else if (userAgent.contains("(KHTML, like Gecko) Slackware/Chrome/")) {
			return CHROME;
		} else if (userAgent.contains("(KHTML, like Gecko) Ubuntu/10.10 Chrome/")) {
			return CHROME;
		} else if (userAgent.contains("Chrome/4.")) {
			return CHROME; 
		} else if (userAgent.contains("Gecko/2009032609 Chrome/")) {
			return CHROME; 
		} else if (userAgent.contains("Chromium/")) {
			return CHROME;
		}
		return UNKNOWN;
	}

}
