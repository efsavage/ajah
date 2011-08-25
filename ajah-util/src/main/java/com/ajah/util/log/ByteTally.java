package com.ajah.util.log;

import java.io.PrintStream;

import com.ajah.util.data.DataSizeUnit;

public class ByteTally<T> extends Tally<T> {

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
