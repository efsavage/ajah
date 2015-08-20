/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
 * Base class for alerts.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
public class ApiResponseAlert {

	/**
	 * Code for reliable matching/translating.
	 */
	public String code;

	/**
	 * Descriptive message in default language.
	 */
	public String message;

	/**
	 * Level of severity of the alert.
	 */
	public String level;

	/**
	 * Tags/context of the alert (like an area of the site ('checkout') or a
	 * particular entity ('game:123').
	 */
	public String[] tags;
}
