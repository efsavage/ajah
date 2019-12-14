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
package com.ajah.util.log;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.ajah.util.compare.EntryValueComparator;
import com.ajah.util.text.Strings;

/**
 * An atomic counter for keeping track of things, intended but not restricted to
 * tally against the values of an enum.
 * 
 * @author efsavage
 * @param <T>
 */
public class Tally<T> {

	protected final ConcurrentMap<T, Long> map = new ConcurrentHashMap<>();
	protected final AtomicLong errors = new AtomicLong();
	protected final AtomicLong successes = new AtomicLong();
	protected PrintStream out;

	/**
	 * Public constructor.
	 * 
	 * @param out
	 *            The output for reporting.
	 */
	public Tally(final PrintStream out) {
		this.out = out;
	}

	/**
	 * Increment the error count.
	 */
	public void error() {
		this.errors.incrementAndGet();
	}

	/**
	 * Return the error count.
	 * 
	 * @return The error count.
	 */
	public long getErrors() {
		return this.errors.get();
	}

	/**
	 * Return the success count.
	 * 
	 * @return The success count.
	 */
	public long getSuccesses() {
		return this.successes.get();
	}

	/**
	 * Return the total count.
	 * 
	 * @return The total count.
	 */
	public long getTotal() {
		return this.errors.get() + this.successes.get();
	}

	/**
	 * Generates a report only if the {@link #getTotal()} evenly divides by the
	 * interval, and is greater than zero.
	 * 
	 * @param interval
	 *            The interval to space reports by
	 * @see #report()
	 */
	public void intervalReport(final int interval) {
		if (getTotal() > 0 && (getTotal() % interval) == 0) {
			report();
		}
	}

	/**
	 * Write a report with totals to the configured output.
	 */
	public void report() {
		report(0);
	}

	/**
	 * Write a report with totals to the configured output.
	 * 
	 * @param threshold
	 *            Only print totals of at least this value.
	 */
	public void report(final long threshold) {
		this.out.println(Strings.HYPEN35);
		this.out.println("Success/Error/Total: " + getSuccesses() + "/" + getErrors() + "/" + getTotal() + " - " + NumberFormat.getPercentInstance().format(1.0 * getSuccesses() / getTotal()));
		for (final T t : this.map.keySet()) {
			if (this.map.get(t).longValue() >= threshold) {
				this.out.println(t.toString() + ": " + this.map.get(t));
			}
		}
	}

	public void reportTop(final int number) {
		this.out.println(Strings.HYPEN35);
		this.out.println("Success/Error/Total: " + getSuccesses() + "/" + getErrors() + "/" + getTotal() + " - " + NumberFormat.getPercentInstance().format(1.0 * getSuccesses() / getTotal()));
		List<Entry<T, Long>> top = new ArrayList<>(this.map.entrySet());
		Collections.sort(top, new EntryValueComparator<T, Long>());
		if (top.size() > number) {
			top = top.subList(0, number);
		}
		for (final Entry<T, Long> entry : top) {
			this.out.println(entry.getKey().toString() + ": " + entry.getValue());
		}
	}

	/**
	 * Builds a simple report with a line for each keys/count.
	 *
 	 * @return A multi-line string with keys and current counts.
	 */
	public String getReport() {
		StringBuilder builder = new StringBuilder();
		for (final T t : this.map.keySet()) {
			if (builder.length() > 0) {
				builder.append("\n");
			}
			builder.append(t.toString() + ": " + this.map.get(t));
		}
		return builder.toString();
	}

	/**
	 * Increment the success count.
	 */
	public void success() {
		this.successes.incrementAndGet();
	}

	/**
	 * Tally an object with an increment of 1. This works best with enums.
	 * 
	 * @param tallyObject
	 *            The object to tally by.
	 */
	public long tally(final T tallyObject) {
		return tally(tallyObject, 1);
	}

	/**
	 * Get but do not increment the tally for an object.
	 * 
	 * @param tallyObject
	 *            The object to get the current count for.
	 */
	public long getValue(final T tallyObject) {
		final Long value = this.map.get(tallyObject);
		if (value == null) {
			return 0;
		}
		return value.longValue();
	}

	/**
	 * Tally an object with a specific increment. This works best with enums.
	 * 
	 * @param tallyObject
	 *            The object to tally by.
	 * @param increment
	 *            The number to increment the tally by.
	 */
	public long tally(final T tallyObject, final long increment) {
		final Long oldValue = this.map.get(tallyObject);
		if (oldValue == null) {
			this.map.putIfAbsent(tallyObject, Long.valueOf(increment));
		} else {
			final Long newValue = Long.valueOf(oldValue.intValue() + increment);
			this.map.replace(tallyObject, oldValue, newValue);
		}
		return this.map.get(tallyObject).longValue();
	}

	/**
	 * Returns a simple report of keys/values up to {number} lines long.
	 *
	 * @param number
	 * 		The maximum number of lines to return.
	 * @return A multi-line string with keys and current counts.
	 */
	public String getReport(int number) {
		StringBuilder builder = new StringBuilder();
		List<Entry<T, Long>> top = new ArrayList<>(this.map.entrySet());
		Collections.sort(top, new EntryValueComparator<T, Long>());
		if (top.size() > number) {
			top = top.subList(0, number);
		}
		for (final Entry<T, Long> entry : top) {
			if (builder.length() > 0) {
				builder.append("\n");
			}
			builder.append(entry.getKey() + ": " + entry.getValue());
		}
		return builder.toString();
	}

}
