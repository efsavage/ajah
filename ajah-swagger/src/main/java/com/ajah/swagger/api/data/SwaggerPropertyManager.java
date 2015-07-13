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
import com.ajah.swagger.api.SwaggerProperty;
import com.ajah.swagger.api.SwaggerPropertyId;
import com.ajah.swagger.api.SwaggerPropertyStatus;
import com.ajah.swagger.api.SwaggerPropertyType;

/**
 * Manages data operations for {@link SwaggerProperty}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class SwaggerPropertyManager {

	@Autowired
	private SwaggerPropertyDao swaggerPropertyDao;

	/**
	 * Saves an {@link SwaggerProperty}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param swaggerProperty
	 *            The swaggerProperty to save.
	 * @return The result of the save operation, which will include the new
	 *         swaggerProperty at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerProperty> save(SwaggerProperty swaggerProperty) throws DataOperationException {
		boolean create = false;
		if (swaggerProperty.getId() == null) {
			swaggerProperty.setId(new SwaggerPropertyId(UUID.randomUUID().toString()));
			create = true;
		}
		if (swaggerProperty.getCreated() == null) {
			swaggerProperty.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<SwaggerProperty> result = this.swaggerPropertyDao.insert(swaggerProperty);
			log.fine("Created SwaggerProperty " + swaggerProperty.getName() + " [" + swaggerProperty.getId() + "]");
			return result;
		}
		DataOperationResult<SwaggerProperty> result = this.swaggerPropertyDao.update(swaggerProperty);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated SwaggerProperty " + swaggerProperty.getName() + " [" + swaggerProperty.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link SwaggerProperty} by it's ID.
	 * 
	 * @param swaggerPropertyId
	 *            The ID to load, required.
	 * @return The matching swaggerProperty, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerPropertyNotFoundException
	 *             If the ID specified did not match any swaggerPropertys.
	 */
	public SwaggerProperty load(SwaggerPropertyId swaggerPropertyId) throws DataOperationException, SwaggerPropertyNotFoundException {
		SwaggerProperty swaggerProperty = this.swaggerPropertyDao.load(swaggerPropertyId);
		if (swaggerProperty == null) {
			throw new SwaggerPropertyNotFoundException(swaggerPropertyId);
		}
		return swaggerProperty;
	}

	/**
	 * Returns a list of {@link SwaggerProperty}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The SwaggerPropertyType to filter on, optional.
	 * @param status
	 *            The SwaggerPropertyStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link SwaggerProperty}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<SwaggerProperty> list(String search, SwaggerPropertyType type, SwaggerPropertyStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.swaggerPropertyDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link SwaggerProperty} with the given properties.
	 * 
	 * @param name
	 *            The name of the swaggerProperty, required.
	 * @param type
	 *            The type of swaggerProperty, required.
	 * @param status
	 *            The status of the swaggerProperty, required.
	 * @return The result of the creation, which will include the new swaggerProperty at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SwaggerProperty> create(String name, SwaggerPropertyType type, SwaggerPropertyStatus status) throws DataOperationException {
		SwaggerProperty swaggerProperty = new SwaggerProperty();
		swaggerProperty.setName(name);
		swaggerProperty.setType(type);
		swaggerProperty.setStatus(status);
		DataOperationResult<SwaggerProperty> result = save(swaggerProperty);
		return result;
	}

	/**
	 * Marks the entity as {@link SwaggerPropertyStatus#DELETED}.
	 * 
	 * @param swaggerPropertyId
	 *            The ID of the swaggerProperty to delete.
	 * @return The result of the deletion, will not include the new swaggerProperty at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SwaggerPropertyNotFoundException
	 *             If the ID specified did not match any swaggerPropertys.
	 */
	public DataOperationResult<SwaggerProperty> delete(SwaggerPropertyId swaggerPropertyId) throws DataOperationException, SwaggerPropertyNotFoundException {
		SwaggerProperty swaggerProperty = load(swaggerPropertyId);
		swaggerProperty.setStatus(SwaggerPropertyStatus.DELETED);
		DataOperationResult<SwaggerProperty> result = save(swaggerProperty);
		return result;
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
	 *            The swaggerProperty type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final SwaggerPropertyType type, final SwaggerPropertyStatus status) throws DataOperationException {
		return this.swaggerPropertyDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The SwaggerPropertyType to filter on, optional.
	 * @param status
	 *            The SwaggerPropertyStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, SwaggerPropertyType type, SwaggerPropertyStatus status) throws DataOperationException {
		return this.swaggerPropertyDao.searchCount(search, type, status);
	}

}
