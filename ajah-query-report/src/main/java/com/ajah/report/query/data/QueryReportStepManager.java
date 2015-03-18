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

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.report.query.QueryReportStep;
import com.ajah.report.query.QueryReportStepId;
import com.ajah.report.query.QueryReportStepStatus;
import com.ajah.report.query.QueryReportStepType;

/**
 * Manages data operations for {@link QueryReportStep}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class QueryReportStepManager {

	@Autowired
	private QueryReportStepDao queryReportStepDao;

	/**
	 * Saves an {@link QueryReportStep}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param queryReportStep
	 *            The queryReportStep to save.
	 * @return The result of the save operation, which will include the new
	 *         queryReportStep at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportStep> save(QueryReportStep queryReportStep) throws DataOperationException {
		boolean create = false;
		if (queryReportStep.getId() == null) {
			queryReportStep.setId(new QueryReportStepId(UUID.randomUUID().toString()));
			create = true;
		}
		if (queryReportStep.getCreated() == null) {
			queryReportStep.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<QueryReportStep> result = this.queryReportStepDao.insert(queryReportStep);
			log.fine("Created QueryReportStep " + queryReportStep.getName() + " [" + queryReportStep.getId() + "]");
			return result;
		}
		DataOperationResult<QueryReportStep> result = this.queryReportStepDao.update(queryReportStep);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated QueryReportStep " + queryReportStep.getName() + " [" + queryReportStep.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link QueryReportStep} by it's ID.
	 * 
	 * @param queryReportStepId
	 *            The ID to load, required.
	 * @return The matching queryReportStep, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportStepNotFoundException
	 *             If the ID specified did not match any queryReportSteps.
	 */
	public QueryReportStep load(QueryReportStepId queryReportStepId) throws DataOperationException, QueryReportStepNotFoundException {
		QueryReportStep queryReportStep = this.queryReportStepDao.load(queryReportStepId);
		if (queryReportStep == null) {
			throw new QueryReportStepNotFoundException(queryReportStepId);
		}
		return queryReportStep;
	}

	/**
	 * Returns a list of {@link QueryReportStep}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The QueryReportStepType to filter on, optional.
	 * @param status
	 *            The QueryReportStepStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link QueryReportStep}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<QueryReportStep> list(String search, QueryReportStepType type, QueryReportStepStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.queryReportStepDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link QueryReportStep} with the given properties.
	 * 
	 * @param name
	 *            The name of the queryReportStep, required.
	 * @param type
	 *            The type of queryReportStep, required.
	 * @param status
	 *            The status of the queryReportStep, required.
	 * @return The result of the creation, which will include the new queryReportStep at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportStep> create(String name, QueryReportStepType type, QueryReportStepStatus status) throws DataOperationException {
		QueryReportStep queryReportStep = new QueryReportStep();
		queryReportStep.setName(name);
		queryReportStep.setType(type);
		queryReportStep.setStatus(status);
		DataOperationResult<QueryReportStep> result = save(queryReportStep);
		return result;
	}

	/**
	 * Marks the entity as {@link QueryReportStepStatus#DELETED}.
	 * 
	 * @param queryReportStepId
	 *            The ID of the queryReportStep to delete.
	 * @return The result of the deletion, will not include the new queryReportStep at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportStepNotFoundException
	 *             If the ID specified did not match any queryReportSteps.
	 */
	public DataOperationResult<QueryReportStep> delete(QueryReportStepId queryReportStepId) throws DataOperationException, QueryReportStepNotFoundException {
		QueryReportStep queryReportStep = load(queryReportStepId);
		queryReportStep.setStatus(QueryReportStepStatus.DELETED);
		DataOperationResult<QueryReportStep> result = save(queryReportStep);
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
	 *            The queryReportStep type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final QueryReportStepType type, final QueryReportStepStatus status) throws DataOperationException {
		return this.queryReportStepDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The QueryReportStepType to filter on, optional.
	 * @param status
	 *            The QueryReportStepStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, QueryReportStepType type, QueryReportStepStatus status) throws DataOperationException {
		return this.queryReportStepDao.searchCount(search, type, status);
	}

}
