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
import com.ajah.swagger.api.SwaggerDefinitionId;
import com.ajah.swagger.api.SwaggerOperationId;
import com.ajah.swagger.api.SwaggerResponse;
import com.ajah.swagger.api.SwaggerResponseId;
import com.ajah.swagger.api.SwaggerResponseStatus;
import com.ajah.swagger.api.SwaggerResponseType;
import com.ajah.util.AjahUtils;

/**
 * Manages data operations for {@link SwaggerResponse}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class SwaggerResponseManager {

	@Autowired
	private SwaggerResponseDao swaggerResponseDao;

	/**
	 * Saves an {@link SwaggerResponse}. Assigns a new ID ({@link UUID}) and
	 * sets the creation date if necessary. If either of these elements are set,
	 * will perform an insert. Otherwise will perform an update.
	 * 
	 * @param swaggerResponse
	 *            The swaggerResponse to save.
	 * @return The result of the save operation, which will include the new
	 *         swaggerResponse at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerResponse> save(SwaggerResponse swaggerResponse) throws DataOperationException {
		boolean create = false;
		if (swaggerResponse.getId() == null) {
			swaggerResponse.setId(new SwaggerResponseId(UUID.randomUUID().toString()));
			create = true;
		}
		if (swaggerResponse.getCreated() == null) {
			swaggerResponse.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<SwaggerResponse> result = this.swaggerResponseDao.insert(swaggerResponse);
			log.fine("Created SwaggerResponse " + swaggerResponse.getName() + " [" + swaggerResponse.getId() + "]");
			return result;
		}
		DataOperationResult<SwaggerResponse> result = this.swaggerResponseDao.update(swaggerResponse);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated SwaggerResponse " + swaggerResponse.getName() + " [" + swaggerResponse.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link SwaggerResponse} by it's ID.
	 * 
	 * @param swaggerResponseId
	 *            The ID to load, required.
	 * @return The matching swaggerResponse, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerResponseNotFoundException
	 *             If the ID specified did not match any swaggerResponses.
	 */
	public SwaggerResponse load(SwaggerResponseId swaggerResponseId) throws DataOperationException, SwaggerResponseNotFoundException {
		SwaggerResponse swaggerResponse = this.swaggerResponseDao.load(swaggerResponseId);
		if (swaggerResponse == null) {
			throw new SwaggerResponseNotFoundException(swaggerResponseId);
		}
		return swaggerResponse;
	}

	/**
	 * Returns a list of {@link SwaggerResponse}s that match the specified
	 * criteria.
	 * 
	 * @param swaggerOperationId
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The SwaggerResponseType to filter on, optional.
	 * @param status
	 *            The SwaggerResponseStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link SwaggerResponse}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<SwaggerResponse> list(SwaggerOperationId swaggerOperationId, String search, SwaggerResponseType type, SwaggerResponseStatus status, String sort, Order order, int page, int count)
			throws DataOperationException {
		return this.swaggerResponseDao.list(swaggerOperationId, search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link SwaggerResponse} with the given properties.
	 * 
	 * @param name
	 *            The name of the swaggerResponse, required.
	 * @param type
	 *            The type of swaggerResponse, required.
	 * @return The result of the creation, which will include the new
	 *         swaggerResponse at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerResponse> create(SwaggerOperationId swaggerOperationId, SwaggerDefinitionId swaggerDefinitionId, String name, String description, String code,
			SwaggerResponseType type) throws DataOperationException {
		AjahUtils.requireParam(swaggerOperationId, "swaggerOperationId");
		AjahUtils.requireParam(type, "type");
		if (type == SwaggerResponseType.DEFINITION) {
			AjahUtils.requireParam(swaggerDefinitionId, "swaggerDefinitionId");
		}
		SwaggerResponse swaggerResponse = new SwaggerResponse();
		swaggerResponse.setSwaggerOperationId(swaggerOperationId);
		swaggerResponse.setSwaggerDefinitionId(swaggerDefinitionId);
		swaggerResponse.setName(name);
		swaggerResponse.setDescription(description);
		swaggerResponse.setCode(code);
		swaggerResponse.setType(type);
		swaggerResponse.setStatus(SwaggerResponseStatus.ACTIVE);
		DataOperationResult<SwaggerResponse> result = save(swaggerResponse);
		return result;
	}

	/**
	 * Marks the entity as {@link SwaggerResponseStatus#DELETED}.
	 * 
	 * @param swaggerResponseId
	 *            The ID of the swaggerResponse to delete.
	 * @return The result of the deletion, will not include the new
	 *         swaggerResponse at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerResponseNotFoundException
	 *             If the ID specified did not match any swaggerResponses.
	 */
	public DataOperationResult<SwaggerResponse> delete(SwaggerResponseId swaggerResponseId) throws DataOperationException, SwaggerResponseNotFoundException {
		SwaggerResponse swaggerResponse = load(swaggerResponseId);
		swaggerResponse.setStatus(SwaggerResponseStatus.DELETED);
		DataOperationResult<SwaggerResponse> result = save(swaggerResponse);
		return result;
	}

	/**
	 * Returns a count of all records.
	 * 
	 * @param swaggerOperationId
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(SwaggerOperationId swaggerOperationId) throws DataOperationException {
		return count(swaggerOperationId, null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param swaggerOperationId
	 * 
	 * @param type
	 *            The swaggerResponse type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(SwaggerOperationId swaggerOperationId, final SwaggerResponseType type, final SwaggerResponseStatus status) throws DataOperationException {
		return this.swaggerResponseDao.count(swaggerOperationId, type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param swaggerOperationId
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The SwaggerResponseType to filter on, optional.
	 * @param status
	 *            The SwaggerResponseStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(SwaggerOperationId swaggerOperationId, String search, SwaggerResponseType type, SwaggerResponseStatus status) throws DataOperationException {
		return this.swaggerResponseDao.searchCount(swaggerOperationId, search, type, status);
	}

}
