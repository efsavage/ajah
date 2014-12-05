/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.ajah.swagger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ajah.lang.ListMap;
import com.ajah.lang.MapMap;
import com.ajah.util.ToStringable;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Scans a class for Swagger annotations.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Service
@Data
@Log
@AllArgsConstructor
public class SwaggerScanner {

	/**
	 * Adds errors based on thrown exceptions.
	 * 
	 * @param api
	 *            The API we're documenting.
	 * @param exceptionTypes
	 *            The exceptions that are declared.
	 */
	private static void addErrors(final SwaggerApi api, final Class<?>[] exceptionTypes) {
		if (exceptionTypes == null || exceptionTypes.length == 0) {
			return;
		}
		for (final Class<?> exceptionType : exceptionTypes) {
			log.fine("Adding exception: " + exceptionType.getName());
			api.description += "<br />Throws " + exceptionType.getSimpleName();
		}

		// final SwaggerModel typeModel = new
		// SwaggerModel(type.getSimpleName());
		// if (this.models.get(api.path, typeModel.getId()) != null) {
		// return;
		// }
		// this.models.put(api.path, typeModel.getId(), typeModel);
		// log.fine("Storing models under: " + api.path);
		// final Field[] fields = type.getFields();
		// log.fine(fields.length + " fields");
		// for (final Field field : fields) {
		// log.fine("Field: " + field.getName());
		// log.fine("\tAccessible: " + field.isAccessible());
		// log.fine("\tSynthetic: " + field.isSynthetic());
		// log.fine("\tModifiers: " + field.getModifiers());
		// log.fine("\tPublic: " + Modifier.isPublic(field.getModifiers()));
		// if (Modifier.isPublic(field.getModifiers())) {
		// final SwaggerModelProperty fieldProp = new
		// SwaggerModelProperty(convert(field.getType()), false);
		// // lookupResponseProp.items = new SwaggerItemsRef("League");
		// typeModel.getProperties().put(field.getName(), fieldProp);
		// // api.models.put(lookupResponseModel.getId(),
		// // lookupResponseModel);
		// if (!isBasic(field.getType())) {
		// addModels(api, field.getType());
		// }
		// }
		// }
	}

	private static String convert(final Class<?> type) {
		if (ToStringable.class.isAssignableFrom(type)) {
			return "string";
		}
		switch (type.getSimpleName()) {
		case "String":
			return "string";
		case "BigDecimal":
			return "integer";
		case "Date":
			return "integer";
		case "double":
			return "double";
		case "boolean":
			return "boolean";
		case "int":
			return "integer";
		case "List":
			return "array";
		default:
			return type.getSimpleName();
		}
	}

	private static boolean isBasic(final Class<?> type) {
		switch (type.getSimpleName()) {
		case "String":
			return true;
		case "BigDecimal":
			return true;
		case "Date":
			return true;
		case "double":
			return true;
		case "boolean":
			return true;
		case "int":
			return true;
		default:
			return false;
		}
	}

	private boolean scanned = false;
	private String basePackage;
	private ClassPathScanningCandidateComponentProvider scanner;
	private ListMap<String, SwaggerApi> apis = new ListMap<>();

	private MapMap<String, String, SwaggerModel> models = new MapMap<>();

	List<SwaggerApiShort> apiShorts = new ArrayList<>();

	Paranamer paranamer = new CachingParanamer();

	/**
	 * Constructs a scanner looking for {@link Api} annotated classes.
	 */
	public SwaggerScanner() {
		this.scanner = new ClassPathScanningCandidateComponentProvider(false);
		this.scanner.addIncludeFilter(new AnnotationTypeFilter(Api.class));
	}

	private void addModels(final SwaggerApi api, final Class<?> type) {
		log.fine("Adding model: " + type.getName());
		final SwaggerModel typeModel = new SwaggerModel(type.getSimpleName());
		if (this.models.get(api.path, typeModel.getId()) != null) {
			return;
		}
		this.models.put(api.path, typeModel.getId(), typeModel);
		log.fine("Storing models under: " + api.path);
		final Field[] fields = type.getFields();
		log.fine(fields.length + " fields");
		for (final Field field : fields) {
			log.fine("Field: " + field.getName());
			log.fine("\tAccessible: " + field.isAccessible());
			log.fine("\tSynthetic: " + field.isSynthetic());
			log.fine("\tModifiers: " + field.getModifiers());
			log.fine("\tPublic: " + Modifier.isPublic(field.getModifiers()));
			if (Modifier.isPublic(field.getModifiers())) {
				final SwaggerModelProperty fieldProp = new SwaggerModelProperty(convert(field.getType()), false);
				// lookupResponseProp.items = new SwaggerItemsRef("League");
				typeModel.getProperties().put(field.getName(), fieldProp);
				// api.models.put(lookupResponseModel.getId(),
				// lookupResponseModel);
				if (field.getType().equals("array")) {
					fieldProp.items = new SwaggerItemsRef("stuff");
				} else if (!isBasic(field.getType())) {
					addModels(api, field.getType());
				}
			}
		}
	}

	/**
	 * Fetches an api by its path.
	 * 
	 * @param path
	 *            The path to this API.
	 * @return The API, or null if none match.
	 */
	public List<SwaggerApi> getApi(final String path) {
		if (this.apis.size() == 0) {
			scanForApi();
		}
		return this.apis.get(path);
	}

	/**
	 * Fetches models for an api by its path.
	 * 
	 * @param path
	 *            The path to this API.
	 * @return The API, or null if none match.
	 */
	public Map<String, SwaggerModel> getModels(final String path) {
		log.fine("Fetching models for " + path);
		if (this.models.size() == 0) {
			scanForApi();
		}
		return this.models.get(path);
	}

	private List<SwaggerParameter> getParameters(final Method method) {
		final List<SwaggerParameter> swaggerParameters = new ArrayList<>();
		final Class<?>[] params = method.getParameterTypes();
		String[] parameterNames = this.paranamer.lookupParameterNames(method);
		if (parameterNames.length != params.length) {
			parameterNames = null;
		}
		int index = 0;
		for (int i = 0; i < params.length; i++) {
			if (HttpServletRequest.class.isAssignableFrom(params[i]) || HttpServletResponse.class.isAssignableFrom(params[i])) {
				continue;
			}
			final SwaggerParameter swaggerParameter = new SwaggerParameter(parameterNames == null ? (params[i].getSimpleName() + "(" + index++ + ")") : parameterNames[i], convert(params[i]), "?");
			swaggerParameters.add(swaggerParameter);
		}
		return swaggerParameters;
	}

	/**
	 * Scans for {@link Api} annotated classes.
	 * 
	 * @return A list of short descriptor beans of the annotated classes.
	 */
	public List<SwaggerApiShort> scanForApi() {
		if (this.scanned) {
			return this.apiShorts;
		}
		for (final BeanDefinition bd : this.scanner.findCandidateComponents(this.basePackage)) {
			Api annotation;
			Class<?> clazz;
			try {
				clazz = Class.forName(bd.getBeanClassName());
				annotation = AnnotationUtils.findAnnotation(clazz, Api.class);
			} catch (final ClassNotFoundException e) {
				// This really shouldn't happen since we just got the class
				// name.
				log.log(Level.SEVERE, e.getMessage(), e);
				continue;
			}
			final String path = annotation.basePath();
			final String description = annotation.description();
			final SwaggerApiShort apiShort = new SwaggerApiShort("/" + path, description);
			this.apiShorts.add(apiShort);

			for (final Method method : clazz.getMethods()) {
				final ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
				if (apiOperation == null) {
					continue;
				}
				final RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
				if (requestMapping == null) {
					log.warning("Found @ApiOperation on " + clazz.getName() + "." + method.getName() + " but no @RequestMapping");
					continue;
				}
				RequestMethod[] requestMethods = requestMapping.method();
				if (requestMethods == null || requestMethods.length == 0) {
					requestMethods = new RequestMethod[] { RequestMethod.GET, RequestMethod.POST };
				}
				for (final RequestMethod requestMethod : requestMapping.method()) {
					// TODO Handle multiple request mappings?
					String apiPath = requestMapping.value()[0];
					if (apiPath.startsWith(path)) {
						apiPath = apiPath.substring(path.length());
					} else {
						log.warning("API Path " + apiPath + " does not start with base path " + path);
					}
					final SwaggerApi api = new SwaggerApi(apiPath, description);
					this.apis.putValue(path, api);
					log.fine("Found API " + path + " in class" + bd.getBeanClassName());

					final SwaggerOperation operation = new SwaggerOperation();
					operation.method = requestMethod == null ? "GET" : requestMethod.name();
					operation.responseClass = method.getReturnType().getSimpleName();
					operation.notes = apiOperation.notes();
					operation.nickname = apiOperation.nickname();
					operation.summary = apiOperation.value();

					operation.parameters = getParameters(method);

					api.operations.add(operation);

					// SwaggerModelProperty lookupResponseProp = new
					// SwaggerModelProperty("array", false);
					// lookupResponseProp.items = new SwaggerItemsRef("League");
					// lookupResponseModel.getProperties().put("leagues",
					// lookupResponseProp);

					addModels(api, method.getReturnType());
					addErrors(api, method.getExceptionTypes());

				}
			}

		}
		this.scanned = true;
		return this.apiShorts;
	}

}
