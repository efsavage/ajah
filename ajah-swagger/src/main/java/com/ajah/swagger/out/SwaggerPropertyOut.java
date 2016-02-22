package com.ajah.swagger.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SwaggerPropertyOut {

	public String type;
	public String format;
	public String description;
	@JsonProperty("$ref")
	public String ref;
	public Boolean uniqueItems;
	public SwaggerItemsOut items;

}
