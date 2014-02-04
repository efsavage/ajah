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

import java.util.List;

import com.ajah.job.JobId;
import com.ajah.job.task.JobTask;
import com.ajah.job.task.JobTaskId;
import com.ajah.job.task.JobTaskStatus;
import com.ajah.job.task.JobTaskType;
import com.ajah.job.task.Task;
import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * DAO interface for {@link JobTask}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface JobTaskDao extends AjahDao<JobTaskId, JobTask> {

	/**
	 * Returns a list of {@link Task}s that match the specified criteria.
	 * 
	 * @param jobId
	 *            The Job the task is a part of.
	 * @param type
	 *            The type of task, optional.
	 * @param status
	 *            The status of the task, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Task}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<JobTask> list(final JobId jobId, final JobTaskType type, final JobTaskStatus status, final long page, final long count) throws DataOperationException;

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
	long count(final JobTaskType type, final JobTaskStatus status) throws DataOperationException;

}
