package com.ajah.swagger.api.relay;

import java.util.ArrayList;
import java.util.List;

import com.ajah.rest.api.model.AjahApiConvert;
import com.ajah.swagger.api.SwaggerApi;
import com.ajah.swagger.api.SwaggerDefinition;
import com.ajah.swagger.api.SwaggerDefinitionRelay;
import com.ajah.swagger.api.SwaggerOperation;
import com.ajah.swagger.api.SwaggerOperationRelay;
import com.ajah.swagger.api.SwaggerParameter;
import com.ajah.swagger.api.SwaggerParameterRelay;
import com.ajah.swagger.api.SwaggerProperty;
import com.ajah.swagger.api.SwaggerPropertyRelay;
import com.ajah.swagger.api.SwaggerResponse;
import com.ajah.swagger.api.SwaggerResponseRelay;
import com.ajah.util.CollectionUtils;

public class SwaggerRelayConvert {

	public static List<SwaggerApiRelay> convertSwaggerApis(List<SwaggerApi> swaggerApis) {
		if (CollectionUtils.isEmpty(swaggerApis)) {
			return null;
		}
		final List<SwaggerApiRelay> relays = new ArrayList<>(swaggerApis.size());
		for (final SwaggerApi swaggerApi : swaggerApis) {
			relays.add(convert(swaggerApi));
		}
		return relays;
	}

	private static SwaggerApiRelay convert(SwaggerApi swaggerApi) {
		SwaggerApiRelay relay = new SwaggerApiRelay();
		relay.id = swaggerApi.getId().toString();
		relay.name = swaggerApi.getName();
		relay.version = swaggerApi.getVersion();
		relay.host = swaggerApi.getHost();
		relay.basePath = swaggerApi.getBasePath();
		relay.bucket = swaggerApi.getBucket();
		relay.docRoot = swaggerApi.getDocRoot();
		relay.status = AjahApiConvert.convert(swaggerApi.getStatus());
		relay.type = AjahApiConvert.convert(swaggerApi.getType());
		relay.created = swaggerApi.getCreated();
		relay.http = swaggerApi.isHttp();
		relay.https = swaggerApi.isHttps();
		relay.json = swaggerApi.isJson();
		relay.xml = swaggerApi.isXml();
		return relay;
	}

	public static List<SwaggerDefinitionRelay> convertSwaggerDefinitions(List<SwaggerDefinition> swaggerDefinitions) {
		if (CollectionUtils.isEmpty(swaggerDefinitions)) {
			return null;
		}
		final List<SwaggerDefinitionRelay> relays = new ArrayList<>(swaggerDefinitions.size());
		for (final SwaggerDefinition swaggerDefinition : swaggerDefinitions) {
			relays.add(convert(swaggerDefinition));
		}
		return relays;
	}

	private static SwaggerDefinitionRelay convert(SwaggerDefinition swaggerDefinition) {
		if (swaggerDefinition == null) {
			return null;
		}
		SwaggerDefinitionRelay relay = new SwaggerDefinitionRelay();
		relay.id = swaggerDefinition.getId().toString();
		relay.swaggerApiId = swaggerDefinition.getSwaggerApiId().toString();
		relay.name = swaggerDefinition.getName();
		relay.status = AjahApiConvert.convert(swaggerDefinition.getStatus());
		relay.type = AjahApiConvert.convert(swaggerDefinition.getType());
		relay.created = swaggerDefinition.getCreated();
		return relay;
	}

	public static List<SwaggerOperationRelay> convertSwaggerOperations(List<SwaggerOperation> swaggerOperations) {
		if (CollectionUtils.isEmpty(swaggerOperations)) {
			return null;
		}
		final List<SwaggerOperationRelay> relays = new ArrayList<>(swaggerOperations.size());
		for (final SwaggerOperation swaggerOperation : swaggerOperations) {
			relays.add(convert(swaggerOperation));
		}
		return relays;
	}

	private static SwaggerOperationRelay convert(SwaggerOperation swaggerOperation) {
		SwaggerOperationRelay relay = new SwaggerOperationRelay();
		relay.id = swaggerOperation.getId().toString();
		relay.swaggerApiId = swaggerOperation.getSwaggerApiId().toString();
		relay.name = swaggerOperation.getName();
		relay.path = swaggerOperation.getPath();
		relay.summary = swaggerOperation.getSummary();
		relay.consumes = swaggerOperation.getConsumes();
		relay.tags = swaggerOperation.getTagArray();
		relay.operationId = swaggerOperation.getOperationId();
		relay.description = swaggerOperation.getDescription();
		relay.method = AjahApiConvert.convert(swaggerOperation.getMethod());
		relay.status = AjahApiConvert.convert(swaggerOperation.getStatus());
		relay.type = AjahApiConvert.convert(swaggerOperation.getType());
		relay.created = swaggerOperation.getCreated();
		return relay;
	}

	public static List<SwaggerPropertyRelay> convertSwaggerProperties(List<SwaggerProperty> swaggerProperties) {
		if (CollectionUtils.isEmpty(swaggerProperties)) {
			return null;
		}
		final List<SwaggerPropertyRelay> relays = new ArrayList<>(swaggerProperties.size());
		for (final SwaggerProperty swaggerProperty : swaggerProperties) {
			relays.add(convert(swaggerProperty));
		}
		return relays;
	}

	private static SwaggerPropertyRelay convert(SwaggerProperty swaggerProperty) {
		SwaggerPropertyRelay relay = new SwaggerPropertyRelay();
		relay.id = swaggerProperty.getId().toString();
		relay.swaggerDefinition = convert(swaggerProperty.getSwaggerDefinition());
		relay.name = swaggerProperty.getName();
		relay.format = swaggerProperty.getFormat();
		relay.required = swaggerProperty.isRequired();
		relay.status = AjahApiConvert.convert(swaggerProperty.getStatus());
		relay.type = AjahApiConvert.convert(swaggerProperty.getType());
		relay.created = swaggerProperty.getCreated();
		return relay;
	}

	public static List<SwaggerResponseRelay> convertSwaggerResponses(List<SwaggerResponse> swaggerResponses) {
		if (CollectionUtils.isEmpty(swaggerResponses)) {
			return null;
		}
		final List<SwaggerResponseRelay> relays = new ArrayList<>(swaggerResponses.size());
		for (final SwaggerResponse swaggerResponse : swaggerResponses) {
			relays.add(convert(swaggerResponse));
		}
		return relays;
	}

	private static SwaggerResponseRelay convert(SwaggerResponse swaggerResponse) {
		SwaggerResponseRelay relay = new SwaggerResponseRelay();
		relay.id = swaggerResponse.getId().toString();
		relay.swaggerOperationId = swaggerResponse.getSwaggerOperationId().toString();
		relay.swaggerDefinition = convert(swaggerResponse.getSwaggerDefinition());
		relay.name = swaggerResponse.getName();
		relay.description = swaggerResponse.getDescription();
		relay.code = swaggerResponse.getCode();
		relay.status = AjahApiConvert.convert(swaggerResponse.getStatus());
		relay.type = AjahApiConvert.convert(swaggerResponse.getType());
		relay.created = swaggerResponse.getCreated();
		return relay;
	}

	public static List<SwaggerParameterRelay> convertSwaggerParameters(List<SwaggerParameter> swaggerParameters) {
		if (CollectionUtils.isEmpty(swaggerParameters)) {
			return null;
		}
		final List<SwaggerParameterRelay> relays = new ArrayList<>(swaggerParameters.size());
		for (final SwaggerParameter swaggerParameter : swaggerParameters) {
			relays.add(convert(swaggerParameter));
		}
		return relays;
	}

	private static SwaggerParameterRelay convert(SwaggerParameter swaggerParameter) {
		SwaggerParameterRelay relay = new SwaggerParameterRelay();
		relay.id = swaggerParameter.getId().toString();
		relay.swaggerOperationId = swaggerParameter.getSwaggerOperationId().toString();
		relay.swaggerDefinition = convert(swaggerParameter.getSwaggerDefinition());
		relay.name = swaggerParameter.getName();
		relay.in = swaggerParameter.getIn();
		relay.required = swaggerParameter.isRequired();
		relay.status = AjahApiConvert.convert(swaggerParameter.getStatus());
		relay.type = AjahApiConvert.convert(swaggerParameter.getType());
		relay.created = swaggerParameter.getCreated();
		return relay;
	}

}
