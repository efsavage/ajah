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
package com.ajah.geo.us;

import com.ajah.geo.Country;
import com.ajah.geo.State;
import com.ajah.geo.iso.ISOCountry;

/**
 * List of 50 US states and Washington D.C. Names and abbreviations match USPS
 * standards.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum USState implements State {

	/** Alabama */
	AL("AL", "Alabama"),
	/** Alaska */
	AK("AK", "Alaska"),
	/** Arizona */
	AZ("AZ", "Arizona"),
	/** Arkansas */
	AR("AR", "Arkansas"),
	/** California */
	CA("CA", "California"),
	/** Colorado */
	CO("CO", "Colorado"),
	/** Connecticut */
	CT("CT", "Connecticut"),
	/** Washington D.C. */
	DC("DC", "Washington D.C."),
	/** Delaware */
	DE("DE", "Delaware"),
	/** Florida */
	FL("FL", "Florida"),
	/** Georgia */
	GA("GA", "Georgia"),
	/** Hawaii */
	HI("HI", "Hawaii"),
	/** Idaho */
	ID("ID", "Idaho"),
	/** Illinois */
	IL("IL", "Illinois"),
	/** Indiana */
	IN("IN", "Indiana"),
	/** Iowa */
	IA("IA", "Iowa"),
	/** Kansas */
	KS("KS", "Kansas"),
	/** Kentucky */
	KY("KY", "Kentucky"),
	/** Louisiana */
	LA("LA", "Louisiana"),
	/** Maine */
	ME("ME", "Maine"),
	/** Maryland */
	MD("MD", "Maryland"),
	/** Massachusetts */
	MA("MA", "Massachusetts"),
	/** Michigan */
	MI("MI", "Michigan"),
	/** Minnesota */
	MN("MN", "Minnesota"),
	/** Mississippi */
	MS("MS", "Mississippi"),
	/** Missouri */
	MO("MO", "Missouri"),
	/** Montana */
	MT("MT", "Montana"),
	/** Nebraska */
	NE("NE", "Nebraska"),
	/** Nevada */
	NV("NV", "Nevada"),
	/** New Hampshire */
	NH("NH", "New Hampshire"),
	/** New Mexico */
	NM("NM", "New Mexico"),
	/** New York */
	NY("NY", "New York"),
	/** New Jersey */
	NJ("NJ", "New Jersey"),
	/** North Carolina */
	NC("NC", "North Carolina"),
	/** North Dakota */
	ND("ND", "North Dakota"),
	/** Ohio */
	OH("OH", "Ohio"),
	/** Oklahoma */
	OK("OK", "Oklahoma"),
	/** Oregon */
	OR("OR", "Oregon"),
	/** Pennsylvania */
	PA("PA", "Pennsylvania"),
	/** Puerto Rico */
	PR("PR", "Puerto Rico"),
	/** Rhode Island */
	RI("RI", "Rhode Island"),
	/** South Carolina */
	SC("SC", "South Carolina"),
	/** South Dakota */
	SD("SD", "South Dakota"),
	/** Tennessee */
	TN("TN", "Tennessee"),
	/** Texas */
	TX("TX", "Texas"),
	/** Utah */
	UT("UT", "Utah"),
	/** Vermont */
	VT("VT", "Vermont"),
	/** Virginia */
	VA("VA", "Virginia"),
	/** Washington */
	WA("WA", "Washington"),
	/** West Virginia */
	WV("WV", "West Virginia"),
	/** Wisconsin */
	WI("WI", "Wisconsin"),
	/** Wyoming */
	WY("WY", "Wyoming");

	public static USState get(final String string) {
		for (final USState value : values()) {
			if (value.name().equalsIgnoreCase(string) || value.getAbbr().equalsIgnoreCase(string) || value.getName().equalsIgnoreCase(string)) {
				return value;
			}
		}
		return null;
	}

	private final String id;

	private final String abbr;

	private final String name;

	USState(final String abbr, final String name) {
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
