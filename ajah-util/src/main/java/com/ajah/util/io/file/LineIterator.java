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

import java.util.Iterator;

/**
 * Wraps a {@link BufferedReaderIterator} so that it will return a {@link Line}
 * instead of a {@link String}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class LineIterator implements Iterator<Line> {

	private final BufferedReaderIterator iterator;

	/**
	 * @param lineReader
	 */
	public LineIterator(final LineReader lineReader) {
		this.iterator = new BufferedReaderIterator(lineReader);
	}

	/**
	 * @see BufferedReaderIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Line next() {
		return new Line(this.iterator.next());
	}

	/**
	 * @see BufferedReaderIterator#remove()
	 */
	@Override
	public void remove() {
		this.iterator.remove();
	}

}
