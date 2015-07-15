package com.ajah.swagger.out;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.ajah.http.HttpMethod;
import com.ajah.swagger.api.SwaggerApi;
import com.ajah.swagger.api.SwaggerDefinition;
import com.ajah.swagger.api.SwaggerOperation;
import com.ajah.swagger.api.SwaggerProperty;
import com.ajah.swagger.api.SwaggerPropertyType;
import com.ajah.swagger.api.SwaggerResponse;
import com.ajah.swagger.api.SwaggerResponseType;

public class SwaggerWriter {

	public SwaggerOut write(SwaggerApi api, List<SwaggerOperation> operations, List<SwaggerDefinition> definitions) {
		SwaggerOut out = new SwaggerOut();
		out.swagger = "2.0";

		out.basePath = api.getBasePath();
		out.host = api.getHost();

		out.info = new SwaggerInfoOut();
		out.info.description = api.getDescription();
		out.info.title = api.getName();
		out.info.version = api.getVersion();

		if (api.isHttp()) {
			if (api.isHttps()) {
				out.schemes = new String[] { "http", "https" };
			} else {
				out.schemes = new String[] { "http" };
			}
		} else {
			if (api.isHttps()) {
				out.schemes = new String[] { "https" };
			} else {
				out.schemes = null;
			}
		}

		if (api.isJson()) {
			if (api.isXml()) {
				out.produces = new String[] { "application/xml", "application/json" };
			} else {
				out.produces = new String[] { "application/json" };
			}
		} else {
			if (api.isXml()) {
				out.produces = new String[] { "application/xml" };
			} else {
				out.produces = null;
			}
		}

		out.paths = new TreeMap<String, SwaggerPathOut>();
		for (SwaggerOperation operation : operations) {
			SwaggerPathOut path = out.paths.get(operation.getPath());
			if (path == null) {
				path = new SwaggerPathOut();
				out.paths.put(operation.getPath(), path);
			}
			if (operation.getMethod() == HttpMethod.POST) {
				path.get = createPath(operation);
			} else if (operation.getMethod() == HttpMethod.GET) {
				path.post = createPath(operation);
			}
		}

		out.definitions = new TreeMap<String, SwaggerDefinitionOut>();
		for (SwaggerDefinition definition : definitions) {
			out.definitions.put(definition.getName(), getDefOut(definition));
		}

		return out;
	}

	private SwaggerDefinitionOut getDefOut(SwaggerDefinition definition) {
		SwaggerDefinitionOut out = new SwaggerDefinitionOut();
		out.properties = new HashMap<>();
		for (SwaggerProperty property : definition.getSwaggerProperties()) {
			out.properties.put(property.getName(), getPropertyOut(property));
		}
		return out;
	}

	private SwaggerPropertyOut getPropertyOut(SwaggerProperty property) {
		SwaggerPropertyOut out = new SwaggerPropertyOut();
		if (property.getType() == SwaggerPropertyType.DEFINITION) {
		} else if (property.getType() == SwaggerPropertyType.LIST) {
			out.type = "array";
			out.items = new SwaggerItemsOut();
			out.items.ref = "#/definitions/" + property.getSwaggerDefinition().getName();
		} else if (property.getType() == SwaggerPropertyType.SET) {
			out.type = "array";
			out.uniqueItems = true;
			out.items = new SwaggerItemsOut();
			out.items.ref = "#/definitions/" + property.getSwaggerDefinition().getName();
		} else {
			out.type = property.getType().getCode();
		}
		out.description = property.getDescription();
		out.format = property.getFormat();
		return out;
	}

	private SwaggerPathMethodOut createPath(SwaggerOperation operation) {
		SwaggerPathMethodOut path = new SwaggerPathMethodOut();
		path.description = operation.getDescription();
		path.consumes = new String[] { operation.getConsumes() };
		path.produces = operation.getProduces() == null ? null : new String[] { operation.getProduces() };
		path.operationId = operation.getOperationId();
		path.summary = operation.getSummary();
		path.tags = operation.getTagArray();
		path.responses = new HashMap<>();
		for (SwaggerResponse response : operation.getResponses()) {
			path.responses.put(response.getCode(), getResponseOut(response));
		}
		return path;
	}

	private SwaggerResponseOut getResponseOut(SwaggerResponse response) {
		SwaggerResponseOut out = new SwaggerResponseOut();
		out.description = response.getDescription();
		out.schema = new SwaggerSchemaOut();
		if (response.getType() == SwaggerResponseType.DEFINITION) {
			if (response.getSwaggerDefinition() != null) {
				out.schema.ref = "#/definitions/" + response.getSwaggerDefinition().getName();
			}
		} else {
			out.schema.type = response.getType().getCode();
		}
		return out;
	}
}
