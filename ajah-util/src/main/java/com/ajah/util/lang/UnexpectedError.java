/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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

/**
 * This is a wrapper for other exceptions to denote that they were not expected
 * (such as in a "catch (Throwable t)" and a handler should be implemented.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class UnexpectedError extends Exception {

	/**
	 * Private constructor, as this error should never be constructed on it's
	 * own.
	 */
	@SuppressWarnings("unused")
	private UnexpectedError() {
		// This error should never be constructed on it's own.
	}

	/**
	 * Wraps another error.
	 * 
	 * @param t
	 *            The unexpected error to be wrapped.
	 */
	public UnexpectedError(final Throwable t) {
		super(t);
	}
}
