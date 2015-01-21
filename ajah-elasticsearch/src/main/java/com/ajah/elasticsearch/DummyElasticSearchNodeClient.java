/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.elasticsearch;

import java.lang.reflect.ParameterizedType;

import lombok.extern.java.Log;

import org.elasticsearch.action.index.IndexResponse;

import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A base client that allows for easily indexing and searching documents. Does
 * not support cross-index, multi-type searches.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The primary key field.
 * @param <T>
 *            The type of the object stored/returned.
 * @param <C>
 *            The concrete class of the object stored/returned.
 */
@Log
public abstract class DummyElasticSearchNodeClient<K extends Comparable<K>, T extends Identifiable<K>, C extends T> implements ElasticSearchClient<K, T, C> {

	private final ObjectMapper mapper = new ObjectMapper();
	private String index;
	private String type;
	private String clusterName;

	/**
	 * Public constructor with data set to false and no additional
	 * configurations.
	 */
	public DummyElasticSearchNodeClient() {
		this(false);
	}

	/**
	 * Public constructor with configurable data parameter and all other values
	 * set to defaults.
	 * 
	 * @param data
	 *            Should this client use a data node?
	 */
	public DummyElasticSearchNodeClient(final boolean data) {
		this(data, null, null, null);
	}

	/**
	 * @param data
	 *            Should this client use a data node?
	 * @param typeName
	 *            The name of the type. If null it will attempt to infer the
	 *            type from the class parameters.
	 * @param indexName
	 *            The name of the index. If null it will use the type.
	 * @param clusterName
	 *            The name of the cluster to use. If null it will use the type.
	 */
	public DummyElasticSearchNodeClient(final boolean data, final String typeName, final String indexName, final String clusterName) {
		// Derive the cluster, index, type name if needed
		if (StringUtils.isBlank(typeName)) {
			final String simpleName = getTargetClass().getSimpleName();
			this.type = StringUtils.splitCamelCase(simpleName).replaceAll("\\W+", "_").toLowerCase();
		} else {
			this.type = typeName;
		}
		log.finest("Type is: " + this.type);
		if (StringUtils.isBlank(indexName)) {
			this.index = this.type;
		} else {
			this.index = indexName;
		}
		log.fine("Index is: " + this.index);
		if (clusterName == null) {
			this.clusterName = this.type;
		} else {
			this.clusterName = clusterName;
		}
		log.fine("Cluster name is: " + this.clusterName);

		log.fine("Waiting for green status");
		log.fine("Green status achieved");
	}

	@SuppressWarnings("unchecked")
	private Class<C> getTargetClass() {
		return (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}

	/**
	 * Indexes a document.
	 * 
	 * @param entity
	 *            The document to index.
	 * @return The response of the index operations, synchronously.
	 */
	@Override
	public IndexResponse index(final T entity) throws ElasticSearchException {

		// IndexRequestBuilder irb = this.client.prepareIndex(this.index,
		// this.type, entity.getId().toString());
		try {
			final String json = this.mapper.writeValueAsString(entity);
			log.finest(json);
		} catch (JsonProcessingException e) {
			throw new ElasticSearchException(e);
		}
		log.finest("Executed");
		return null;

	}

	/**
	 * Returns a result of a search.
	 * 
	 * @param query
	 *            The search query.
	 * @return The results of the search.
	 */
	@Override
	public SearchList<C> search(final String query) throws ElasticSearchException {
		return new SearchList<>();
	}

}
