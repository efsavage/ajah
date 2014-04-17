/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.job.Job;
import com.ajah.job.run.Run;
import com.ajah.job.run.RunId;
import com.ajah.job.run.RunStatus;
import com.ajah.job.run.RunType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link Run}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
public class RunManager {

	@Autowired
	private RunDao runDao;

	@Autowired
	private RunMessageManager runMessageManager;

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
	 *            The run type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final RunType type, final RunStatus status) throws DataOperationException {
		return this.runDao.count(type, status);
	}

	/**
	 * Creates a new {@link Run} with the given properties.
	 * 
	 * @param name
	 *            The name of the run, required.
	 * @param type
	 *            The type of run, required.
	 * @param status
	 *            The status of the run, required.
	 * @return The result of the creation, which will include the new run at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */

	/**
	 * Marks the entity as {@link RunStatus#DELETED}.
	 * 
	 * @param runId
	 *            The ID of the run to delete.
	 * @return The result of the deletion, will not include the new run at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RunNotFoundException
	 *             If the ID specified did not match any runs.
	 */
	public DataOperationResult<Run> delete(final RunId runId) throws DataOperationException, RunNotFoundException {
		final Run run = load(runId);
		run.setStatus(RunStatus.DELETED);
		final DataOperationResult<Run> result = save(run);
		return result;
	}

	/**
	 * Returns a list of {@link Run}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of run, optional.
	 * @param status
	 *            The status of the run, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Run}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Run> list(final RunType type, final RunStatus status, final long page, final long count) throws DataOperationException {
		return this.runDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link Run} by it's ID.
	 * 
	 * @param runId
	 *            The ID to load, required.
	 * @return The matching run, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RunNotFoundException
	 *             If the ID specified did not match any runs.
	 */
	public Run load(final RunId runId) throws DataOperationException, RunNotFoundException {
		final Run run = this.runDao.load(runId);
		if (run == null) {
			throw new RunNotFoundException(runId);
		}
		return run;
	}

	/**
	 * Saves an {@link Run}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param run
	 *            The run to save.
	 * @return The result of the save operation, which will include the new run
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Run> save(final Run run) throws DataOperationException {
		boolean create = false;
		if (run.getId() == null) {
			run.setId(new RunId(UUID.randomUUID().toString()));
			create = true;
		}
		if (run.getCreated() == null) {
			run.setCreated(new Date());
			create = true;
		}
		if (create) {
			return this.runDao.insert(run);
		}
		final DataOperationResult<Run> result = this.runDao.update(run);
		return result;
	}

	public Run create(Job job, RunType type) throws DataOperationException {
		Run run = new Run();
		run.setJobId(job.getId());
		run.setStatus(RunStatus.NEW);
		run.setType(type);
		run.setRunMessageManager(this.runMessageManager);
		return save(run).getEntity();
	}

	public DataOperationResult<Run> start(Run run) throws DataOperationException {
		run.setStatus(RunStatus.ACTIVE);
		run.setStart(new Date());
		return save(run);
	}

	public DataOperationResult<Run> complete(Run run) throws DataOperationException {
		run.setStatus(RunStatus.COMPLETED);
		run.setEnd(new Date());
		return save(run);
	}

}
