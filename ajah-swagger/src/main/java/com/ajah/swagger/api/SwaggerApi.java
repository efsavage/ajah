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

import javax.persistence.GeneratedValue;

import lombok.Data;

import com.ajah.util.Identifiable;

@Data
public class SwaggerApi implements Identifiable<SwaggerApiId> {

	@GeneratedValue
	private SwaggerApiId id;
	private String name;
	private String description;
	private String version;
	private String host;
	private String basePath;
	private boolean http;
	private boolean https;
	private boolean xml;
	private boolean json;
	private SwaggerApiStatus status;
	private SwaggerApiType type;
	private Date created;

	/**
	 * When publishing to s3.
	 */
	private String bucket;
	/**
	 * When publishing, this is the path to swagger.json.
	 */
	private String docRoot;

}
