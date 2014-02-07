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
package com.ajah.job.task.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.job.JobId;
import com.ajah.job.task.JobTask;
import com.ajah.job.task.JobTaskId;
import com.ajah.job.task.JobTaskStatus;
import com.ajah.job.task.JobTaskType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link JobTask}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class JobTaskManager {

	@Autowired
	private JobTaskDao jobTaskDao;

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
	 *            The jobTask type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final JobTaskType type, final JobTaskStatus status) throws DataOperationException {
		return this.jobTaskDao.count(type, status);
	}

	/**
	 * Creates a new {@link JobTask} with the given properties.
	 * 
	 * @param comment
	 *            The name of the jobTask, required.
	 * @param type
	 *            The type of jobTask, required.
	 * @param status
	 *            The status of the jobTask, required.
	 * @return The result of the creation, which will include the new jobTask at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<JobTask> create(final String comment, final JobTaskType type, final JobTaskStatus status) throws DataOperationException {
		final JobTask jobTask = new JobTask();
		jobTask.setComment(comment);
		jobTask.setType(type);
		jobTask.setStatus(status);
		final DataOperationResult<JobTask> result = save(jobTask);
		return result;
	}

	/**
	 * Marks the entity as {@link JobTaskStatus#DELETED}.
	 * 
	 * @param jobTaskId
	 *            The ID of the jobTask to delete.
	 * @return The result of the deletion, will not include the new jobTask at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws JobTaskNotFoundException
	 *             If the ID specified did not match any jobTasks.
	 */
	public DataOperationResult<JobTask> delete(final JobTaskId jobTaskId) throws DataOperationException, JobTaskNotFoundException {
		final JobTask jobTask = load(jobTaskId);
		jobTask.setStatus(JobTaskStatus.DELETED);
		final DataOperationResult<JobTask> result = save(jobTask);
		return result;
	}

	/**
	 * Returns a list of {@link JobTask}s that match the specified criteria.
	 * 
	 * @param jobId
	 *            The Job the task is a part of.
	 * @param type
	 *            The type of jobTask, optional.
	 * @param status
	 *            The status of the jobTask, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link JobTask}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<JobTask> list(final JobId jobId, final JobTaskType type, final JobTaskStatus status, final long page, final long count) throws DataOperationException {
		return this.jobTaskDao.list(jobId, type, status, page, count);
	}

	/**
	 * Loads an {@link JobTask} by it's ID.
	 * 
	 * @param jobTaskId
	 *            The ID to load, required.
	 * @return The matching jobTask, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws JobTaskNotFoundException
	 *             If the ID specified did not match any jobTasks.
	 */
	public JobTask load(final JobTaskId jobTaskId) throws DataOperationException, JobTaskNotFoundException {
		final JobTask jobTask = this.jobTaskDao.load(jobTaskId);
		if (jobTask == null) {
			throw new JobTaskNotFoundException(jobTaskId);
		}
		return jobTask;
	}

	/**
	 * Saves an {@link JobTask}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param jobTask
	 *            The jobTask to save.
	 * @return The result of the save operation, which will include the new
	 *         jobTask at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<JobTask> save(final JobTask jobTask) throws DataOperationException {
		boolean create = false;
		if (jobTask.getId() == null) {
			jobTask.setId(new JobTaskId(UUID.randomUUID().toString()));
			create = true;
		}
		if (jobTask.getCreated() == null) {
			jobTask.setCreated(new Date());
			jobTask.setModified(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<JobTask> result = this.jobTaskDao.insert(jobTask);
			log.fine("Created JobTask " + jobTask.getComment() + " [" + jobTask.getId() + "]");
			return result;
		}
		jobTask.setModified(new Date());
		final DataOperationResult<JobTask> result = this.jobTaskDao.update(jobTask);
		log.fine("Updated JobTask " + jobTask.getComment() + " [" + jobTask.getId() + "]");
		return result;
	}

}
