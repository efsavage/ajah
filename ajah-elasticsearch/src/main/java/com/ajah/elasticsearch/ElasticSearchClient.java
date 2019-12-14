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
package com.ajah.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import com.ajah.util.Identifiable;

/**
 * Interface for Elastic Search Clients.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The type of key/ID.
 * @param <T>
 *            The type (may be an interface).
 * @param <C>
 *            The implemented type (must be concrete class).
 * 
 */
public interface ElasticSearchClient<K extends Comparable<K>, T extends Identifiable<K>, C extends T> extends AutoCloseable {

	/**
	 * Indexes an entity.
	 * 
	 * @param entity
	 *            The entity to index.
	 * @return Object with details of the outcome of the operation.
	 * @throws ElasticSearchException
	 *             If the index could not be completed.
	 */
	IndexResponse index(final T entity) throws ElasticSearchException;

	/**
	 * Searches the default set of fields with the given query.
	 * 
	 * @param query
	 *            The query to execute.
	 * @return A list of results.
	 * @throws ElasticSearchException
	 *             If the search could not be executed.
	 */
	SearchList<C> search(final String query) throws ElasticSearchException;

	/**
	 * Loads an entity by its ID.
	 * 
	 * @param id
	 *            The ID to match on.
	 * @return The entity, or null if not found.
	 * @throws ElasticSearchException
	 *             If the search could not be executed.
	 */
	C load(final K id) throws ElasticSearchException;

	/**
	 * Executes a search based on builders.
	 * 
	 * @param queryBuilder
	 *            The query builder, may be null or empty.
	 * @param sortBuilders
	 *            The sort builder, may be null or empty.
	 * @param page
	 *            The 0-based page of results
	 * @param count
	 *            The number of results per page.
	 * @return A list of results.
	 * @throws ElasticSearchException
	 *             If the search could not be executed.
	 */
	SearchList<C> search(final QueryBuilder queryBuilder, final SortBuilder[] sortBuilders, final int page, final int count) throws ElasticSearchException;

	/**
	 * Closes the client.
	 * 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	void close() throws ElasticSearchException;

}
