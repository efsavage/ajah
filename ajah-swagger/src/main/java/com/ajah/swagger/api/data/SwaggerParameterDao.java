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

import java.util.List;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.swagger.api.SwaggerOperationId;
import com.ajah.swagger.api.SwaggerParameter;
import com.ajah.swagger.api.SwaggerParameterId;
import com.ajah.swagger.api.SwaggerParameterStatus;
import com.ajah.swagger.api.SwaggerParameterType;

/**
 * DAO interface for {@link SwaggerParameter}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface SwaggerParameterDao extends AjahDao<SwaggerParameterId, SwaggerParameter> {

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
	List<SwaggerParameter> list(SwaggerOperationId swaggerOperationId, String search, SwaggerParameterType type, SwaggerParameterStatus status, String sort, Order order, int page, int count)
			throws DataOperationException;

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
	int count(SwaggerOperationId swaggerOperationId, SwaggerParameterType type, SwaggerParameterStatus status) throws DataOperationException;

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
	int searchCount(SwaggerOperationId swaggerOperationId, String search, SwaggerParameterType type, SwaggerParameterStatus status) throws DataOperationException;

}
