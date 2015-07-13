package com.ajah.swagger.out;

import java.util.List;
import java.util.TreeMap;

import com.ajah.http.HttpMethod;
import com.ajah.swagger.api.SwaggerApi;
import com.ajah.swagger.api.SwaggerDefinition;
import com.ajah.swagger.api.SwaggerOperation;

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
			SwaggerDefinitionOut defOut = new SwaggerDefinitionOut();
			out.definitions.put(definition.getName(), defOut);
		}

		return out;
	}

	private SwaggerPathMethodOut createPath(SwaggerOperation operation) {
		SwaggerPathMethodOut path = new SwaggerPathMethodOut ();
		path.description = operation.getDescription();
		path.consumes = new String[] {operation.getConsumes()};
		path.operationId = operation.getOperationId();
		path.summary = operation.getSummary();
		path.tags = operation.getTagArray();
		return path;
	}
}
