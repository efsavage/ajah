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

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import com.ajah.job.Job;
import com.ajah.job.JobId;
import com.ajah.util.Identifiable;

/**
 * A configurable association between a {@link Job} and a {@link Task}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@Table(name = "job__task")
public class JobTask implements Identifiable<JobTaskId> {

	private JobTaskId id;
	private JobId jobId;
	private TaskId taskId;
	private String comment;
	private JobTaskStatus status;
	private JobTaskType type;
	private Date created;
	private Date modified;
	private long maxDuration;

	@Transient
	private Task task;

}
