/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.lang;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ajah.util.AjahUtils;

/**
 * Convenience methods for dealing with Streams.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class StreamUtils {

	/**
	 * Converts an {@link InputStream} into a byte array.
	 * 
	 * @param inputStream
	 *            The stream to convert, required.
	 * @return byte array with the contents of the stream.
	 * @throws IOException
	 */
	public static byte[] toByteArray(final InputStream inputStream) throws IOException {
		AjahUtils.requireParam(inputStream, "inputStream");
		final byte[] buffer = new byte[1024];
		int read;
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((read = inputStream.read(buffer)) != -1) {
			output.write(buffer, 0, read);
		}
		return output.toByteArray();
	}

	/**
	 * Converts an {@link InputStream} into a string.
	 * 
	 * @param inputStream
	 *            The stream to convert, required.
	 * @return byte array with the contents of the stream.
	 * @throws IOException
	 */
	public static String toString(final InputStream inputStream) throws IOException {
		AjahUtils.requireParam(inputStream, "inputStream");
		final byte[] buffer = new byte[1024];
		int read;
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((read = inputStream.read(buffer)) != -1) {
			output.write(buffer, 0, read);
		}
		return new String(output.toByteArray(), "UTF-8");
	}

}
