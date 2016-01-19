package com.ajah.swagger.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SwaggerParameterOut {

	public String name;
	public String in;
	public String description;
	public SwaggerSchemaOut schema;
	public boolean required;
	public String type;

}
