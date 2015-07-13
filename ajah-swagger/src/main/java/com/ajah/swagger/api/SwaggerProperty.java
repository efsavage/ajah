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

import lombok.Data;

import com.ajah.util.Identifiable;

@Data
public class SwaggerProperty implements Identifiable<SwaggerPropertyId> {

	private SwaggerPropertyId id;
	private SwaggerDefinitionId swaggerDefinitionId;
	private String name;
	private String format;
	private String description;
	private boolean required;
	private SwaggerPropertyStatus status;
	private SwaggerPropertyType type;
	private Date created;

}
