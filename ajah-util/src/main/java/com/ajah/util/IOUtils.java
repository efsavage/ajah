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
package com.ajah.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;

import lombok.extern.java.Log;

/**
 * I/O Utilities.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class IOUtils {

	/**
	 * Closes a {@link Closeable} object, checking for null and ignoring
	 * (logging as warning) {@link IOException}s.
	 * 
	 * @param closeable
	 *            Closeable object, may be null.
	 */
	public static void safeClose(final AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (final Exception e) {
				log.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

}
