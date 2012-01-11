package com.ajah.util.log;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

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
	public Tally(PrintStream out) {
		this.out = out;
	}

	/**
	 * Tally an object with an increment of 1. This works best with enums.
	 * 
	 * @param tallyObject
	 *            The object to tally by.
	 */
	public void tally(final T tallyObject) {
		tally(tallyObject, 1);
	}

	/**
	 * Tally an object with a specific increment. This works best with enums.
	 * 
	 * @param tallyObject
	 *            The object to tally by.
	 * @param increment
	 *            The number to increment the tally by.
	 */
	public void tally(final T tallyObject, final long increment) {
		final Long oldValue = this.map.get(tallyObject);
		if (oldValue == null) {
			this.map.putIfAbsent(tallyObject, Long.valueOf(increment));
		} else {
			final Long newValue = Long.valueOf(oldValue.intValue() + increment);
			this.map.replace(tallyObject, oldValue, newValue);
		}
	}

	/**
	 * Increment the error count.
	 */
	public void error() {
		this.errors.incrementAndGet();
	}

	/**
	 * Increment the success count.
	 */
	public void success() {
		this.successes.incrementAndGet();
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
	public void report(int interval) {
		if (getTotal() > 0 && (getTotal() % interval) == 0) {
			report();
		}
	}

	/**
	 * Write a report with totals to the configured output.
	 */
	public void report() {
		this.out.println(Report.HYPEN35);
		this.out.println("Success/Error/Total: " + getSuccesses() + "/" + getErrors() + "/" + getTotal() + " - " + NumberFormat.getPercentInstance().format(1.0 * getSuccesses() / getTotal()));
		for (final T t : this.map.keySet()) {
			this.out.println(t.toString() + ": " + this.map.get(t));
		}
	}

}
