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

import com.ajah.report.query.QueryReport;
import com.ajah.report.query.QueryReportId;
import com.ajah.report.query.QueryReportStatus;
import com.ajah.report.query.QueryReportType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link QueryReport}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class QueryReportManager {

	@Autowired
	private QueryReportDao queryReportDao;

	/**
	 * Saves an {@link QueryReport}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param queryReport
	 *            The queryReport to save.
	 * @return The result of the save operation, which will include the new
	 *         queryReport at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReport> save(QueryReport queryReport) throws DataOperationException {
		boolean create = false;
		if (queryReport.getId() == null) {
			queryReport.setId(new QueryReportId(UUID.randomUUID().toString()));
			create = true;
		}
		if (queryReport.getCreated() == null) {
			queryReport.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<QueryReport> result = this.queryReportDao.insert(queryReport);
			log.fine("Created QueryReport " + queryReport.getName() + " [" + queryReport.getId() + "]");
			return result;
		}
		DataOperationResult<QueryReport> result = this.queryReportDao.update(queryReport);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated QueryReport " + queryReport.getName() + " [" + queryReport.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link QueryReport} by it's ID.
	 * 
	 * @param queryReportId
	 *            The ID to load, required.
	 * @return The matching queryReport, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportNotFoundException
	 *             If the ID specified did not match any queryReports.
	 */
	public QueryReport load(QueryReportId queryReportId) throws DataOperationException, QueryReportNotFoundException {
		QueryReport queryReport = this.queryReportDao.load(queryReportId);
		if (queryReport == null) {
			throw new QueryReportNotFoundException(queryReportId);
		}
		return queryReport;
	}

	/**
	 * Returns a list of {@link QueryReport}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The QueryReportType to filter on, optional.
	 * @param status
	 *            The QueryReportStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link QueryReport}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<QueryReport> list(String search, QueryReportType type, QueryReportStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.queryReportDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link QueryReport} with the given properties.
	 * 
	 * @param name
	 *            The name of the queryReport, required.
	 * @param type
	 *            The type of queryReport, required.
	 * @param status
	 *            The status of the queryReport, required.
	 * @return The result of the creation, which will include the new queryReport at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<QueryReport> create(String name, QueryReportType type, QueryReportStatus status) throws DataOperationException {
		QueryReport queryReport = new QueryReport();
		queryReport.setName(name);
		queryReport.setType(type);
		queryReport.setStatus(status);
		return save(queryReport);
	}

	/**
	 * Marks the entity as {@link QueryReportStatus#DELETED}.
	 * 
	 * @param queryReportId
	 *            The ID of the queryReport to delete.
	 * @return The result of the deletion, will not include the new queryReport at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws QueryReportNotFoundException
	 *             If the ID specified did not match any queryReports.
	 */
	public DataOperationResult<QueryReport> delete(QueryReportId queryReportId) throws DataOperationException, QueryReportNotFoundException {
		QueryReport queryReport = load(queryReportId);
		queryReport.setStatus(QueryReportStatus.DELETED);
		return save(queryReport);
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
	 *            The queryReport type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final QueryReportType type, final QueryReportStatus status) throws DataOperationException {
		return this.queryReportDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The QueryReportType to filter on, optional.
	 * @param status
	 *            The QueryReportStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, QueryReportType type, QueryReportStatus status) throws DataOperationException {
		return this.queryReportDao.searchCount(search, type, status);
	}

}
