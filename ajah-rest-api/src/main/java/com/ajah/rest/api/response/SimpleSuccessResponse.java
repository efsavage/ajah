/*
 *  Copyright 2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.rest.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A response to send when we only need to acknowledge success or failure.
 *
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@JsonInclude(Include.NON_NULL)
public class SimpleSuccessResponse extends SimpleApiResponse {

	public final boolean success;

	/**
	 * Public constructor where success is true.
	 */
	public SimpleSuccessResponse() {
		this.success = true;
	}

	/**
	 * Public constructor
	 *
	 * @param success
	 *            The success state.
	 */
	public SimpleSuccessResponse(final boolean success) {
		this.success = success;
	}

}
