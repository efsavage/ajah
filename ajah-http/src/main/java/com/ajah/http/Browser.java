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
package com.ajah.http;

import com.ajah.util.IdentifiableEnum;
import com.ajah.util.StringUtils;

/**
 * Enum of browser types. Note that this class is currently incomplete and only
 * does some basic tests for Chrome.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum Browser implements IdentifiableEnum<String> {

	/**
	 * Internet Explorer
	 */
	IE("1", "Internet Explorer", "IE", false),
	/**
	 * Mozilla Firefox
	 */
	FIREFOX("2", "Firefox", "FF", false),
	/**
	 * Google Chrome
	 */
	CHROME("3", "Chrome", "CHR", false),
	/**
	 * Opera
	 */
	OPERA("4", "Opera", "OP", false),
	/**
	 * Safari
	 */
	SAFARI("5", "Safari", "SAF", false),
	/**
	 * Facebook
	 */
	FACEBOOK("1000", "Facebook", "FB", true),
	/**
	 * Google(bot)
	 */
	GOOGLE("1002", "Google", "GOOG", true),
	/**
	 * Amazon's ELB status check
	 */
	ELB("1001", "Amazon ELB", "ELB", true),
	/**
	 * Pingdom
	 */
	PINGDOM("1003", "Pingdom", "PING", true),
	/**
	 * Runscope
	 */
	RUNSCOPE("1004", "Runscope", "RNSC", true),
	/**
	 * Stackdriver
	 */
	STACKDRIVER("1005", "Stackdriver", "STKD", true),
	/**
	 * Unknown
	 */
	UNKNOWN("0", "Unknown", "?", false);

	/**
	 * Looks at user agent string and extracts browser.
	 * 
	 * @param userAgent
	 *            The user agent string
	 * @return The browser, if one can be deduced, or {@link #UNKNOWN}.
	 */
	public static Browser get(final String userAgent) {
		if (StringUtils.isBlank(userAgent)) {
			return UNKNOWN;
		}
		if (userAgent.contains("facebookexternalhit/")) {
			return FACEBOOK;
		} else if (userAgent.contains("Facebot")) {
			return FACEBOOK;
		} else if (userAgent.contains("ELB-HealthChecker/")) {
			return ELB;
		} else if (userAgent.contains("Pingdom.com_bot_version")) {
			return PINGDOM;
		} else if (userAgent.equals("Stackdriver_terminus_bot(http://www.stackdriver.com)")) {
			return STACKDRIVER;
		} else if (userAgent.contains("runscope-radar")) {
			return RUNSCOPE;
		} else if (userAgent.contains("https://developers.google.com/+/web/snippet/")) {
			return GOOGLE;
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

	private final String id;
	private final String name;
	private final String abbreviation;
	private final boolean bot;

	Browser(final String id, final String name, final String abbreviation, final boolean bot) {
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
		this.bot = bot;
	}

	/**
	 * Returns the common abbreviation of the browser. E.G. "IE".
	 * 
	 * @return the common abbreviation of the browser.
	 */
	public String getAbbreviation() {
		return this.abbreviation;
	}

	@Override
	public String getCode() {
		return this.abbreviation;
	}

	/**
	 * Returns the unique ID of this browser. Note that this value is arbitrary
	 * and does not come from the browser itself.
	 * 
	 * @return the id The unique internal ID of the browser.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Returns the common name of the browser. E.G. "Internet Explorer".
	 * 
	 * @return the common name of the browser.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Is ths a known bot or crawler?
	 * 
	 * @return true if this "browser" is known bot or crawler.
	 */
	public boolean isBot() {
		return this.bot;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
