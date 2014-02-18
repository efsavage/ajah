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
package com.ajah.swagger;

import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class SwaggerParameter {

	private String name;
	private String type;
	private boolean allowMultiple = false;
	private String paramType = "query";
	private boolean required = false;
	private String description;
	private Integer minimum;
	private Integer maximum;
	private String defaultValue;

	@JsonProperty("enum")
	private List<String> enumValues;

	public SwaggerParameter(final String name, final String type, final String description) {
		this.name = name;
		this.type = type;
		this.description = description;
	}

}
