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
package com.ajah.util.date;

import java.util.Calendar;

/**
 * This class attempts to make up for the fact that the {@link Calendar} class
 * uses integers for it's enums, because it predates Java having enums. This
 * allows you to use type-safe values in your code and easily convert to what
 * Calendar expects when you need to.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public enum CalendarUnit {

	/** Calendar.SECOND */
	SECOND(1000L, "Minute", "Minutes", Calendar.SECOND),
	/** Calendar.MINUTE */
	MINUTE(60 * 1000L, "Minute", "Minutes", Calendar.MINUTE),
	/** Calendar.HOUR */
	HOUR(60 * MINUTE.millis, "Hour", "Hours", Calendar.HOUR),
	/** Calendar.DAY_OF_MONTH */
	DAY(24 * HOUR.millis, "Day", "Days", Calendar.DAY_OF_MONTH),
	/** Calendar.MONTH */
	MONTH(30 * DAY.millis, "Month", "Months", Calendar.MONTH),
	/** Calendar.WEEK_OF_YEAR */
	WEEK(7 * DAY.millis, "Week", "Weeks", Calendar.WEEK_OF_YEAR),
	/** Calendar.YEAR */
	YEAR(365 * DAY.millis, "Year", "Years", Calendar.YEAR);

	private final int calendarField;
	private final long millis;
	private final String name;
	private final String plural;

	private CalendarUnit(final long millis, final String name, final String plural, final int calendarField) {
		this.millis = millis;
		this.name = name;
		this.plural = plural;
		this.calendarField = calendarField;
	}

	/**
	 * The field from {@link Calendar} that this field is equivalent to.
	 * 
	 * @return The field from {@link Calendar} that this field is equivalent to.
	 */
	public int getCalendarField() {
		return this.calendarField;
	}

	/**
	 * Returns how many milliseconds one of this unit is equivalent to.
	 * 
	 * @return How many milliseconds one of this unit is equivalent to.
	 */
	public long getMillis() {
		return this.millis;
	}

	/**
	 * Returns how many milliseconds the specified quantity of this unit is
	 * equivalent to.
	 * 
	 * @param quantity
	 *            The quantity of this unit to return the equivalent
	 *            milliseconds for.
	 * @return How many milliseconds the specified quantity of this unit is
	 *         equivalent to.
	 */
	public long getMillis(int quantity) {
		return this.millis;
	}

	/**
	 * The singular name of this unit.
	 * 
	 * @return The singular name of this unit.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * The plural name of this unit.
	 * 
	 * @return The plural name of this unit.
	 */
	public String getPlural() {
		return this.plural;
	}

}
