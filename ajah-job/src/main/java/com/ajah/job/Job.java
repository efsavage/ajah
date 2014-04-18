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
package com.ajah.job;

import java.util.Date;

import lombok.Data;

import com.ajah.job.task.Task;
import com.ajah.util.Identifiable;

/**
 * A Job is a set of {@link Task}s to be executed. The {@link ExecutionStrategy}
 * defines how the tasks are managed.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
public class Job implements Identifiable<JobId> {

	private JobId id;
	private String name;
	private JobStatus status;
	private JobType type;
	private ExecutionStrategy executionStrategy;
	private Date created;
	/**
	 * The key/name/ID that references this job in external monitors such as
	 * Vigilanced.
	 */
	private String monitorKey;

}
