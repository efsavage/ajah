package com.ajah.swagger.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SwaggerSchemaOut {

	public String type;

	@JsonProperty(value = "$ref")
	public String ref;

}
