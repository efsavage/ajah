package com.ajah.util.log;

import java.io.PrintStream;

import com.ajah.util.data.DataSizeUnit;

/**
 * Extends Tally with the ability to track bytes. Useful for tracking
 * data/files.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 * @param <T>
 */
public class ByteTally<T> extends Tally<T> {

	/**
	 * Public constructor.
	 * 
	 * @see Tally#Tally(PrintStream)
	 * @param out
	 */
	public ByteTally(PrintStream out) {
		super(out);
	}

	@Override
	public void report() {
		this.out.println(Report.HYPEN35);
		for (final T t : this.map.keySet()) {
			System.out.println(t.toString() + ": " + DataSizeUnit.format(this.map.get(t).longValue()));
		}
	}

}
