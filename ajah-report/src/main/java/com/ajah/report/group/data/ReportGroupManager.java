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
package com.ajah.report.group.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.report.group.ReportGroup;
import com.ajah.report.group.ReportGroupId;
import com.ajah.report.group.ReportGroupStatus;
import com.ajah.report.group.ReportGroupType;

/**
 * Manages data operations for {@link ReportGroup}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class ReportGroupManager {

	@Autowired
	private ReportGroupDao reportGroupDao;

	/**
	 * Saves an {@link ReportGroup}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param reportGroup
	 *            The reportGroup to save.
	 * @return The result of the save operation, which will include the new
	 *         reportGroup at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<ReportGroup> save(ReportGroup reportGroup) throws DataOperationException {
		boolean create = false;
		if (reportGroup.getId() == null) {
			reportGroup.setId(new ReportGroupId(UUID.randomUUID().toString()));
			create = true;
		}
		if (reportGroup.getCreated() == null) {
			reportGroup.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<ReportGroup> result = this.reportGroupDao.insert(reportGroup);
			log.fine("Created ReportGroup " + reportGroup.getName() + " [" + reportGroup.getId() + "]");
			return result;
		}
		DataOperationResult<ReportGroup> result = this.reportGroupDao.update(reportGroup);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated ReportGroup " + reportGroup.getName() + " [" + reportGroup.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link ReportGroup} by it's ID.
	 * 
	 * @param reportGroupId
	 *            The ID to load, required.
	 * @return The matching reportGroup, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws ReportGroupNotFoundException
	 *             If the ID specified did not match any reportGroups.
	 */
	public ReportGroup load(ReportGroupId reportGroupId) throws DataOperationException, ReportGroupNotFoundException {
		ReportGroup reportGroup = this.reportGroupDao.load(reportGroupId);
		if (reportGroup == null) {
			throw new ReportGroupNotFoundException(reportGroupId);
		}
		return reportGroup;
	}

	/**
	 * Returns a list of {@link ReportGroup}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The ReportGroupType to filter on, optional.
	 * @param status
	 *            The ReportGroupStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link ReportGroup}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<ReportGroup> list(String search, ReportGroupType type, ReportGroupStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.reportGroupDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link ReportGroup} with the given properties.
	 * 
	 * @param name
	 *            The name of the reportGroup, required.
	 * @param type
	 *            The type of reportGroup, required.
	 * @param status
	 *            The status of the reportGroup, required.
	 * @return The result of the creation, which will include the new reportGroup at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<ReportGroup> create(String name, ReportGroupType type, ReportGroupStatus status) throws DataOperationException {
		ReportGroup reportGroup = new ReportGroup();
		reportGroup.setName(name);
		reportGroup.setType(type);
		reportGroup.setStatus(status);
		DataOperationResult<ReportGroup> result = save(reportGroup);
		return result;
	}

	/**
	 * Marks the entity as {@link ReportGroupStatus#DELETED}.
	 * 
	 * @param reportGroupId
	 *            The ID of the reportGroup to delete.
	 * @return The result of the deletion, will not include the new reportGroup at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws ReportGroupNotFoundException
	 *             If the ID specified did not match any reportGroups.
	 */
	public DataOperationResult<ReportGroup> delete(ReportGroupId reportGroupId) throws DataOperationException, ReportGroupNotFoundException {
		ReportGroup reportGroup = load(reportGroupId);
		reportGroup.setStatus(ReportGroupStatus.DELETED);
		DataOperationResult<ReportGroup> result = save(reportGroup);
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
	 *            The reportGroup type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final ReportGroupType type, final ReportGroupStatus status) throws DataOperationException {
		return this.reportGroupDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The ReportGroupType to filter on, optional.
	 * @param status
	 *            The ReportGroupStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, ReportGroupType type, ReportGroupStatus status) throws DataOperationException {
		return this.reportGroupDao.searchCount(search, type, status);
	}

}
