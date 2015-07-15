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
import com.ajah.swagger.api.SwaggerParameter;
import com.ajah.swagger.api.SwaggerParameterId;
import com.ajah.swagger.api.SwaggerParameterStatus;
import com.ajah.swagger.api.SwaggerParameterType;

/**
 * Manages data operations for {@link SwaggerParameter}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class SwaggerParameterManager {

	@Autowired
	private SwaggerParameterDao swaggerParameterDao;

	/**
	 * Saves an {@link SwaggerParameter}. Assigns a new ID ({@link UUID}) and
	 * sets the creation date if necessary. If either of these elements are set,
	 * will perform an insert. Otherwise will perform an update.
	 * 
	 * @param swaggerParameter
	 *            The swaggerParameter to save.
	 * @return The result of the save operation, which will include the new
	 *         swaggerParameter at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerParameter> save(SwaggerParameter swaggerParameter) throws DataOperationException {
		boolean create = false;
		if (swaggerParameter.getId() == null) {
			swaggerParameter.setId(new SwaggerParameterId(UUID.randomUUID().toString()));
			create = true;
		}
		if (swaggerParameter.getCreated() == null) {
			swaggerParameter.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<SwaggerParameter> result = this.swaggerParameterDao.insert(swaggerParameter);
			log.fine("Created SwaggerParameter " + swaggerParameter.getName() + " [" + swaggerParameter.getId() + "]");
			return result;
		}
		DataOperationResult<SwaggerParameter> result = this.swaggerParameterDao.update(swaggerParameter);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated SwaggerParameter " + swaggerParameter.getName() + " [" + swaggerParameter.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link SwaggerParameter} by it's ID.
	 * 
	 * @param swaggerParameterId
	 *            The ID to load, required.
	 * @return The matching swaggerParameter, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerParameterNotFoundException
	 *             If the ID specified did not match any swaggerParameters.
	 */
	public SwaggerParameter load(SwaggerParameterId swaggerParameterId) throws DataOperationException, SwaggerParameterNotFoundException {
		SwaggerParameter swaggerParameter = this.swaggerParameterDao.load(swaggerParameterId);
		if (swaggerParameter == null) {
			throw new SwaggerParameterNotFoundException(swaggerParameterId);
		}
		return swaggerParameter;
	}

	/**
	 * Returns a list of {@link SwaggerParameter}s that match the specified
	 * criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The SwaggerParameterType to filter on, optional.
	 * @param status
	 *            The SwaggerParameterStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link SwaggerParameter}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<SwaggerParameter> list(SwaggerOperationId swaggerOperationId, String search, SwaggerParameterType type, SwaggerParameterStatus status, String sort, Order order, int page, int count)
			throws DataOperationException {
		return this.swaggerParameterDao.list(swaggerOperationId, search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link SwaggerParameter} with the given properties.
	 * 
	 * @param name
	 *            The name of the swaggerParameter, required.
	 * @param type
	 *            The type of swaggerParameter, required.
	 * @param status
	 *            The status of the swaggerParameter, required.
	 * @return The result of the creation, which will include the new
	 *         swaggerParameter at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerParameter> create(SwaggerOperationId swaggerOperationId, SwaggerDefinitionId swaggerDefinitionId, String name, String in, boolean required,
			SwaggerParameterType type) throws DataOperationException {
		SwaggerParameter swaggerParameter = new SwaggerParameter();
		swaggerParameter.setSwaggerOperationId(swaggerOperationId);
		swaggerParameter.setSwaggerDefinitionId(swaggerDefinitionId);
		swaggerParameter.setName(name);
		swaggerParameter.setIn(in);
		swaggerParameter.setRequired(required);
		swaggerParameter.setType(type);
		swaggerParameter.setStatus(SwaggerParameterStatus.ACTIVE);
		DataOperationResult<SwaggerParameter> result = save(swaggerParameter);
		return result;
	}

	/**
	 * Marks the entity as {@link SwaggerParameterStatus#DELETED}.
	 * 
	 * @param swaggerParameterId
	 *            The ID of the swaggerParameter to delete.
	 * @return The result of the deletion, will not include the new
	 *         swaggerParameter at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerParameterNotFoundException
	 *             If the ID specified did not match any swaggerParameters.
	 */
	public DataOperationResult<SwaggerParameter> delete(SwaggerParameterId swaggerParameterId) throws DataOperationException, SwaggerParameterNotFoundException {
		SwaggerParameter swaggerParameter = load(swaggerParameterId);
		swaggerParameter.setStatus(SwaggerParameterStatus.DELETED);
		DataOperationResult<SwaggerParameter> result = save(swaggerParameter);
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
	 *            The swaggerParameter type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(SwaggerOperationId swaggerOperationId, final SwaggerParameterType type, final SwaggerParameterStatus status) throws DataOperationException {
		return this.swaggerParameterDao.count(swaggerOperationId, type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The SwaggerParameterType to filter on, optional.
	 * @param status
	 *            The SwaggerParameterStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(SwaggerOperationId swaggerOperationId, String search, SwaggerParameterType type, SwaggerParameterStatus status) throws DataOperationException {
		return this.swaggerParameterDao.searchCount(swaggerOperationId, search, type, status);
	}

}
