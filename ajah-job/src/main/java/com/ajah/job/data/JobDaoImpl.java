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

import org.springframework.stereotype.Repository;

import com.ajah.job.Job;
import com.ajah.job.JobId;
import com.ajah.job.JobStatus;
import com.ajah.job.JobType;
import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * MySQL-based implementation of {@link JobDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class JobDaoImpl extends AbstractAjahDao<JobId, Job, Job> implements JobDao {

	/**
	 * @see com.ajah.job.data.JobDao#count(com.ajah.job.JobType,
	 *      com.ajah.job.JobStatus)
	 */
	@Override
	public long count(final JobType type, final JobStatus status) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.job.data.JobDao#findRunnableJobs()
	 */
	@Override
	public List<Job> findRunnableJobs() throws DataOperationException {
		return super.list("status=" + JobStatus.ACTIVE.getId() + " AND next_run_date <= (UNIX_TIMESTAMP()*1000) ORDER BY next_run_date");
	}

	@Override
	public List<Job> list(final JobType type, final JobStatus status, final long page, final long count) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("name", Order.ASC));
	}

}
