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

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.report.query.QueryReportId;
import com.ajah.report.query.QueryReportRun;
import com.ajah.report.query.QueryReportRunId;
import com.ajah.report.query.QueryReportRunStatus;
import com.ajah.report.query.QueryReportRunType;
import com.ajah.report.query.QueryReportRunner;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link QueryReportRun}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class QueryReportRunManager {

	@Autowired
	private QueryReportRunDao queryReportRunDao;

	@Autowired
	private QueryReportRunner queryReportRunner;

	/**
	 * Saves an {@link QueryReportRun}. Assigns a new ID ({@link UUID}) and sets
	 * the creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param queryReportRun
	 *            The queryReportRun to save.
	 * @return The result of the save operation, which will include the new
	 *         queryReportRun at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReportRun> save(QueryReportRun queryReportRun) throws DataOperationException {
		boolean create = false;
		if (queryReportRun.getId() == null) {
			queryReportRun.setId(new QueryReportRunId(UUID.randomUUID().toString()));
			create = true;
		}
		if (queryReportRun.getCreated() == null) {
			queryReportRun.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<QueryReportRun> result = this.queryReportRunDao.insert(queryReportRun);
			log.fine("Created QueryReportRun " + queryReportRun.getName() + " [" + queryReportRun.getId() + "]");
			return result;
		}
		DataOperationResult<QueryReportRun> result = this.queryReportRunDao.update(queryReportRun);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated QueryReportRun " + queryReportRun.getName() + " [" + queryReportRun.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link QueryReportRun} by it's ID.
	 * 
	 * @param queryReportRunId
	 *            The ID to load, required.
	 * @return The matching queryReportRun, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportRunNotFoundException
	 *             If the ID specified did not match any queryReportRuns.
	 */
	public QueryReportRun load(QueryReportRunId queryReportRunId) throws DataOperationException, QueryReportRunNotFoundException {
		QueryReportRun queryReportRun = this.queryReportRunDao.load(queryReportRunId);
		if (queryReportRun == null) {
			throw new QueryReportRunNotFoundException(queryReportRunId);
		}
		return queryReportRun;
	}

	/**
	 * Returns a list of {@link QueryReportRun}s that match the specified
	 * criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The QueryReportRunType to filter on, optional.
	 * @param status
	 *            The QueryReportRunStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link QueryReportRun}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<QueryReportRun> list(String search, QueryReportRunType type, QueryReportRunStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.queryReportRunDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link QueryReportRun} with the given properties.
	 * 
	 * @param queryReportId
	 * 
	 * @param name
	 *            The name of the queryReportRun, required.
	 * @param type
	 *            The type of queryReportRun, required.
	 * @param status
	 *            The status of the queryReportRun, required.
	 * @return The result of the creation, which will include the new
	 *         queryReportRun at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws IOException
	 *             If the file could not be read.
	 */
	public DataOperationResult<QueryReportRun> create(QueryReportId queryReportId, String name, QueryReportRunType type, QueryReportRunStatus status) throws DataOperationException, IOException {
		QueryReportRun queryReportRun = new QueryReportRun();
		queryReportRun.setQueryReportId(queryReportId);
		queryReportRun.setName(name);
		queryReportRun.setType(type);
		queryReportRun.setStatus(status);
		DataOperationResult<QueryReportRun> result = save(queryReportRun);
		return result;
	}

	/**
	 * Marks the entity as {@link QueryReportRunStatus#DELETED}.
	 * 
	 * @param queryReportRunId
	 *            The ID of the queryReportRun to delete.
	 * @return The result of the deletion, will not include the new
	 *         queryReportRun at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportRunNotFoundException
	 *             If the ID specified did not match any queryReportRuns.
	 */
	public DataOperationResult<QueryReportRun> delete(QueryReportRunId queryReportRunId) throws DataOperationException, QueryReportRunNotFoundException {
		QueryReportRun queryReportRun = load(queryReportRunId);
		queryReportRun.setStatus(QueryReportRunStatus.DELETED);
		DataOperationResult<QueryReportRun> result = save(queryReportRun);
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
	 *            The queryReportRun type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final QueryReportRunType type, final QueryReportRunStatus status) throws DataOperationException {
		return this.queryReportRunDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The QueryReportRunType to filter on, optional.
	 * @param status
	 *            The QueryReportRunStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, QueryReportRunType type, QueryReportRunStatus status) throws DataOperationException {
		return this.queryReportRunDao.searchCount(search, type, status);
	}

	/**
	 * Executes a report and creates a run.
	 * 
	 * @param queryReportId
	 *            The report to run.
	 * @return The results of the run.
	 * @throws QueryReportNotFoundException
	 * @throws DataOperationException
	 * @throws IOException
	 */
	public QueryReportRun run(QueryReportId queryReportId) throws IOException, DataOperationException, QueryReportNotFoundException {
		return this.queryReportRunner.run(queryReportId);
	}

}
