package com.ajah.swagger.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SwaggerPathOut {

	public SwaggerPathMethodOut post;
	public SwaggerPathMethodOut get;
	
}
