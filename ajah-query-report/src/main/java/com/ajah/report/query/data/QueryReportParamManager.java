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

import com.ajah.report.query.QueryReportParam;
import com.ajah.report.query.QueryReportParamId;
import com.ajah.report.query.QueryReportParamStatus;
import com.ajah.report.query.QueryReportParamType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link QueryReportParam}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class QueryReportParamManager {

	@Autowired
	private QueryReportParamDao queryReportParamDao;

	/**
	 * Saves an {@link QueryReportParam}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param queryReportParam
	 *            The queryReportParam to save.
	 * @return The result of the save operation, which will include the new
	 *         queryReportParam at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportParam> save(QueryReportParam queryReportParam) throws DataOperationException {
		boolean create = false;
		if (queryReportParam.getId() == null) {
			queryReportParam.setId(new QueryReportParamId(UUID.randomUUID().toString()));
			create = true;
		}
		if (queryReportParam.getCreated() == null) {
			queryReportParam.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<QueryReportParam> result = this.queryReportParamDao.insert(queryReportParam);
			log.fine("Created QueryReportParam " + queryReportParam.getName() + " [" + queryReportParam.getId() + "]");
			return result;
		}
		DataOperationResult<QueryReportParam> result = this.queryReportParamDao.update(queryReportParam);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated QueryReportParam " + queryReportParam.getName() + " [" + queryReportParam.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link QueryReportParam} by it's ID.
	 * 
	 * @param queryReportParamId
	 *            The ID to load, required.
	 * @return The matching queryReportParam, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportParamNotFoundException
	 *             If the ID specified did not match any queryReportParams.
	 */
	public QueryReportParam load(QueryReportParamId queryReportParamId) throws DataOperationException, QueryReportParamNotFoundException {
		QueryReportParam queryReportParam = this.queryReportParamDao.load(queryReportParamId);
		if (queryReportParam == null) {
			throw new QueryReportParamNotFoundException(queryReportParamId);
		}
		return queryReportParam;
	}

	/**
	 * Returns a list of {@link QueryReportParam}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The QueryReportParamType to filter on, optional.
	 * @param status
	 *            The QueryReportParamStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link QueryReportParam}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<QueryReportParam> list(String search, QueryReportParamType type, QueryReportParamStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.queryReportParamDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link QueryReportParam} with the given properties.
	 * 
	 * @param name
	 *            The name of the queryReportParam, required.
	 * @param type
	 *            The type of queryReportParam, required.
	 * @param status
	 *            The status of the queryReportParam, required.
	 * @return The result of the creation, which will include the new queryReportParam at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportParam> create(String name, QueryReportParamType type, QueryReportParamStatus status) throws DataOperationException {
		QueryReportParam queryReportParam = new QueryReportParam();
		queryReportParam.setName(name);
		queryReportParam.setType(type);
		queryReportParam.setStatus(status);
		DataOperationResult<QueryReportParam> result = save(queryReportParam);
		return result;
	}

	/**
	 * Marks the entity as {@link QueryReportParamStatus#DELETED}.
	 * 
	 * @param queryReportParamId
	 *            The ID of the queryReportParam to delete.
	 * @return The result of the deletion, will not include the new queryReportParam at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportParamNotFoundException
	 *             If the ID specified did not match any queryReportParams.
	 */
	public DataOperationResult<QueryReportParam> delete(QueryReportParamId queryReportParamId) throws DataOperationException, QueryReportParamNotFoundException {
		QueryReportParam queryReportParam = load(queryReportParamId);
		queryReportParam.setStatus(QueryReportParamStatus.DELETED);
		DataOperationResult<QueryReportParam> result = save(queryReportParam);
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
	 *            The queryReportParam type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final QueryReportParamType type, final QueryReportParamStatus status) throws DataOperationException {
		return this.queryReportParamDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The QueryReportParamType to filter on, optional.
	 * @param status
	 *            The QueryReportParamStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, QueryReportParamType type, QueryReportParamStatus status) throws DataOperationException {
		return this.queryReportParamDao.searchCount(search, type, status);
	}

}
