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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class Report {

	/**
	 * 40 equals signs
	 */
	public static final String EQUAL_40 = "========================================";

	/**
	 * 35 equals signs
	 */
	public static final String HYPEN35 = "-----------------------------------";

	public static char[] tabs(final int depth) {
		final char[] tabs = new char[depth];
		Arrays.fill(tabs, '\t');
		return tabs;
	}

	private final List<PrintWriter> writers = new ArrayList<>();

	private Logger log;

	public void add(final File file) throws FileNotFoundException {
		this.writers.add(new PrintWriter(file));
	}

	public void add(final Logger _log) {
		this.log = _log;
	}

	public void add(final OutputStream out) {
		this.writers.add(new PrintWriter(out));
	}

	public void add(final PrintWriter writer) {
		this.writers.add(writer);
	}

	public void close() {
		for (final PrintWriter out : this.writers) {
			out.close();
		}
	}

	public void print(final String line) {
		this.log.info(line);
		for (final PrintWriter out : this.writers) {
			out.print(line);
		}
	}

	public void println(final int depth, final String line) {
		if (this.writers.size() < 1) {
			throw new IllegalArgumentException();
		}
		this.log.info(line);
		for (final PrintWriter out : this.writers) {
			if (depth > 0) {
				out.print(tabs(depth));
			}
			out.println(line);
		}
	}

	public void println(final String line) {
		println(0, line);
	}

	public void rule() {
		println(EQUAL_40);
	}

}
