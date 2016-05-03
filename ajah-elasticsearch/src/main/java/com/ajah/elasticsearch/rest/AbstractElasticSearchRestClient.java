/*
 *  Copyright 2014-2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.elasticsearch.rest;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import com.ajah.elasticsearch.ElasticSearchClient;
import com.ajah.elasticsearch.ElasticSearchException;
import com.ajah.elasticsearch.SearchList;
import com.ajah.util.Identifiable;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

/**
 * A base client that allows for easily indexing and searching documents. Does
 * not support cross-index, multi-type searches.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The primary key field.
 * @param <T>
 *            The type of the object stored/returned.
 * @param <C>
 *            The concrete class of the object stored/returned.
 */
@Log
public abstract class AbstractElasticSearchRestClient<K extends Comparable<K>, T extends Identifiable<K>, C extends T> implements ElasticSearchClient<K, T, C> {

	protected RestClient<C> client;
	private final ObjectMapper mapper = new ObjectMapper();
	@Getter
	protected String index;
	@Getter
	protected String type;
	@Getter
	@Setter
	protected Class<?> clazz;

	protected String hostname;
	protected int port;
	protected CloseableHttpClient http;

	/**
	 * Closes the client.
	 * 
	 * @throws ElasticSearchException
	 *             If there was an {@link IOException} when closing the client.
	 * 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws ElasticSearchException {
		try {
			this.client.close();
		} catch (IOException e) {
			throw new ElasticSearchException(e);
		}
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
	 */
	@Override
	public IndexResponse index(final T entity) {
		throw new NotImplementedException("Indexing not implemented yet");
	}

	/**
	 * Returns a result of a search.
	 * 
	 * @param count
	 * @param page
	 * 
	 * @return The results of the search.
	 */
	@Override
	public SearchList<C> search(final QueryBuilder queryBuilder, final SortBuilder[] sortBuilders, final int page, final int count) throws ElasticSearchException {
		final SearchList<C> results = new SearchList<>();
		final long start = System.currentTimeMillis();
		try {
			final SearchRequest requestBuilder = new SearchRequest().indices(this.index).types(this.type).searchType(SearchType.DEFAULT);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().from(page * count).size(count).query(queryBuilder);
			if (sortBuilders != null) {
				for (final SortBuilder sortBuilder : sortBuilders) {
					sourceBuilder.sort(sortBuilder);
				}
			}
			if (queryBuilder != null) {
				sourceBuilder.postFilter(queryBuilder);
			}
			if (getDefaultSort() != null) {
				sourceBuilder.sort(getDefaultSort());
			}

			log.severe(sourceBuilder.toString());
			final RestSearchResponse<C> response = this.client.search(this.hostname, this.port, this.index, this.type, sourceBuilder.toString(), this.clazz, this.http);
			sourceBuilder.toString();
			// log.severe(sourceBuilder.buildAsBytes().toUtf8());
			// final SearchResponse response =
			// requestBuilder.execute().actionGet();
			log.finest(results.getTotalHits() + " hits");
			// results.setTotalHits(response.getHits().getTotalHits());
			// for (final SearchHit hit : response.getHits()) {
			// final C result = this.mapper.readValue(hit.getSourceAsString(),
			// getTargetClass());
			// results.add(result);
			// }
		} catch (final IndexNotFoundException e) {
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
	 */
	@Override
	public SearchList<C> search(final String query) throws ElasticSearchException {
		final SearchList<C> results = new SearchList<>();
		final long start = System.currentTimeMillis();
		try {
			final SearchResponse response = this.client.prepareSearch(this.index).setTypes(this.type).setSearchType(SearchType.DEFAULT).setSize(100).setQuery(QueryBuilders.matchQuery("_all", query))
					.execute().actionGet();
			results.setTotalHits(response.getHits().getTotalHits());
			for (final SearchHit hit : response.getHits()) {
				final C result = this.mapper.readValue(hit.getSourceAsString(), getTargetClass());
				results.add(result);
			}
		} catch (final IndexNotFoundException | IOException e) {
			log.warning(e.getMessage());
			throw new ElasticSearchException(e);
		}
		results.setTime(System.currentTimeMillis() - start);
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public C load(K id) throws ElasticSearchException {
		HttpGet get = new HttpGet("http://" + this.hostname + ":" + this.port + "/" + this.index + "/" + this.type + "/" + id);
		log.fine(get.getURI().toString());
		try (CloseableHttpResponse response = this.http.execute(get)) {
			HttpEntity entity = response.getEntity();
			// EntityUtils.consume(entity);
			// for (final SearchHit hit : response.getHits()) {
			// final C result = this.mapper.readValue(hit.getSourceAsString(),
			// getTargetClass());
			// results.add(result);
			// }

			return ((RestGetResponse<C>) this.mapper.readValue(entity.getContent(), this.clazz)).source;
		} catch (IOException e) {
			throw new ElasticSearchException(e);
		}
	}
}
