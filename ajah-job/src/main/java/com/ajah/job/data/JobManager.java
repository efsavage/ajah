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
package com.ajah.job.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.job.Job;
import com.ajah.job.JobId;
import com.ajah.job.JobStatus;
import com.ajah.job.JobType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link Job}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class JobManager {

	@Autowired
	private JobDao jobDao;

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
	 *            The job type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final JobType type, final JobStatus status) throws DataOperationException {
		return this.jobDao.count(type, status);
	}

	/**
	 * Creates a new {@link Job} with the given properties.
	 * 
	 * @param name
	 *            The name of the job, required.
	 * @param type
	 *            The type of job, required.
	 * @param status
	 *            The status of the job, required.
	 * @return The result of the creation, which will include the new job at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Job> create(final String name, final JobType type, final JobStatus status) throws DataOperationException {
		final Job job = new Job();
		job.setName(name);
		job.setType(type);
		job.setStatus(status);
		final DataOperationResult<Job> result = save(job);
		return result;
	}

	/**
	 * Marks the entity as {@link JobStatus#DELETED}.
	 * 
	 * @param jobId
	 *            The ID of the job to delete.
	 * @return The result of the deletion, will not include the new job at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws JobNotFoundException
	 *             If the ID specified did not match any jobs.
	 */
	public DataOperationResult<Job> delete(final JobId jobId) throws DataOperationException, JobNotFoundException {
		final Job job = load(jobId);
		job.setStatus(JobStatus.DELETED);
		final DataOperationResult<Job> result = save(job);
		return result;
	}

	/**
	 * Find a list of runnable jobs based on the "nextRun" property.
	 * 
	 * @return A list of jobs, will not be null, may be empty
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Job> findRunnableJobs() throws DataOperationException {
		return this.jobDao.findRunnableJobs();
	}

	/**
	 * Returns a list of {@link Job}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of job, optional.
	 * @param status
	 *            The status of the job, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Job}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Job> list(final JobType type, final JobStatus status, final long page, final long count) throws DataOperationException {
		return this.jobDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link Job} by it's ID.
	 * 
	 * @param jobId
	 *            The ID to load, required.
	 * @return The matching job, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws JobNotFoundException
	 *             If the ID specified did not match any jobs.
	 */
	public Job load(final JobId jobId) throws DataOperationException, JobNotFoundException {
		final Job job = this.jobDao.load(jobId);
		if (job == null) {
			throw new JobNotFoundException(jobId);
		}
		return job;
	}

	/**
	 * Saves an {@link Job}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param job
	 *            The job to save.
	 * @return The result of the save operation, which will include the new job
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Job> save(final Job job) throws DataOperationException {
		boolean create = false;
		if (job.getId() == null) {
			job.setId(new JobId(UUID.randomUUID().toString()));
			create = true;
		}
		if (job.getCreated() == null) {
			job.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<Job> result = this.jobDao.insert(job);
			log.fine("Created Job " + job.getName() + " [" + job.getId() + "]");
			return result;
		}
		final DataOperationResult<Job> result = this.jobDao.update(job);
		log.fine("Updated Job " + job.getName() + " [" + job.getId() + "]");
		return result;
	}

}
