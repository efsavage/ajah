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
package com.ajah.job.execute;

import java.util.List;
import java.util.logging.Level;

import lombok.Data;
import lombok.extern.java.Log;

import org.springframework.context.ApplicationContext;

import com.ajah.job.Job;
import com.ajah.job.JobStatus;
import com.ajah.job.data.JobManager;
import com.ajah.job.task.JobTask;
import com.ajah.job.task.JobTaskStatus;
import com.ajah.job.task.Task;
import com.ajah.job.task.data.JobTaskManager;
import com.ajah.job.task.data.TaskManager;
import com.ajah.job.task.data.TaskNotFoundException;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * The most basic implementation of a JobRunner.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@Log
public abstract class AbstractJobRunner implements JobRunner {

	protected Job job;

	protected ApplicationContext applicationContext;

	protected JobManager jobManager;

	protected JobTaskManager jobTaskManager;

	protected TaskManager taskManager;

	protected List<JobTask> jobTasks;

	/**
	 * Sets up and validates the {@link Task}s for this job.
	 */
	public void init(final ApplicationContext _applicationContext) {

		this.jobManager = _applicationContext.getBean(JobManager.class);
		this.jobTaskManager = _applicationContext.getBean(JobTaskManager.class);
		this.taskManager = _applicationContext.getBean(TaskManager.class);

		try {
			this.jobTasks = this.jobTaskManager.list(this.job.getId(), null, JobTaskStatus.ACTIVE, 0, 1000);
			if (this.jobTasks.size() == 0) {
				log.warning("Deactivating job " + this.job.getId() + " because it has no active tasks");
				this.job.setStatus(JobStatus.INACTIVE);
				this.jobManager.save(this.job);
				return;
			}
			for (final JobTask jobTask : this.jobTasks) {
				final Task task = this.taskManager.load(jobTask.getTaskId());
				jobTask.setTask(task);
			}
		} catch (DataOperationException | RuntimeException | TaskNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
