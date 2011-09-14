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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ajah.util.AjahUtils;

/**
 * Utilities for dealing with Dates, times, intervals, etc.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class DateUtils {

	/**
	 * Milliseconds in a day (86,400,000)
	 */
	public static final long DAY_IN_MILLIS = 86400 * 1000L;
	/**
	 * Milliseconds in a week (604,800,000)
	 */
	public static final long WEEK_IN_MILLIS = 7 * DAY_IN_MILLIS;
	/**
	 * Formatter that only returns the name of the day, e.g. "Friday"
	 */
	public static final DateFormat DAY_OF_WEEK_FORMAT = new SimpleDateFormat("EEEE");

	private static final DateFormat NICE_ABSOLUTE_DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);
	private static final DateFormat NICE_ABSOLUTE_TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.SHORT);

	/**
	 * Returns a {@link Long} of the result of {@link Date#getTime()}. If the
	 * supplied date parameter is null, returns a Long with a value of zero.
	 * 
	 * @param date
	 *            Date to convert to UTC.
	 * @return The date in UTC milliseconds, or zero if date is null.
	 */
	public static Long safeToLong(final Date date) {
		if (date == null) {
			return Long.valueOf(0);
		}
		return Long.valueOf(date.getTime());
	}

	/**
	 * Formats a date, relative to the current time, in a "nice" format, such as
	 * "3 minutes ago" or "in two years". Note that this method uses integer
	 * division without fractions so it will appear to round down aggressively.
	 * If the largestUnit parameter is used, no units representing longer
	 * periods of time will be used. For example, if the interval is 30 months,
	 * and largestUnit is {@link CalendarUnit#YEAR}, "2 years" will be returned.
	 * If the largestUnit is {@link CalendarUnit#MONTH}, "30 months" will be
	 * returned.
	 * 
	 * @param date
	 *            Date to format, required.
	 * @param largestUnit
	 *            The largest unit to use as the format unit.
	 * @return The formatted date.
	 */
	public static String niceFormatRelative(final Date date, final CalendarUnit largestUnit) {
		AjahUtils.requireParam(date, "date");
		final long interval = System.currentTimeMillis() - date.getTime();
		if (interval > 0) {
			if (interval < 100 * CalendarUnit.SECOND.getMillis() || largestUnit == CalendarUnit.SECOND) {
				return interval / CalendarUnit.SECOND.getMillis() + " seconds ago";
			} else if (interval < 100 * CalendarUnit.MINUTE.getMillis() || largestUnit == CalendarUnit.MINUTE) {
				return interval / CalendarUnit.MINUTE.getMillis() + " minutes ago";
			} else if (interval < 36 * CalendarUnit.HOUR.getMillis() || largestUnit == CalendarUnit.HOUR) {
				return interval / CalendarUnit.HOUR.getMillis() + " hours ago";
			} else if (interval < 10 * CalendarUnit.DAY.getMillis() || largestUnit == CalendarUnit.DAY) {
				return interval / CalendarUnit.DAY.getMillis() + " days ago";
			} else if (interval < 36 * CalendarUnit.WEEK.getMillis() || largestUnit == CalendarUnit.WEEK) {
				return interval / CalendarUnit.WEEK.getMillis() + " weeks ago";
			} else if (interval < 36 * CalendarUnit.MONTH.getMillis() || largestUnit == CalendarUnit.MONTH) {
				return interval / CalendarUnit.HOUR.getMillis() + " months ago";
			} else {
				return interval / CalendarUnit.YEAR.getMillis() + " years ago";
			}
		}
		if (interval > -100 * CalendarUnit.SECOND.getMillis() || largestUnit == CalendarUnit.SECOND) {
			return "in " + -interval / CalendarUnit.SECOND.getMillis() + " seconds";
		} else if (interval > -100 * CalendarUnit.MINUTE.getMillis() || largestUnit == CalendarUnit.MINUTE) {
			return "in " + -interval / CalendarUnit.MINUTE.getMillis() + " minutes";
		} else if (interval > -36 * CalendarUnit.HOUR.getMillis() || largestUnit == CalendarUnit.HOUR) {
			return "in " + -interval / CalendarUnit.HOUR.getMillis() + " hours";
		} else if (interval > -10 * CalendarUnit.DAY.getMillis() || largestUnit == CalendarUnit.DAY) {
			return "in " + -interval / CalendarUnit.DAY.getMillis() + " days";
		} else if (interval > -36 * CalendarUnit.WEEK.getMillis() || largestUnit == CalendarUnit.WEEK) {
			return "in " + -interval / CalendarUnit.WEEK.getMillis() + " weeks";
		} else if (interval > -36 * CalendarUnit.MONTH.getMillis() || largestUnit == CalendarUnit.MONTH) {
			return "in " + -interval / CalendarUnit.HOUR.getMillis() + " months";
		} else {
			return "in " + -interval / CalendarUnit.YEAR.getMillis() + " years";
		}
	}

	/**
	 * Alias for {@link DateUtils#niceFormatRelative(Date, CalendarUnit)} with
	 * null for the largestUnit parameter.
	 * 
	 * @param date
	 *            Date for format, required.
	 * @return The formatted date.
	 */
	public static String niceFormatRelative(final Date date) {
		return niceFormatRelative(date, null);
	}

	/**
	 * Formats a date, adjusting based on the current time, in a "nice" format,
	 * such as "tomorrow" or "next Tuesday".
	 * 
	 * @param date
	 *            Date to format, required.
	 * @return The formatted date.
	 */
	public static String niceFormatAbsolute(final Date date) {
		StringBuffer string = new StringBuffer();
		Calendar then = Calendar.getInstance();
		then.setTime(date);
		Calendar now = Calendar.getInstance();
		long interval = System.currentTimeMillis() - date.getTime();

		if (now.get(Calendar.DAY_OF_MONTH) == then.get(Calendar.DAY_OF_MONTH)) {
			string.append("Today");
		} else if ((now.get(Calendar.DAY_OF_MONTH) + 1) == then.get(Calendar.DAY_OF_MONTH)) {
			string.append("Tomorrow");
		} else if ((now.get(Calendar.DAY_OF_MONTH) - 1) == then.get(Calendar.DAY_OF_MONTH)) {
			string.append("Yesterday");
		} else if (interval < 0 && interval > -WEEK_IN_MILLIS) {
			// Within the next week
			// TODO handle the case of it being 9:00 am on tuesday and the date
			// is 9:01 the following tuesday
			string.append("Next " + DAY_OF_WEEK_FORMAT.format(date));
		} else if (interval > 0 && interval < WEEK_IN_MILLIS) {
			// Within the past week
			// TODO handle the case of it being 9:00 am on tuesday and the date
			// is 8:59 the previous tuesday
			string.append("Last " + DAY_OF_WEEK_FORMAT.format(date));
		} else {
			string.append(NICE_ABSOLUTE_DATE_FORMAT.format(date));
		}
		string.append(" at ");
		string.append(NICE_ABSOLUTE_TIME_FORMAT.format(date));
		return string.toString();
	}

	/**
	 * Alias for {@link DateUtils#niceFormatRelative(Date, CalendarUnit)} with
	 * null for the largestUnit parameter.
	 * 
	 * @param intervalInMillis
	 *            Interval for format, required.
	 * @return The formatted date.
	 */
	public static String formatInterval(final long intervalInMillis) {
		return formatInterval(intervalInMillis, CalendarUnit.YEAR);
	}

	/**
	 * Formats an interval in a "nice" format, such as "3 minutes" or
	 * "two years". Note that this method uses integer division without
	 * fractions so it will appear to round down aggressively. If the
	 * largestUnit parameter is used, no units representing longer periods of
	 * time will be used. For example, if the interval is 30 months, and
	 * largestUnit is {@link CalendarUnit#YEAR}, "2 years" will be returned. If
	 * the largestUnit is {@link CalendarUnit#MONTH}, "30 months" will be
	 * returned.
	 * 
	 * @param intervalInMillis
	 *            Interval to format, required.
	 * @param largestUnit
	 *            The largest unit to use as the format unit.
	 * @return The formatted date.
	 */
	public static String formatInterval(final long intervalInMillis, final CalendarUnit largestUnit) {
		if (intervalInMillis < CalendarUnit.SECOND.getMillis() || largestUnit == CalendarUnit.SECOND) {
			return intervalInMillis + " milliseconds";
		} else if (intervalInMillis < 100 * CalendarUnit.SECOND.getMillis() || largestUnit == CalendarUnit.SECOND) {
			return intervalInMillis / CalendarUnit.SECOND.getMillis() + " seconds";
		} else if (intervalInMillis < 100 * CalendarUnit.MINUTE.getMillis() || largestUnit == CalendarUnit.MINUTE) {
			return intervalInMillis / CalendarUnit.MINUTE.getMillis() + " minutes";
		} else if (intervalInMillis < 36 * CalendarUnit.HOUR.getMillis() || largestUnit == CalendarUnit.HOUR) {
			return intervalInMillis / CalendarUnit.HOUR.getMillis() + " hours";
		} else if (intervalInMillis < 10 * CalendarUnit.DAY.getMillis() || largestUnit == CalendarUnit.DAY) {
			return intervalInMillis / CalendarUnit.DAY.getMillis() + " days";
		} else if (intervalInMillis < 36 * CalendarUnit.WEEK.getMillis() || largestUnit == CalendarUnit.WEEK) {
			return intervalInMillis / CalendarUnit.WEEK.getMillis() + " weeks";
		} else if (intervalInMillis < 36 * CalendarUnit.MONTH.getMillis() || largestUnit == CalendarUnit.MONTH) {
			return intervalInMillis / CalendarUnit.HOUR.getMillis() + " months";
		} else {
			return intervalInMillis / CalendarUnit.YEAR.getMillis() + " years";
		}
	}

	/**
	 * Returns the date for the day after the current one, at midnight.
	 * 
	 * @return The date for the day after the current one, at midnight.
	 */
	public static Date tomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_MONTH, true);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Adds a number of hours to a date, rolling over other fields as necessary.
	 * 
	 * @see Calendar#add(int, int)
	 * @param date
	 *            The date to add the hours to.
	 * @param hours
	 *            The number of hours to add to the date.
	 * @return The date, with the specified number of hours added to it.
	 */
	public static Date addHours(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hours);
		return calendar.getTime();
	}

}
