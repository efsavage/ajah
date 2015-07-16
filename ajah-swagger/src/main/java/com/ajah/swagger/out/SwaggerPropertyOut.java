package com.ajah.swagger.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SwaggerPropertyOut {

	public String type;
	public String format;
	public String description;
	public Boolean uniqueItems;
	public SwaggerItemsOut items;

}
