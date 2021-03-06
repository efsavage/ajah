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
package com.ajah.job.task;

import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.ajah.job.run.Run;

/**
 * Simplest concrete implementation of {@link AjahTask}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public abstract class SimpleAjahTask implements AjahTask {

	protected ApplicationContext applicationContext;
	protected Date start;
	protected Date lastActivity;
	protected Date end;
	protected long maxDuration;

	protected void checkIn() throws TaskDurationExceededException {
		this.lastActivity = new Date();
		if (this.lastActivity.getTime() - this.start.getTime() > this.maxDuration) {
			throw new TaskDurationExceededException(this.start, this.lastActivity, this.maxDuration);
		}
	}

	/**
	 * @see com.ajah.job.task.AjahTask#execute(Run, JobTask)
	 */
	@Override
	public void execute(final Run run, final JobTask jobTask) throws TaskExecutionException {
		this.start = new Date();
		this.maxDuration = jobTask.getMaxDuration();
		innerExecute(run);
		this.end = new Date();
	}

	public abstract void innerExecute(final Run run) throws TaskExecutionException;

	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
