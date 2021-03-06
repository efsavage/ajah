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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Data
public class SwaggerModel {

	private String id;
	private Map<String, SwaggerModelProperty> properties = new HashMap<>();

	public SwaggerModel(final String id) {
		this.id = id;
	}

	public List<String> getRequired() {
		final List<String> requiredProps = new ArrayList<>();
		for (final String key : this.properties.keySet()) {
			if (this.properties.get(key).required) {
				requiredProps.add(key);
			}
		}
		return requiredProps;
	}

}
