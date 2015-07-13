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
package com.ajah.swagger.api;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;

import lombok.Data;

import com.ajah.http.HttpMethod;
import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;

@Data
public class SwaggerOperation implements Identifiable<SwaggerOperationId> {

	@GeneratedValue
	private SwaggerOperationId id;
	private SwaggerApiId swaggerApiId;
	private String name;
	private String path;
	private HttpMethod method;

	private String summary;
	private String description;
	private String consumes;
	private String operationId;
	private String tags;

	private SwaggerOperationStatus status;
	private SwaggerOperationType type;
	private Date created;

	public List<String> getTagArray() {
		if (StringUtils.isBlank(tags)) {
			return null;
		}
		return StringUtils.cleanSplit(tags, " ");
	}

}
