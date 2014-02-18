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

import org.springframework.stereotype.Repository;

import com.ajah.job.JobId;
import com.ajah.job.task.JobTask;
import com.ajah.job.task.JobTaskId;
import com.ajah.job.task.JobTaskStatus;
import com.ajah.job.task.JobTaskType;
import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * MySQL-based implementation of {@link JobTaskDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class JobTaskDaoImpl extends AbstractAjahDao<JobTaskId, JobTask, JobTask> implements JobTaskDao {

	/**
	 * @see com.ajah.job.task.data.JobTaskDao#count(com.ajah.job.task.JobTaskType,
	 *      com.ajah.job.task.JobTaskStatus)
	 */
	@Override
	public long count(final JobTaskType type, final JobTaskStatus status) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	@Override
	public List<JobTask> list(final JobId jobId, final JobTaskType type, final JobTaskStatus status, final long page, final long count) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (jobId != null) {
			criteria.eq(jobId);
		}
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("sequence", Order.ASC));
	}
}
