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
