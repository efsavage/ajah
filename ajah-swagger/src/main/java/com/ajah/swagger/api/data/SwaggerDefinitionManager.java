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
import com.ajah.swagger.api.SwaggerApiId;
import com.ajah.swagger.api.SwaggerDefinition;
import com.ajah.swagger.api.SwaggerDefinitionId;
import com.ajah.swagger.api.SwaggerDefinitionStatus;
import com.ajah.swagger.api.SwaggerDefinitionType;

/**
 * Manages data operations for {@link SwaggerDefinition}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class SwaggerDefinitionManager {

	@Autowired
	private SwaggerDefinitionDao swaggerDefinitionDao;

	/**
	 * Saves an {@link SwaggerDefinition}. Assigns a new ID ({@link UUID}) and
	 * sets the creation date if necessary. If either of these elements are set,
	 * will perform an insert. Otherwise will perform an update.
	 * 
	 * @param swaggerDefinition
	 *            The swaggerDefinition to save.
	 * @return The result of the save operation, which will include the new
	 *         swaggerDefinition at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerDefinition> save(SwaggerDefinition swaggerDefinition) throws DataOperationException {
		boolean create = false;
		if (swaggerDefinition.getId() == null) {
			swaggerDefinition.setId(new SwaggerDefinitionId(UUID.randomUUID().toString()));
			create = true;
		}
		if (swaggerDefinition.getCreated() == null) {
			swaggerDefinition.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<SwaggerDefinition> result = this.swaggerDefinitionDao.insert(swaggerDefinition);
			log.fine("Created SwaggerDefinition " + swaggerDefinition.getName() + " [" + swaggerDefinition.getId() + "]");
			return result;
		}
		DataOperationResult<SwaggerDefinition> result = this.swaggerDefinitionDao.update(swaggerDefinition);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated SwaggerDefinition " + swaggerDefinition.getName() + " [" + swaggerDefinition.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link SwaggerDefinition} by it's ID.
	 * 
	 * @param swaggerDefinitionId
	 *            The ID to load, required.
	 * @return The matching swaggerDefinition, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerDefinitionNotFoundException
	 *             If the ID specified did not match any swaggerDefinitions.
	 */
	public SwaggerDefinition load(SwaggerDefinitionId swaggerDefinitionId) throws DataOperationException, SwaggerDefinitionNotFoundException {
		SwaggerDefinition swaggerDefinition = this.swaggerDefinitionDao.load(swaggerDefinitionId);
		if (swaggerDefinition == null) {
			throw new SwaggerDefinitionNotFoundException(swaggerDefinitionId);
		}
		return swaggerDefinition;
	}

	/**
	 * Returns a list of {@link SwaggerDefinition}s that match the specified
	 * criteria.
	 * @param swaggerApiId 
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The SwaggerDefinitionType to filter on, optional.
	 * @param status
	 *            The SwaggerDefinitionStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link SwaggerDefinition}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<SwaggerDefinition> list(SwaggerApiId swaggerApiId, String search, SwaggerDefinitionType type, SwaggerDefinitionStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.swaggerDefinitionDao.list(swaggerApiId,search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link SwaggerDefinition} with the given properties.
	 * 
	 * @param name
	 *            The name of the swaggerDefinition, required.
	 * @return The result of the creation, which will include the new
	 *         swaggerDefinition at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerDefinition> create(SwaggerApiId swaggerApiId, String name) throws DataOperationException {
		SwaggerDefinition swaggerDefinition = new SwaggerDefinition();
		swaggerDefinition.setSwaggerApiId(swaggerApiId);
		swaggerDefinition.setName(name);
		swaggerDefinition.setType(SwaggerDefinitionType.STANDARD);
		swaggerDefinition.setStatus(SwaggerDefinitionStatus.ACTIVE);
		return save(swaggerDefinition);
	}

	/**
	 * Marks the entity as {@link SwaggerDefinitionStatus#DELETED}.
	 * 
	 * @param swaggerDefinitionId
	 *            The ID of the swaggerDefinition to delete.
	 * @return The result of the deletion, will not include the new
	 *         swaggerDefinition at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerDefinitionNotFoundException
	 *             If the ID specified did not match any swaggerDefinitions.
	 */
	public DataOperationResult<SwaggerDefinition> delete(SwaggerDefinitionId swaggerDefinitionId) throws DataOperationException, SwaggerDefinitionNotFoundException {
		SwaggerDefinition swaggerDefinition = load(swaggerDefinitionId);
		swaggerDefinition.setStatus(SwaggerDefinitionStatus.DELETED);
		return save(swaggerDefinition);
	}

	/**
	 * Returns a count of all records.
	 * @param swaggerApiId 
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(SwaggerApiId swaggerApiId) throws DataOperationException {
		return count(swaggerApiId,null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * @param swaggerApiId 
	 * 
	 * @param type
	 *            The swaggerDefinition type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(SwaggerApiId swaggerApiId, final SwaggerDefinitionType type, final SwaggerDefinitionStatus status) throws DataOperationException {
		return this.swaggerDefinitionDao.count(swaggerApiId,type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * @param swaggerApiId 
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The SwaggerDefinitionType to filter on, optional.
	 * @param status
	 *            The SwaggerDefinitionStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(SwaggerApiId swaggerApiId, String search, SwaggerDefinitionType type, SwaggerDefinitionStatus status) throws DataOperationException {
		return this.swaggerDefinitionDao.searchCount(swaggerApiId,search, type, status);
	}

}
