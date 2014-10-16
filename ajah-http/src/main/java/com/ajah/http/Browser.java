/*
 *  Copyright 2011-2012 Eric F. Savage, code@efsavage.com
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
package com.ajah.http;

/**
 * Enum of browser types. Note that this class is currently incomplete and only
 * does some basic tests for Chrome.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum Browser {

	/**
	 * Internet Explorer
	 */
	IE(1, "Internet Explorer", "IE"),
	/**
	 * Mozilla Firefox
	 */
	FIREFOX(2, "Firefox", "FF"),
	/**
	 * Google Chrome
	 */
	CHROME(3, "Chrome", "CHR"),
	/**
	 * Opera
	 */
	OPERA(4, "Opera", "OP"),
	/**
	 * Safari
	 */
	SAFARI(5, "Safari", "SAF"),
	/**
	 * Facebook
	 */
	FACEBOOK(6, "Facebook", "FB"),
	/**
	 * Unknown
	 */
	UNKNOWN(0, "Unknown", "?");

	/**
	 * Looks at user agent string and extracts browser.
	 * 
	 * @param userAgent
	 *            The user agent string
	 * @return The browser, if one can be deduced, or {@link #UNKNOWN}.
	 */
	public static Browser get(final String userAgent) {
		if (userAgent.contains("facebookexternalhit/")) {
			return FACEBOOK;
		} else if (userAgent.contains("Facebot")) {
			return FACEBOOK;
		} else if (userAgent.contains("(KHTML, like Gecko) Chrome/")) {
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

	private final int id;
	private final String name;

	private final String abbreviation;

	private Browser(final int id, final String name, final String abbreviation) {
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;

	}

	/**
	 * Returns the common abbreviation of the browser. E.G. "IE".
	 * 
	 * @return the common abbreviation of the browser.
	 */
	public String getAbbreviation() {
		return this.abbreviation;
	}

	/**
	 * Returns the unique ID of this browser. Note that this value is arbitrary
	 * and does not come from the browser itself.
	 * 
	 * @return the id The unique internal ID of the browser.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Returns the common name of the browser. E.G. "Internet Explorer".
	 * 
	 * @return the common name of the browser.
	 */
	public String getName() {
		return this.name;
	}

}
