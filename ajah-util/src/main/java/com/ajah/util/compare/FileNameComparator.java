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
package com.ajah.util.compare;

import java.io.File;
import java.util.Comparator;

/**
 * Compares two files by file size, with the smaller file first.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class FileNameComparator implements Comparator<File> {

	private static final FileNameComparator instance = new FileNameComparator();

	/**
	 * Returns singleton instance of this comparator.
	 * 
	 * @return Singleton instance of this comparator.
	 */
	public static Comparator<? super File> getInstance() {
		return instance;
	}

	/**
	 * @see CompareUtils#compare(long, long)
	 * @throws IllegalArgumentException
	 *             If Both values are null, as this means it cannot be passed
	 *             along to a comparator even though they are "equal".
	 */
	@Override
	public int compare(final File first, final File second) {
		final int retVal = CompareUtils.compareNulls(first, second);
		if (retVal != 0) {
			return retVal;
		}
		return CompareUtils.compare(first.getName(), second.getName());
	}

}
