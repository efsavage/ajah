/*
 *  Copyright 2013-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.rest.api.model;

import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Base class for errors, with constants for standard errors that happen in most
 * applications.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
public class ApiResponseError {

	/*
	 * Usage/client errors
	 */
	/**
	 * A required parameter is missing.
	 */
	public static final int MISSING_PARAM = 1000;
	/**
	 * Permission to the URL or resource is not allowed for the current user.
	 */
	public static final int PERMISSION_DENIED = 1001;
	/**
	 * Generic bad/malformed request error.
	 */
	public static final int BAD_REQUEST = 1002;
	/**
	 * The request contained malformed JSON.
	 */
	public static final int BAD_REQUEST_JSON = 1003;
	/**
	 * No user is logged in.
	 */
	public static final int AUTHENTICATION_REQUIRED = 1004;
	/**
	 * The authentication attempt failed.
	 */
	public static final int AUTHENTICATION_FAILED = 1005;
	/**
	 * An invalid email format.
	 */
	public static final int INVALID_EMAIL_FORMAT = 1006;
	/**
	 * General invalid parameter error.
	 */
	public static final int INVALID_PARAMETER = 1007;
	/**
	 * Resource is missing (404).
	 */
	public static final int RESOURCE_MISSING = 1008;
	/**
	 * User is inactive.
	 */
	public static final int INACTIVE_USER = 1009;
	/**
	 * User is blocked (temporarily).
	 */
	public static final int BLOCKED_USER = 1010;
	/**
	 * User is disabled (permanently).
	 */
	public static final int DISABLED_USER = 1011;

	/*
	 * Data errors
	 */
	/**
	 * The account did not have enough funds to complete the requested
	 * transaction.
	 */
	public static final int INSUFFICIENT_FUNDS = 2000;
	/**
	 * The requested username is already in use.
	 */
	public static final int USERNAME_EXISTS = 2200;
	/**
	 * The requested email is already in use.
	 */
	public static final int EMAIL_EXISTS = 2201;
	/**
	 * The user is not old enough to perform the operation.
	 */
	public static final int AGE_REQUIREMENT_NOT_MET = 2202;

	/*
	 * Internal errors
	 */
	/**
	 * Unknown error that wasn't handled more specifically.
	 */
	public static final int UNEXPECTED_ERROR = 8000;
	/**
	 * Error fetching or saving data.
	 */
	public static final int DATA_OPERATION_ERROR = 8001;

	/**
	 * Numeric code for reliable matching.
	 */
	public int code;

	/**
	 * Descriptive message.
	 */
	public String message;

}
