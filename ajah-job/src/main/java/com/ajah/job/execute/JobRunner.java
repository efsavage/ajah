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

import com.ajah.job.Job;
import com.ajah.job.run.Run;
import com.ajah.job.task.Task;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * A JobRunner takes a {@link Job} and manages the execution of it's
 * {@link Task}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public interface JobRunner {

	/**
	 * Execute a run.
	 * 
	 * @param run
	 *            The run to execute.
	 * @throws DataOperationException
	 *             If there was a problem loading the run or it's tasks. This
	 *             should not be thrown by any of the task executions.
	 */
	void execute(final Run run) throws DataOperationException;

}
