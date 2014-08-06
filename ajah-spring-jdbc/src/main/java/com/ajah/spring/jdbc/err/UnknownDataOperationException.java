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
package com.ajah.spring.jdbc.err;

/**
 * This is thrown as a last resort, if no better matching exception could be
 * found.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class UnknownDataOperationException extends DataOperationException {

	/**
	 * Thrown when a message and cause is available.
	 * 
	 * @param message
	 *            The message for this exception.
	 * @param cause
	 *            The wrapped cause of this exception.
	 * @see DataOperationException#DataOperationException(Throwable)
	 */
	public UnknownDataOperationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * @see DataOperationException#DataOperationException(Throwable)
	 */
	public UnknownDataOperationException(final Throwable cause) {
		super(cause);
	}

	/**
	 * @see DataOperationException#DataOperationException(String)
	 */
	public UnknownDataOperationException(String message) {
		super(message);
	}

}
