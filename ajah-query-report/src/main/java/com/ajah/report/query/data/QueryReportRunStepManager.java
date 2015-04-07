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

import com.ajah.report.query.QueryReportRunId;
import com.ajah.report.query.QueryReportRunStep;
import com.ajah.report.query.QueryReportRunStepId;
import com.ajah.report.query.QueryReportRunStepStatus;
import com.ajah.report.query.QueryReportRunStepType;
import com.ajah.report.query.QueryReportStepId;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link QueryReportRunStep}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class QueryReportRunStepManager {

	@Autowired
	private QueryReportRunStepDao queryReportRunStepDao;

	/**
	 * Saves an {@link QueryReportRunStep}. Assigns a new ID ({@link UUID}) and
	 * sets the creation date if necessary. If either of these elements are set,
	 * will perform an insert. Otherwise will perform an update.
	 * 
	 * @param queryReportRunStep
	 *            The queryReportRunStep to save.
	 * @return The result of the save operation, which will include the new
	 *         queryReportRunStep at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportRunStep> save(QueryReportRunStep queryReportRunStep) throws DataOperationException {
		boolean create = false;
		if (queryReportRunStep.getId() == null) {
			queryReportRunStep.setId(new QueryReportRunStepId(UUID.randomUUID().toString()));
			create = true;
		}
		if (queryReportRunStep.getCreated() == null) {
			queryReportRunStep.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<QueryReportRunStep> result = this.queryReportRunStepDao.insert(queryReportRunStep);
			log.fine("Created QueryReportRunStep " + queryReportRunStep.getName() + " [" + queryReportRunStep.getId() + "]");
			return result;
		}
		DataOperationResult<QueryReportRunStep> result = this.queryReportRunStepDao.update(queryReportRunStep);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated QueryReportRunStep " + queryReportRunStep.getName() + " [" + queryReportRunStep.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link QueryReportRunStep} by it's ID.
	 * 
	 * @param queryReportRunStepId
	 *            The ID to load, required.
	 * @return The matching queryReportRunStep, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportRunStepNotFoundException
	 *             If the ID specified did not match any queryReportRunSteps.
	 */
	public QueryReportRunStep load(QueryReportRunStepId queryReportRunStepId) throws DataOperationException, QueryReportRunStepNotFoundException {
		QueryReportRunStep queryReportRunStep = this.queryReportRunStepDao.load(queryReportRunStepId);
		if (queryReportRunStep == null) {
			throw new QueryReportRunStepNotFoundException(queryReportRunStepId);
		}
		return queryReportRunStep;
	}

	/**
	 * Returns a list of {@link QueryReportRunStep}s that match the specified
	 * criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The QueryReportRunStepType to filter on, optional.
	 * @param status
	 *            The QueryReportRunStepStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link QueryReportRunStep}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<QueryReportRunStep> list(String search, QueryReportRunStepType type, QueryReportRunStepStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.queryReportRunStepDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link QueryReportRunStep} with the given properties.
	 * @param  
	 * 
	 * @param name
	 *            The name of the queryReportRunStep, required.
	 * @param type
	 *            The type of queryReportRunStep, required.
	 * @param status
	 *            The status of the queryReportRunStep, required.
	 * @return The result of the creation, which will include the new
	 *         queryReportRunStep at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportRunStep> create(QueryReportRunId queryReportRunId, QueryReportStepId queryReportStepId, int sequence, String name, QueryReportRunStepType type,
			QueryReportRunStepStatus status) throws DataOperationException {
		QueryReportRunStep queryReportRunStep = new QueryReportRunStep();
		queryReportRunStep.setQueryReportRunId(queryReportRunId);
		queryReportRunStep.setQueryReportStepId(queryReportStepId);
		queryReportRunStep.setSequence(sequence);
		queryReportRunStep.setName(name);
		queryReportRunStep.setType(type);
		queryReportRunStep.setStatus(status);
		DataOperationResult<QueryReportRunStep> result = save(queryReportRunStep);
		return result;
	}

	/**
	 * Marks the entity as {@link QueryReportRunStepStatus#DELETED}.
	 * 
	 * @param queryReportRunStepId
	 *            The ID of the queryReportRunStep to delete.
	 * @return The result of the deletion, will not include the new
	 *         queryReportRunStep at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportRunStepNotFoundException
	 *             If the ID specified did not match any queryReportRunSteps.
	 */
	public DataOperationResult<QueryReportRunStep> delete(QueryReportRunStepId queryReportRunStepId) throws DataOperationException, QueryReportRunStepNotFoundException {
		QueryReportRunStep queryReportRunStep = load(queryReportRunStepId);
		queryReportRunStep.setStatus(QueryReportRunStepStatus.DELETED);
		DataOperationResult<QueryReportRunStep> result = save(queryReportRunStep);
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
	 *            The queryReportRunStep type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final QueryReportRunStepType type, final QueryReportRunStepStatus status) throws DataOperationException {
		return this.queryReportRunStepDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The QueryReportRunStepType to filter on, optional.
	 * @param status
	 *            The QueryReportRunStepStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, QueryReportRunStepType type, QueryReportRunStepStatus status) throws DataOperationException {
		return this.queryReportRunStepDao.searchCount(search, type, status);
	}

}
