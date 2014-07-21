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
package com.ajah.util.data;

import com.ajah.util.IdentifiableEnum;

/**
 * Enumeration of Months. Java has this data but it is often troublesome to get
 * to, especially in the case of partial dates.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public enum Month implements IdentifiableEnum<String> {
	/**
	 * January
	 */
	JANUARY("1", 1, "January", "Jan"),
	/**
	 * February
	 */
	FEBRUARY("2", 2, "February", "Feb"),
	/**
	 * March
	 */
	MARCH("3", 3, "March", "Mar"),
	/**
	 * April
	 */
	APRIL("4", 4, "April", "Apr"),
	/**
	 * May
	 */
	MAY("5", 5, "May", "May"),
	/**
	 * June
	 */
	JUNE("6", 6, "June", "Jun"),
	/**
	 * July
	 */
	JULY("7", 7, "July", "Jul"),
	/**
	 * August
	 */
	AUGUST("8", 8, "August", "Aug"),
	/**
	 * September
	 */
	SEPTEMBER("9", 9, "September", "Sep"),
	/**
	 * October
	 */
	OCTOBER("10", 10, "October", "Oct"),
	/**
	 * November
	 */
	NOVEMBER("11", 11, "November", "Nov"),
	/**
	 * December
	 */
	DECEMBER("12", 12, "December", "Dec");

	/**
	 * Returns the Month that corresponds to the supplied ID.
	 * 
	 * @param _intId
	 *            ID/ordinal value of the month.
	 * @return Month if found, otherwise null.
	 */
	public static Month get(final int _intId) {
		if (_intId < 1 || _intId > 12) {
			return null;
		}
		for (final Month month : values()) {
			if (month.intId == _intId) {
				return month;
			}
		}
		return null;
	}

	private final String id;
	private final int intId;
	private final String name;
	private final String shortName;

	private Month(final String id, final int intId, final String name, final String shortName) {
		this.id = id;
		this.intId = intId;
		this.name = name;
		this.shortName = shortName;
	}

	/**
	 * @see com.ajah.util.IdentifiableEnum#getCode()
	 */
	@Override
	public String getCode() {
		return this.shortName;
	}

	/**
	 * Returns the ID, which is also the ordinal number of the month (e.g.
	 * January = 1, July = 7)
	 * 
	 * @return ID/Ordinal value.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Returns the full name of the month.
	 * 
	 * @return The full name of the month.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the short name (3 characters) of the month.
	 * 
	 * @return The short name (3 characters) of the month.
	 */
	public String getShortName() {
		return this.shortName;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
