/*
 *  Copyright 2013-2015 Eric F. Savage, code@efsavage.com
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Base API response class. Includes random response ID, errors and alerts.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@JsonInclude(Include.NON_NULL)
public class SimpleApiResponse implements ApiResponse {

	/**
	 * Unique ID for each response.
	 */
	public String id = UUID.randomUUID().toString();

	/**
	 * The errors that occurred during this request.
	 */
	public List<ApiResponseError> errors;

	/**
	 * The errors that occurred during this request.
	 */
	public List<ApiResponseAlert> alerts;

	/**
	 * Adds an error to this response.
	 * 
	 * @param code
	 *            The numeric code of the error.
	 * @param message
	 *            The message explaining the error.
	 */
	public void addError(final int code, final String message) {
		if (this.errors == null) {
			this.errors = new ArrayList<>();
		}
		this.errors.add(new ApiResponseError(code, message));
	}

	/**
	 * Adds an error to this response.
	 * 
	 *            The numeric code of the error.
	 * @param message
	 *            The message explaining the error.
	 */
	public void addAlert(final String code, final String message, ApiResponseAlertLevel level, final String[] tags) {
		if (this.alerts == null) {
			this.alerts = new ArrayList<>();
		}
		this.alerts.add(new ApiResponseAlert(code, message, level.getCode(), tags));
	}

}
