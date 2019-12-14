/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.swagger.api.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.swagger.api.SwaggerApi;
import com.ajah.swagger.api.SwaggerApiId;
import com.ajah.swagger.api.SwaggerApiStatus;
import com.ajah.swagger.api.SwaggerApiType;

/**
 * Manages data operations for {@link SwaggerApi}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class SwaggerApiManager {

	@Autowired
	private SwaggerApiDao swaggerApiDao;

	/**
	 * Saves an {@link SwaggerApi}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param swaggerApi
	 *            The swaggerApi to save.
	 * @return The result of the save operation, which will include the new
	 *         swaggerApi at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerApi> save(SwaggerApi swaggerApi) throws DataOperationException {
		boolean create = false;
		if (swaggerApi.getId() == null) {
			swaggerApi.setId(new SwaggerApiId(UUID.randomUUID().toString()));
			create = true;
		}
		if (swaggerApi.getCreated() == null) {
			swaggerApi.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<SwaggerApi> result = this.swaggerApiDao.insert(swaggerApi);
			log.fine("Created SwaggerApi " + swaggerApi.getName() + " [" + swaggerApi.getId() + "]");
			return result;
		}
		DataOperationResult<SwaggerApi> result = this.swaggerApiDao.update(swaggerApi);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated SwaggerApi " + swaggerApi.getName() + " [" + swaggerApi.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link SwaggerApi} by it's ID.
	 * 
	 * @param swaggerApiId
	 *            The ID to load, required.
	 * @return The matching swaggerApi, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerApiNotFoundException
	 *             If the ID specified did not match any swaggerApis.
	 */
	public SwaggerApi load(SwaggerApiId swaggerApiId) throws DataOperationException, SwaggerApiNotFoundException {
		SwaggerApi swaggerApi = this.swaggerApiDao.load(swaggerApiId);
		if (swaggerApi == null) {
			throw new SwaggerApiNotFoundException(swaggerApiId);
		}
		return swaggerApi;
	}

	/**
	 * Returns a list of {@link SwaggerApi}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The SwaggerApiType to filter on, optional.
	 * @param status
	 *            The SwaggerApiStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link SwaggerApi}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<SwaggerApi> list(String search, SwaggerApiType type, SwaggerApiStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.swaggerApiDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link SwaggerApi} with the given properties.
	 * 
	 * @param name
	 *            The name of the swaggerApi, required.
	 * @param type
	 *            The type of swaggerApi, required.
	 * @param status
	 *            The status of the swaggerApi, required.
	 * @return The result of the creation, which will include the new swaggerApi
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerApi> create(String name, String description, String version, String host, String basePath, String bucket, String docRoot, boolean http, boolean https,
			boolean json, boolean xml, SwaggerApiType type, SwaggerApiStatus status) throws DataOperationException {
		SwaggerApi swaggerApi = new SwaggerApi();
		swaggerApi.setName(name);
		swaggerApi.setDescription(description);
		swaggerApi.setVersion(version);
		swaggerApi.setHost(host);
		swaggerApi.setBasePath(basePath);
		swaggerApi.setBucket(bucket);
		swaggerApi.setDocRoot(docRoot);
		swaggerApi.setHttp(http);
		swaggerApi.setHttps(https);
		swaggerApi.setJson(json);
		swaggerApi.setXml(xml);
		swaggerApi.setType(type);
		swaggerApi.setStatus(status);
		return save(swaggerApi);
	}

	/**
	 * Marks the entity as {@link SwaggerApiStatus#DELETED}.
	 * 
	 * @param swaggerApiId
	 *            The ID of the swaggerApi to delete.
	 * @return The result of the deletion, will not include the new swaggerApi
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerApiNotFoundException
	 *             If the ID specified did not match any swaggerApis.
	 */
	public DataOperationResult<SwaggerApi> delete(SwaggerApiId swaggerApiId) throws DataOperationException, SwaggerApiNotFoundException {
		SwaggerApi swaggerApi = load(swaggerApiId);
		swaggerApi.setStatus(SwaggerApiStatus.DELETED);
		return save(swaggerApi);
	}

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count() throws DataOperationException {
		return count(null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The swaggerApi type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final SwaggerApiType type, final SwaggerApiStatus status) throws DataOperationException {
		return this.swaggerApiDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The SwaggerApiType to filter on, optional.
	 * @param status
	 *            The SwaggerApiStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, SwaggerApiType type, SwaggerApiStatus status) throws DataOperationException {
		return this.swaggerApiDao.searchCount(search, type, status);
	}

}
