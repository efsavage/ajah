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

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import lombok.extern.java.Log;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilder;

import com.ajah.util.Identifiable;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
public abstract class AbstractElasticSearchClient<K extends Comparable<K>, T extends Identifiable<K>, C extends T> implements ElasticSearchClient<K, T, C> {

	protected Client client;
	private final ObjectMapper mapper = new ObjectMapper();
	protected String index;
	protected String type;
	protected String clusterName;

	/**
	 * Closes the node.
	 * 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		this.client.close();
	}

	/**
	 * Creates the index for this type.
	 * 
	 * @return The acknowledgment of the request.
	 */
	public boolean createIndex() {
		final CreateIndexRequest createIndexRequest = new CreateIndexRequest(this.index);
		final CreateIndexResponse createResponse = this.client.admin().indices().create(createIndexRequest).actionGet();
		return createResponse.isAcknowledged();
	}

	@SuppressWarnings("static-method")
	protected SortBuilder getDefaultSort() {
		return null;
	}

	@SuppressWarnings("unchecked")
	protected Class<C> getTargetClass() {
		return (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}

	/**
	 * Indexes a document.
	 * 
	 * @param entity
	 *            The document to index.
	 * @return The response of the index operations, synchronously.
	 * @throws JsonProcessingException
	 *             If the entity could not be parsed into JSON.
	 */
	@Override
	public IndexResponse index(final T entity) throws JsonProcessingException {

		final IndexRequestBuilder irb = this.client.prepareIndex(this.index, this.type, entity.getId().toString());
		final String json = this.mapper.writeValueAsString(entity);
		log.finest(json);
		irb.setSource(json);
		final ListenableActionFuture<IndexResponse> result = irb.execute();
		log.finest("Executed");
		return result.actionGet();
	}

	/**
	 * Returns a result of a search.
	 * 
	 * @param count
	 * @param page
	 * 
	 * @return The results of the search.
	 * @throws JsonParseException
	 *             If the entity could not be parsed from the JSON.
	 * @throws JsonMappingException
	 *             If the entity could not be parsed from the JSON.
	 * @throws IOException
	 *             If the query failed to execute.
	 */
	@Override
	public SearchList<C> search(final QueryBuilder queryBuilder, final FilterBuilder filterBuilder, final SortBuilder[] sortBuilders, final int page, final int count) throws IOException {
		final SearchList<C> results = new SearchList<>();
		long start = System.currentTimeMillis();
		try {
			final SearchRequestBuilder requestBuilder = this.client.prepareSearch(this.index).setTypes(this.type).setSearchType(SearchType.DEFAULT).setFrom(page * count).setSize(count)
					.setQuery(queryBuilder);
			if (sortBuilders != null) {
				for (final SortBuilder sortBuilder : sortBuilders) {
					requestBuilder.addSort(sortBuilder);
				}
			}
			if (filterBuilder != null) {
				requestBuilder.setPostFilter(filterBuilder);
			}
			if (getDefaultSort() != null) {
				requestBuilder.addSort(getDefaultSort());
			}

			final SearchResponse response = requestBuilder.execute().actionGet();
			log.finest(results.getTotalHits() + " hits");
			results.setTotalHits(response.getHits().getTotalHits());
			for (final SearchHit hit : response.getHits()) {
				final C result = this.mapper.readValue(hit.getSourceAsString(), getTargetClass());
				results.add(result);
			}
		} catch (final IndexMissingException e) {
			log.warning(e.getMessage());
		}
		results.setTime(System.currentTimeMillis() - start);
		return results;
	}

	/**
	 * Returns a result of a search.
	 * 
	 * @param query
	 *            The search query.
	 * @return The results of the search.
	 * @throws JsonParseException
	 *             If the entity could not be parsed from the JSON.
	 * @throws JsonMappingException
	 *             If the entity could not be parsed from the JSON.
	 * @throws IOException
	 *             If the query failed to execute.
	 */
	@Override
	public SearchList<C> search(final String query) throws IOException {
		final SearchList<C> results = new SearchList<>();
		long start = System.currentTimeMillis();
		try {
			final SearchResponse response = this.client.prepareSearch(this.index).setTypes(this.type).setSearchType(SearchType.DEFAULT).setSize(100).setQuery(QueryBuilders.matchQuery("_all", query))
					.execute().actionGet();
			results.setTotalHits(response.getHits().getTotalHits());
			for (final SearchHit hit : response.getHits()) {
				final C result = this.mapper.readValue(hit.getSourceAsString(), getTargetClass());
				results.add(result);
			}
		} catch (final IndexMissingException e) {
			log.warning(e.getMessage());
		}
		results.setTime(System.currentTimeMillis() - start);
		return results;
	}

}
