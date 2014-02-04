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

import java.util.List;

import com.ajah.job.Job;
import com.ajah.job.JobId;
import com.ajah.job.JobStatus;
import com.ajah.job.JobType;
import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * DAO interface for {@link Job}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface JobDao extends AjahDao<JobId, Job> {

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
	List<Job> list(final JobType type, final JobStatus status, final long page, final long count) throws DataOperationException;

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
	long count(final JobType type, final JobStatus status) throws DataOperationException;

	/**
	 * Find a list of runnable jobs based on the "nextRun" property.
	 * 
	 * @return A list of jobs, will not be null, may be empty
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<Job> findRunnableJobs() throws DataOperationException;

}
