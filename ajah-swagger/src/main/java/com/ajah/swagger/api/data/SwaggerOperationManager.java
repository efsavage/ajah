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

import com.ajah.http.HttpMethod;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.swagger.api.SwaggerApiId;
import com.ajah.swagger.api.SwaggerOperation;
import com.ajah.swagger.api.SwaggerOperationId;
import com.ajah.swagger.api.SwaggerOperationStatus;
import com.ajah.swagger.api.SwaggerOperationType;

/**
 * Manages data operations for {@link SwaggerOperation}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class SwaggerOperationManager {

	@Autowired
	private SwaggerOperationDao swaggerOperationDao;

	/**
	 * Saves an {@link SwaggerOperation}. Assigns a new ID ({@link UUID}) and
	 * sets the creation date if necessary. If either of these elements are set,
	 * will perform an insert. Otherwise will perform an update.
	 * 
	 * @param swaggerOperation
	 *            The swaggerOperation to save.
	 * @return The result of the save operation, which will include the new
	 *         swaggerOperation at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerOperation> save(SwaggerOperation swaggerOperation) throws DataOperationException {
		boolean create = false;
		if (swaggerOperation.getId() == null) {
			swaggerOperation.setId(new SwaggerOperationId(UUID.randomUUID().toString()));
			create = true;
		}
		if (swaggerOperation.getCreated() == null) {
			swaggerOperation.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<SwaggerOperation> result = this.swaggerOperationDao.insert(swaggerOperation);
			log.fine("Created SwaggerOperation " + swaggerOperation.getName() + " [" + swaggerOperation.getId() + "]");
			return result;
		}
		DataOperationResult<SwaggerOperation> result = this.swaggerOperationDao.update(swaggerOperation);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated SwaggerOperation " + swaggerOperation.getName() + " [" + swaggerOperation.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link SwaggerOperation} by it's ID.
	 * 
	 * @param swaggerOperationId
	 *            The ID to load, required.
	 * @return The matching swaggerOperation, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerOperationNotFoundException
	 *             If the ID specified did not match any swaggerOperations.
	 */
	public SwaggerOperation load(SwaggerOperationId swaggerOperationId) throws DataOperationException, SwaggerOperationNotFoundException {
		SwaggerOperation swaggerOperation = this.swaggerOperationDao.load(swaggerOperationId);
		if (swaggerOperation == null) {
			throw new SwaggerOperationNotFoundException(swaggerOperationId);
		}
		return swaggerOperation;
	}

	/**
	 * Returns a list of {@link SwaggerOperation}s that match the specified
	 * criteria.
	 * 
	 * @param swaggerApiId
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The SwaggerOperationType to filter on, optional.
	 * @param status
	 *            The SwaggerOperationStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link SwaggerOperation}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<SwaggerOperation> list(SwaggerApiId swaggerApiId, String search, SwaggerOperationType type, SwaggerOperationStatus status, String sort, Order order, int page, int count)
			throws DataOperationException {
		return this.swaggerOperationDao.list(swaggerApiId, search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link SwaggerOperation} with the given properties.
	 * 
	 * @param name
	 *            The name of the swaggerOperation, required.
	 * @return The result of the creation, which will include the new
	 *         swaggerOperation at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerOperation> create(SwaggerApiId swaggerApiId, String name, String path, HttpMethod method, String summary, String description, String consumes, String produces,
			String operationId, String tags) throws DataOperationException {
		SwaggerOperation swaggerOperation = new SwaggerOperation();
		swaggerOperation.setSwaggerApiId(swaggerApiId);
		swaggerOperation.setName(name);
		swaggerOperation.setPath(path);
		swaggerOperation.setMethod(method);
		swaggerOperation.setSummary(summary);
		swaggerOperation.setDescription(description);
		swaggerOperation.setConsumes(consumes);
		swaggerOperation.setProduces(produces);
		swaggerOperation.setOperationId(operationId);
		swaggerOperation.setTags(tags);
		swaggerOperation.setType(SwaggerOperationType.STANDARD);
		swaggerOperation.setStatus(SwaggerOperationStatus.ACTIVE);
		DataOperationResult<SwaggerOperation> result = save(swaggerOperation);
		return result;
	}

	/**
	 * Marks the entity as {@link SwaggerOperationStatus#DELETED}.
	 * 
	 * @param swaggerOperationId
	 *            The ID of the swaggerOperation to delete.
	 * @return The result of the deletion, will not include the new
	 *         swaggerOperation at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerOperationNotFoundException
	 *             If the ID specified did not match any swaggerOperations.
	 */
	public DataOperationResult<SwaggerOperation> delete(SwaggerOperationId swaggerOperationId) throws DataOperationException, SwaggerOperationNotFoundException {
		SwaggerOperation swaggerOperation = load(swaggerOperationId);
		swaggerOperation.setStatus(SwaggerOperationStatus.DELETED);
		DataOperationResult<SwaggerOperation> result = save(swaggerOperation);
		return result;
	}

	/**
	 * Returns a count of all records.
	 * 
	 * @param swaggerApiId
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(SwaggerApiId swaggerApiId) throws DataOperationException {
		return count(swaggerApiId, null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param swaggerApiId
	 * 
	 * @param type
	 *            The swaggerOperation type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(SwaggerApiId swaggerApiId, final SwaggerOperationType type, final SwaggerOperationStatus status) throws DataOperationException {
		return this.swaggerOperationDao.count(swaggerApiId, type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param swaggerApiId
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The SwaggerOperationType to filter on, optional.
	 * @param status
	 *            The SwaggerOperationStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(SwaggerApiId swaggerApiId, String search, SwaggerOperationType type, SwaggerOperationStatus status) throws DataOperationException {
		return this.swaggerOperationDao.searchCount(swaggerApiId, search, type, status);
	}

}
