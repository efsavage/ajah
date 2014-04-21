/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.job.run.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.job.Job;
import com.ajah.job.run.RunId;
import com.ajah.job.run.RunMetric;
import com.ajah.job.run.RunMetricId;
import com.ajah.job.run.RunMetricStatus;
import com.ajah.job.run.RunMetricType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.vigilanced.client.VigilancedClient;

/**
 * Manages data operations for {@link RunMetric}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Slf4j
public class RunMetricManager {

	@Autowired
	private RunMetricDao runMetricDao;

	@Autowired(required = false)
	private VigilancedClient vigilancedClient;

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count() throws DataOperationException {
		return count(null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The runMetric type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final RunMetricType type, final RunMetricStatus status) throws DataOperationException {
		return this.runMetricDao.count(type, status);
	}

	/**
	 * Creates a new {@link RunMetric} with the given properties.
	 * 
	 * @param name
	 *            The name of the runMetric, required.
	 * @param type
	 *            The type of runMetric, required.
	 * @param status
	 *            The status of the runMetric, required.
	 * @return The result of the creation, which will include the new runMetric
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<RunMetric> create(final String name, final BigDecimal value, final RunId runId, final Job job, final boolean external) throws DataOperationException {
		final RunMetric runMetric = new RunMetric();
		runMetric.setRunId(runId);
		runMetric.setJobId(job.getId());
		runMetric.setName(name);
		runMetric.setType(RunMetricType.STANDARD);
		runMetric.setStatus(RunMetricStatus.ACTIVE);
		final DataOperationResult<RunMetric> result = save(runMetric);
		if (external) {
			if (this.vigilancedClient != null) {
				this.vigilancedClient.updateMetric(job.getMonitorKey(), name, value, null);
			} else {
				log.debug("No Vigilanced client is configured");
			}
		}
		return result;
	}

	/**
	 * Marks the entity as {@link RunMetricStatus#DELETED}.
	 * 
	 * @param runMetricId
	 *            The ID of the runMetric to delete.
	 * @return The result of the deletion, will not include the new runMetric at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RunMetricNotFoundException
	 *             If the ID specified did not match any runMetrics.
	 */
	public DataOperationResult<RunMetric> delete(final RunMetricId runMetricId) throws DataOperationException, RunMetricNotFoundException {
		final RunMetric runMetric = load(runMetricId);
		runMetric.setStatus(RunMetricStatus.DELETED);
		final DataOperationResult<RunMetric> result = save(runMetric);
		return result;
	}

	/**
	 * Returns a list of {@link RunMetric}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of runMetric, optional.
	 * @param status
	 *            The status of the runMetric, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link RunMetric}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<RunMetric> list(final RunMetricType type, final RunMetricStatus status, final long page, final long count) throws DataOperationException {
		return this.runMetricDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link RunMetric} by it's ID.
	 * 
	 * @param runMetricId
	 *            The ID to load, required.
	 * @return The matching runMetric, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RunMetricNotFoundException
	 *             If the ID specified did not match any runMetrics.
	 */
	public RunMetric load(final RunMetricId runMetricId) throws DataOperationException, RunMetricNotFoundException {
		final RunMetric runMetric = this.runMetricDao.load(runMetricId);
		if (runMetric == null) {
			throw new RunMetricNotFoundException(runMetricId);
		}
		return runMetric;
	}

	/**
	 * Saves an {@link RunMetric}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param runMetric
	 *            The runMetric to save.
	 * @return The result of the save operation, which will include the new
	 *         runMetric at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<RunMetric> save(final RunMetric runMetric) throws DataOperationException {
		boolean create = false;
		if (runMetric.getId() == null) {
			runMetric.setId(new RunMetricId(UUID.randomUUID().toString()));
			create = true;
		}
		if (runMetric.getCreated() == null) {
			runMetric.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<RunMetric> result = this.runMetricDao.insert(runMetric);
			log.debug("Created RunMetric " + runMetric.getName() + " [" + runMetric.getId() + "]");
			return result;
		}
		final DataOperationResult<RunMetric> result = this.runMetricDao.update(runMetric);
		log.debug("Updated RunMetric " + runMetric.getName() + " [" + runMetric.getId() + "]");
		return result;
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(final String search) throws DataOperationException {
		return this.runMetricDao.searchCount(search);
	}

}
