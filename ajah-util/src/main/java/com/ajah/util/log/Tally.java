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

	public Tally(PrintStream out) {
		this.out = out;
	}

	public void tally(final T tallyObject) {
		tally(tallyObject, 1);
	}

	public void tally(final T tallyObject, final long increment) {
		final Long oldValue = this.map.get(tallyObject);
		if (oldValue == null) {
			this.map.putIfAbsent(tallyObject, Long.valueOf(increment));
		} else {
			final Long newValue = Long.valueOf(oldValue.intValue() + increment);
			this.map.replace(tallyObject, oldValue, newValue);
		}
	}

	public void error() {
		this.errors.incrementAndGet();
	}

	public void success() {
		this.successes.incrementAndGet();
	}

	public long getErrors() {
		return this.errors.get();
	}

	public long getSuccesses() {
		return this.successes.get();
	}

	public long getTotal() {
		return this.errors.get() + this.successes.get();
	}

	public void report() {
		this.out.println(Report.HYPEN35);
		this.out.println("Success/Error/Total: " + getSuccesses() + "/" + getErrors() + "/" + getTotal() + " - " + NumberFormat.getPercentInstance().format(1.0 * getSuccesses() / getTotal()));
		for (final T t : this.map.keySet()) {
			this.out.println(t.toString() + ": " + this.map.get(t));
		}
	}
}
