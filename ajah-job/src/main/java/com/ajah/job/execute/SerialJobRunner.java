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

import lombok.EqualsAndHashCode;

import org.springframework.context.ApplicationContext;

import com.ajah.job.Job;
import com.ajah.job.run.Run;
import com.ajah.job.run.data.RunManager;
import com.ajah.job.task.JobTask;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Runs a job one task after the other. If {@link #abortOnError} is false, will
 * continue to the next task if a task fails, otherwise will abort the sequence.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@EqualsAndHashCode(callSuper = true)
public class SerialJobRunner extends AbstractJobRunner {

	private final boolean abortOnError;

	private final boolean initialized = false;

	public SerialJobRunner(final Job job, final ApplicationContext applicationContext, final boolean abortOnError) {
		this.job = job;
		this.applicationContext = applicationContext;
		this.abortOnError = abortOnError;
	}

	public void init() {
		super.init(this.applicationContext);
	}

	/**
	 * Runs a job.
	 * 
	 * @throws DataOperationException
	 *             This is only thrown if there is a data error in configuring
	 *             or starting the run, not within the actual tasks themselves.
	 */
	@Override
	public void execute(Run run) throws DataOperationException {
		if (!this.initialized) {
			init();
		}
		RunManager runManager = this.applicationContext.getBean(RunManager.class);
		runManager.start(run);
		for (final JobTask jobTask : this.jobTasks) {
			try {
				jobTask.getTask().execute(run, jobTask, this.applicationContext);
			} catch (Throwable t) {
				run.error(t);
				if (this.abortOnError) {
					break;
				}
			}
		}
		runManager.end(run);
	}
}
