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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ajah.lang.ListMap;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Service
@Data
@Log
@AllArgsConstructor
public class SwaggerScanner {

	private boolean scanned = false;
	private String basePackage;
	private ClassPathScanningCandidateComponentProvider scanner;
	private ListMap<String, SwaggerApi> apis = new ListMap<>();
	List<SwaggerApiShort> apiShorts = new ArrayList<>();

	/**
	 * Constructs a scanner looking for {@link Api} annotated classes.
	 */
	public SwaggerScanner() {
		this.scanner = new ClassPathScanningCandidateComponentProvider(false);
		this.scanner.addIncludeFilter(new AnnotationTypeFilter(Api.class));
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
		for (BeanDefinition bd : this.scanner.findCandidateComponents(this.basePackage)) {
			Api annotation;
			Class<?> clazz;
			try {
				clazz = Class.forName(bd.getBeanClassName());
				annotation = AnnotationUtils.findAnnotation(clazz, Api.class);
			} catch (ClassNotFoundException e) {
				// This really shouldn't happen since we just got the class
				// name.
				log.log(Level.SEVERE, e.getMessage(), e);
				continue;
			}
			String path = annotation.basePath();
			String description = annotation.description();
			SwaggerApiShort apiShort = new SwaggerApiShort("/" + path, description);
			this.apiShorts.add(apiShort);

			for (Method method : clazz.getMethods()) {
				ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
				RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
				if (requestMapping == null) {
					log.warning("Found @ApiOperation on " + clazz.getName() + "." + method.getName() + " but no @RequestMapping");
					continue;
				}
				// TODO Handle multiple request mappings?
				String apiPath = requestMapping.value()[0];
				if (apiPath.startsWith(path)) {
					apiPath = apiPath.substring(path.length());
				} else {
					log.warning("API Path " + apiPath + " does not start with base path " + path);
				}
				SwaggerApi api = new SwaggerApi(apiPath, description);
				this.apis.putValue(path, api);
				log.fine("Found API " + path + " in class" + bd.getBeanClassName());

				if (apiOperation == null) {
					continue;
				}
				SwaggerOperation operation = new SwaggerOperation();
				operation.method = requestMapping.method() == null ? "GET" : requestMapping.method()[0].name();
				operation.responseClass = method.getReturnType().getName();
				operation.notes = apiOperation.notes();
				operation.nickname = apiOperation.nickname();
				operation.summary = apiOperation.value();
				api.operations.add(operation);
			}

		}
		this.scanned = true;
		return this.apiShorts;
	}

	/**
	 * Fetches an api by its path.
	 * 
	 * @param path
	 *            The path to this API.
	 * @return The API, or null if none match.
	 */
	public List<SwaggerApi> getApi(String path) {
		if (this.apis.size() == 0) {
			scanForApi();
		}
		return this.apis.get(path);
	}

}
