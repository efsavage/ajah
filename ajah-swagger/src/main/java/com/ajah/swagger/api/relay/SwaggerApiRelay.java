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
package com.ajah.swagger.api.relay;

import java.util.Date;

import com.ajah.rest.api.model.relay.IdentifiableEnumRelay;
import com.ajah.swagger.api.SwaggerApi;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Simplified version of a {@link SwaggerApi} for easier
 * serialization/deserialization.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@JsonInclude(Include.NON_NULL)
public class SwaggerApiRelay {

	public String id;
	public String name;
	public String description;
	public String version;
	public String host;
	public String basePath;
	public String bucket;
	public String docRoot;
	public boolean http;
	public boolean https;
	public boolean xml;
	public boolean json;
	public IdentifiableEnumRelay<String> status;
	public IdentifiableEnumRelay<String> type;
	public Date created;

}
