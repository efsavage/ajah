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
import lombok.extern.java.Log;

import org.springframework.context.ApplicationContext;

import com.ajah.job.Job;
import com.ajah.job.task.Task;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
@EqualsAndHashCode(callSuper = true)
public class SerialJobRunner extends AbstractJobRunner {

	private final boolean abortOnError;

	private final boolean initialized = false;

	public SerialJobRunner(final Job job, ApplicationContext applicationContext, final boolean abortOnError) {
		this.job = job;
		this.applicationContext = applicationContext;
		this.abortOnError = abortOnError;
	}

	public void init() {
		super.init(this.applicationContext);
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (!this.initialized) {
			init();
		}
		for (Task task : tasks) {
			task.execute(this.applicationContext);
		}
	}

}
