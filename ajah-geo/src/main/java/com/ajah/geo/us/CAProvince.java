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
package com.ajah.geo.us;

import com.ajah.geo.Country;
import com.ajah.geo.State;
import com.ajah.geo.iso.ISOCountry;

/**
 * List of 10 Canadian provinces. Names and abbreviations match postal
 * standards.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum CAProvince implements State {

	/**
	 * Ontario
	 */
	ON("ON", "Ontario"),
	/**
	 * Quebec
	 */
	QC("QC", "Quebec"),
	/**
	 * Nova Scotia
	 */
	NS("NS", "Nova Scotia"),
	/**
	 * New Brunswick
	 */
	NB("NB", "New Brunswick"),
	/**
	 * Manitoba
	 */
	MB("MB", "Manitoba"),
	/**
	 * British Columbia
	 */
	BC("BC", "British Columbia"),
	/**
	 * Prince Edward Island
	 */
	PE("PE", "Prince Edward Island"),
	/**
	 * Saskatchewan
	 */
	SK("SK", "Saskatchewan"),
	/**
	 * Alberta
	 */
	AB("AB", "Alberta"),
	/**
	 * Newfoundland and Labrador
	 */
	NL("NL", "Newfoundland and Labrador");

	public static CAProvince get(final String string) {
		for (final CAProvince value : values()) {
			if (value.name().equalsIgnoreCase(string) || value.getAbbr().equalsIgnoreCase(string) || value.getName().equalsIgnoreCase(string)) {
				return value;
			}
		}
		return null;
	}

	private final String id;

	private final String abbr;

	private final String name;

	private CAProvince(final String abbr, final String name) {
		this.id = abbr.toLowerCase();
		this.abbr = abbr;
		this.name = name;
	}

	/**
	 * The public abbreviation of the state. Matches the official USPS
	 * nomenclature.
	 * 
	 * Example: The abbreviation of Massachusetts, USA would be "MA".
	 * 
	 * @see com.ajah.geo.State#getAbbr()
	 * @return The public abbreviation of the state. Should never be null or
	 *         empty.
	 */
	@Override
	public String getAbbr() {
		return this.abbr;
	}

	/**
	 * Always returns {@link ISOCountry#US}
	 * 
	 * @see com.ajah.geo.State#getCountry()
	 */
	@Override
	public Country getCountry() {
		return ISOCountry.US;
	}

	/**
	 * The unique ID of the state, which is the lowercase version of the
	 * official USPS nomenclature.
	 * 
	 * Example: The ID of Massachusetts, USA would be "ma".
	 * 
	 * @see com.ajah.geo.State#getId()
	 * @see com.ajah.geo.State#getAbbr()
	 * @return The unique ID of the state
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

}
