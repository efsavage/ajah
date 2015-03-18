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
package com.ajah.report.query.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.report.query.QueryReportRunParam;
import com.ajah.report.query.QueryReportRunParamId;
import com.ajah.report.query.QueryReportRunParamStatus;
import com.ajah.report.query.QueryReportRunParamType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link QueryReportRunParam}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class QueryReportRunParamManager {

	@Autowired
	private QueryReportRunParamDao queryReportRunParamDao;

	/**
	 * Saves an {@link QueryReportRunParam}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param queryReportRunParam
	 *            The queryReportRunParam to save.
	 * @return The result of the save operation, which will include the new
	 *         queryReportRunParam at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportRunParam> save(QueryReportRunParam queryReportRunParam) throws DataOperationException {
		boolean create = false;
		if (queryReportRunParam.getId() == null) {
			queryReportRunParam.setId(new QueryReportRunParamId(UUID.randomUUID().toString()));
			create = true;
		}
		if (queryReportRunParam.getCreated() == null) {
			queryReportRunParam.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<QueryReportRunParam> result = this.queryReportRunParamDao.insert(queryReportRunParam);
			log.fine("Created QueryReportRunParam " + queryReportRunParam.getName() + " [" + queryReportRunParam.getId() + "]");
			return result;
		}
		DataOperationResult<QueryReportRunParam> result = this.queryReportRunParamDao.update(queryReportRunParam);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated QueryReportRunParam " + queryReportRunParam.getName() + " [" + queryReportRunParam.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link QueryReportRunParam} by it's ID.
	 * 
	 * @param queryReportRunParamId
	 *            The ID to load, required.
	 * @return The matching queryReportRunParam, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportRunParamNotFoundException
	 *             If the ID specified did not match any queryReportRunParams.
	 */
	public QueryReportRunParam load(QueryReportRunParamId queryReportRunParamId) throws DataOperationException, QueryReportRunParamNotFoundException {
		QueryReportRunParam queryReportRunParam = this.queryReportRunParamDao.load(queryReportRunParamId);
		if (queryReportRunParam == null) {
			throw new QueryReportRunParamNotFoundException(queryReportRunParamId);
		}
		return queryReportRunParam;
	}

	/**
	 * Returns a list of {@link QueryReportRunParam}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The QueryReportRunParamType to filter on, optional.
	 * @param status
	 *            The QueryReportRunParamStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link QueryReportRunParam}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<QueryReportRunParam> list(String search, QueryReportRunParamType type, QueryReportRunParamStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.queryReportRunParamDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link QueryReportRunParam} with the given properties.
	 * 
	 * @param name
	 *            The name of the queryReportRunParam, required.
	 * @param type
	 *            The type of queryReportRunParam, required.
	 * @param status
	 *            The status of the queryReportRunParam, required.
	 * @return The result of the creation, which will include the new queryReportRunParam at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportRunParam> create(String name, QueryReportRunParamType type, QueryReportRunParamStatus status) throws DataOperationException {
		QueryReportRunParam queryReportRunParam = new QueryReportRunParam();
		queryReportRunParam.setName(name);
		queryReportRunParam.setType(type);
		queryReportRunParam.setStatus(status);
		DataOperationResult<QueryReportRunParam> result = save(queryReportRunParam);
		return result;
	}

	/**
	 * Marks the entity as {@link QueryReportRunParamStatus#DELETED}.
	 * 
	 * @param queryReportRunParamId
	 *            The ID of the queryReportRunParam to delete.
	 * @return The result of the deletion, will not include the new queryReportRunParam at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportRunParamNotFoundException
	 *             If the ID specified did not match any queryReportRunParams.
	 */
	public DataOperationResult<QueryReportRunParam> delete(QueryReportRunParamId queryReportRunParamId) throws DataOperationException, QueryReportRunParamNotFoundException {
		QueryReportRunParam queryReportRunParam = load(queryReportRunParamId);
		queryReportRunParam.setStatus(QueryReportRunParamStatus.DELETED);
		DataOperationResult<QueryReportRunParam> result = save(queryReportRunParam);
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
	 *            The queryReportRunParam type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final QueryReportRunParamType type, final QueryReportRunParamStatus status) throws DataOperationException {
		return this.queryReportRunParamDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The QueryReportRunParamType to filter on, optional.
	 * @param status
	 *            The QueryReportRunParamStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, QueryReportRunParamType type, QueryReportRunParamStatus status) throws DataOperationException {
		return this.queryReportRunParamDao.searchCount(search, type, status);
	}

}
