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

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ajah.job.Job;
import com.ajah.job.JobStatus;
import com.ajah.job.data.JobManager;
import com.ajah.job.task.data.JobTaskManager;
import com.ajah.job.task.data.TaskManager;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * The JobDispatcher determines which {@link Job}s need to run, and configures
 * the proper {@link JobRunner} to execute it.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Service
@Slf4j
public class JobDispatcher implements ApplicationContextAware {

	@Autowired
	private JobManager jobManager;

	@Autowired
	private JobTaskManager jobTaskManager;

	@Autowired
	private TaskManager taskManager;

	@Setter
	private ApplicationContext applicationContext;

	@Scheduled(fixedDelay = 5000)
	public void poll() throws DataOperationException {
		log.debug("Polling jobs");
		final List<Job> jobs = this.jobManager.findRunnableJobs();
		log.debug(jobs.size() + " runnable jobs found.");
		for (final Job job : jobs) {
			run(job);
		}
	}

	/**
	 * Creates a JobRunner and executes it.
	 * 
	 * @param job
	 *            The job to execute.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	private void run(final Job job) throws DataOperationException {
		JobRunner jobRunner;
		switch (job.getExecutionStrategy()) {
		case SERIAL_ABORT:
			jobRunner = new SerialJobRunner(job, this.applicationContext, true);
			break;
		case SERIAL_CONTINUE:
			jobRunner = new SerialJobRunner(job, this.applicationContext, true);
			break;
		default:
			job.setStatus(JobStatus.ERROR);
			this.jobManager.save(job);
			return;
		}
		log.debug("Using " + jobRunner.getClass().getName());
		jobRunner.run();
	}

}
