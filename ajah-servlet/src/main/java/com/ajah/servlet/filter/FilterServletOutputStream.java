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

	public FilterServletOutputStream(final OutputStream output) {
		this.stream = new DataOutputStream(output);
	}

	/**
	 * Calls {@link DataOutputStream#write(byte[]))}.
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

}
