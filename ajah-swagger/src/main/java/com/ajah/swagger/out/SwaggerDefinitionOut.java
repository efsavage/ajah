package com.ajah.swagger.out;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SwaggerDefinitionOut {

	public Map<String, SwaggerPropertyOut> properties;
	public String[] required;
}
