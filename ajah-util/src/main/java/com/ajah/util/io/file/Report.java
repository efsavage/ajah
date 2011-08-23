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
package com.ajah.util.io.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
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

	private List<PrintWriter> writers = new ArrayList<PrintWriter>();

	private Logger log;

	public void println(String line) throws IOException {
		println(0, line);
	}

	public void print(String line) throws IOException {
		log.info(line);
		for (PrintWriter out : writers) {
			out.print(line);
		}
	}

	public void println(int depth, String line) throws IOException {
		if (this.writers.size() < 1) {
			throw new IllegalArgumentException();
		}
		log.info(line);
		for (PrintWriter out : writers) {
			if (depth > 0) {
				out.print(tabs(depth));
			}
			out.println(line);
		}
	}

	// private void print(char[] chars) {
	// log.info(new String(chars));
	// for (PrintWriter writer : writers) {
	// writer.write(chars);
	// }
	// }

	public void add(PrintWriter writer) {
		this.writers.add(writer);
	}

	public void add(File file) throws FileNotFoundException {
		this.writers.add(new PrintWriter(file));
	}

	public void rule() throws IOException {
		println(EQUAL_40);
	}

	public static char[] tabs(int depth) {
		char[] tabs = new char[depth];
		Arrays.fill(tabs, '\t');
		return tabs;
	}

	public void add(OutputStream out) {
		this.writers.add(new PrintWriter(out));
	}

	public void add(Logger log) {
		this.log = log;
	}

	public void close() {
		for (PrintWriter out : writers) {
			out.close();
		}
	}

}