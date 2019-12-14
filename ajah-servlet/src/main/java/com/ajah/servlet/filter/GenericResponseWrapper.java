package com.ajah.servlet.filter;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import lombok.extern.java.Log;

/**
 * Implementation of {@link HttpServletResponseWrapper} that holds the data for
 * manipulation typically by a {@link Filter}.
 *
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 * href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class GenericResponseWrapper extends HttpServletResponseWrapper {

	private final ByteArrayOutputStream output;
	private int contentLength;
	private String contentType;

	/**
	 * Public constructor, calls
	 * {@link HttpServletResponseWrapper#HttpServletResponseWrapper(HttpServletResponse)}
	 * and creates alternate output stream.
	 *
	 * @param response
	 */
	public GenericResponseWrapper(final HttpServletResponse response) {
		super(response);
		this.output = new ByteArrayOutputStream();
		this.contentType = response.getContentType();
	}

	/**
	 * Returns the content length.
	 *
	 * @return The content length.
	 */
	public int getContentLength() {
		return this.contentLength;
	}

	/**
	 * Returns the content type.
	 *
	 * @see javax.servlet.ServletResponseWrapper#getContentType()
	 */
	@Override
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * Returns the captured output stream as a byte array.
	 *
	 * @return The captured output stream as a byte array.
	 */
	public byte[] getData() {
		return this.output.toByteArray();
	}

	/**
	 * Wraps the output in a {@link FilterServletOutputStream}.
	 *
	 * @see javax.servlet.ServletResponseWrapper#getOutputStream()
	 */
	@Override
	public ServletOutputStream getOutputStream() {
		return new FilterServletOutputStream(this.output);
	}

	/**
	 * Wraps {@link #getOutputStream()} in a {@link PrintWriter}.
	 *
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() {
		return new PrintWriter(getOutputStream(), true);
	}

	/**
	 * Sets the content length.
	 *
	 * @see javax.servlet.ServletResponseWrapper#setContentLength(int)
	 */
	@Override
	public void setContentLength(final int length) {
		this.contentLength = length;
		super.setContentLength(length);
	}

	/**
	 * Sets the content type.
	 *
	 * @see javax.servlet.ServletResponseWrapper#setContentType(java.lang.String)
	 */
	@Override
	public void setContentType(final String type) {
		log.fine("Setting content type to :" + type);
		this.contentType = type;
		super.setContentType(type);
	}

}
