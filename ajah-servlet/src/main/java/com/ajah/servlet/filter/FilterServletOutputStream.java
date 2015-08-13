/*
 *  Copyright 2014-2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.servlet.filter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * This is a simple implementation of the abstract {@link ServletOutputStream}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class FilterServletOutputStream extends ServletOutputStream {

	private final DataOutputStream stream;

	/**
	 * Public constructor with wrapped/captured output stream.
	 * 
	 * @param output
	 */
	public FilterServletOutputStream(final OutputStream output) {
		this.stream = new DataOutputStream(output);
	}

	/**
	 * Calls {@link DataOutputStream#write(byte[])}.
	 * 
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(final byte[] b) throws IOException {
		this.stream.write(b);
	}

	/**
	 * Calls {@link DataOutputStream#write(byte[], int, int)}.
	 * 
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(final byte[] b, final int off, final int len) throws IOException {
		this.stream.write(b, off, len);
	}

	/**
	 * Calls {@link DataOutputStream#write(int)}.
	 * 
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(final int b) throws IOException {
		this.stream.write(b);
	}

	/**
	 * Always returns true.
	 * 
	 * @return Always returns true.
	 */
//	@Override
	public boolean isReady() {
		return true;
	}

	/**
	 * Does nothing, exists only for compatibility.
	 * 
	 * @param writeListener
	 *            Ignored.
	 */
//	@Override
//	public void setWriteListener(WriteListener writeListener) {
		// Empty
//	}

}
