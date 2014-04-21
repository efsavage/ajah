/*
 *  Copyright 2012-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ajah.util.RandomUtils;
import com.ajah.util.date.CalendarUnit;

/**
 * Utilities for dealing with threads and multithreading.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class ThreadUtils {

	private static final Logger log = Logger.getLogger(ThreadUtils.class.getName());

	/**
	 * Sleep via {@link #sleep(long)} for the specified length of time.
	 * 
	 * @param quantity
	 *            The quantity of the specified unit.
	 * @param calendarUnit
	 *            The unit of time the quantity represents.
	 */
	public static void sleep(final int quantity, final CalendarUnit calendarUnit) {
		sleep(quantity * calendarUnit.getMillis());
	}

	/**
	 * Sleep for the specified number of ms. If interuppted by
	 * {@link InterruptedException}, just return.
	 * 
	 * @param ms
	 *            The number of ms to sleep, if 0 or less, returns immediately.
	 */
	public static void sleep(final long ms) {
		if (ms <= 0) {
			return;
		}
		try {
			Thread.sleep(ms);
		} catch (final InterruptedException e) {
			log.log(Level.FINE, e.getMessage(), e);
		}
		return;
	}

	/**
	 * Sleeps for a random amount of time between the values specified.
	 * 
	 * @param min
	 *            The minimum amount of time to sleep for.
	 * @param max
	 *            The maximum amount of time to sleep for.
	 */
	public static void sleepRandom(final long min, final long max) {
		sleep(RandomUtils.getRandomNumber(min, max));
	}

	/**
	 * Sleeps until a set time. If the time is in the pass, no sleep occurs.
	 * 
	 * @param timestamp
	 *            The timestamp when the sleep should terminate.
	 */
	public static void sleepUntil(final long timestamp) {
		if (System.currentTimeMillis() < timestamp) {
			sleep(timestamp - System.currentTimeMillis());
		}
	}

}
