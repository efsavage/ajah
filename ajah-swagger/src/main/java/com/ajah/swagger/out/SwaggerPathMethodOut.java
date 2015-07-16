package com.ajah.swagger.out;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SwaggerPathMethodOut {

	public String summary;
	public String description;
	public String[] consumes;
	public String operationId;
	public List<SwaggerParameterOut> parameters;
	public List<String> tags;
	public Map<String,SwaggerResponseOut> responses;
	public String[] produces;
}
