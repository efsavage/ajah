/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.ajah.util.text.Strings;

/**
 * Writes reports to the configured channels/writers.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class ReportWriter implements AutoCloseable {

	/**
	 * Adds a number of tabs to nest things properly.
	 * 
	 * @param depth
	 *            The number of levels deep we are.
	 * @return Array of tab characters.
	 */
	public static char[] tabs(final int depth) {
		final char[] tabs = new char[depth];
		Arrays.fill(tabs, '\t');
		return tabs;
	}

	private final List<PrintWriter> writers = new ArrayList<>();

	private Logger log;

	/**
	 * Add a file, automatically wrapped by a {@link PrintWriter}.
	 * 
	 * @param file
	 *            The file to add/wrap.
	 * @throws FileNotFoundException
	 *             If the file could not be found.
	 */
	public void add(final File file) throws FileNotFoundException {
		this.writers.add(new PrintWriter(file));
	}

	/**
	 * Adds an OutputStream to write the report to, wrapped by a
	 * {@link PrintWriter}.
	 * 
	 * @param out
	 *            A stream to write the report to.
	 */
	public void add(final OutputStream out) {
		this.writers.add(new PrintWriter(out));
	}

	/**
	 * Adds an PrintWriter to write the report to.
	 * 
	 * @param writer
	 *            A stream to write the report to.
	 */
	public void add(final PrintWriter writer) {
		this.writers.add(writer);
	}

	/**
	 * Closes all of the writers.
	 */
	@Override
	public void close() {
		for (final PrintWriter out : this.writers) {
			out.close();
		}
	}

	/**
	 * Prints a string to the writers.
	 * 
	 * @param string
	 *            The string to print to the writers.
	 */
	public void print(final String string) {
		this.log.info(string);
		for (final PrintWriter out : this.writers) {
			out.print(string);
		}
	}

	/**
	 * Prints a line of text with the appropriate leading tab characters.
	 * 
	 * @param depth
	 *            The depth of the line in the hierarchy.
	 * @param line
	 *            The line to print.
	 */
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

	/**
	 * Prints a line at zero depth.
	 * 
	 * @see #println(int, String)
	 * @param line
	 *            The line to print.
	 */
	public void println(final String line) {
		println(0, line);
	}

	/**
	 * Writes a {@link Strings#EQUAL_40} line.
	 */
	public void rule() {
		println(Strings.EQUAL_40);
	}

	/**
	 * Sets a logger to receive the report.
	 * 
	 * @param _log
	 *            The logger to log to.
	 */
	public void set(final Logger _log) {
		this.log = _log;
	}

}
