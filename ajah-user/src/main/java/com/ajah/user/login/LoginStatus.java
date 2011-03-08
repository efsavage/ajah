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
package com.ajah.user.login;

/**
 * All logins will result in one of these four options. Application-specific
 * reasons can go into the code and reason fields.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum LoginStatus {

	/** Authentication was indeterminate and login was incomplete */
	ABORT(4, false, false, false),
	/**
	 * Authentication was indeterminate and login was probably incomplete due to
	 * an error
	 */
	ERROR(3, false, false, true),
	/** Authentication was invalid and login was complete */
	FAIL(2, false, true, false),
	/** Authentication was valid and login was complete */
	SUCCESS(1, true, true, false);

	private final boolean complete;
	private final boolean error;
	private final int id;
	private final boolean succeeded;

	private LoginStatus(int id, boolean succeeded, boolean complete, boolean error) {
		this.id = id;
		this.succeeded = succeeded;
		this.complete = complete;
		this.error = error;
	}

	/**
	 * Unique ID of this status.
	 * 
	 * @return Unique ID of this status.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Did the login complete (even if it was unsuccessful)?
	 * 
	 * @return true if the login completed, even if it was unsuccessful
	 */
	public boolean isComplete() {
		return this.complete;
	}

	/**
	 * Did the login result in an error?
	 * 
	 * @return true if the login resulted in an error
	 */
	public boolean isError() {
		return this.error;
	}

	/**
	 * Did the login succeed?
	 * 
	 * @return true if the login succeeded
	 */
	public boolean isSucceeded() {
		return this.succeeded;
	}

}