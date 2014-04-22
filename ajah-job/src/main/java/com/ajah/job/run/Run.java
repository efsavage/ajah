/*
 *  Copyright 2013-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.job.run;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import com.ajah.job.Job;
import com.ajah.job.JobId;
import com.ajah.job.run.data.RunMessageManager;
import com.ajah.job.run.data.RunMetricManager;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.util.Identifiable;

@Data
@Slf4j
public class Run implements Identifiable<RunId> {

	@GeneratedValue
	private RunId id;
	private JobId jobId;
	private RunStatus status;
	private RunType type;
	private Date created;
	private Date start;
	private Date end;
	private Date lastActivity;
	private long maxDuration;

	@Transient
	RunMessageManager runMessageManager;

	@Transient
	RunMetricManager runMetricManager;

	@Transient
	Job job;

	public void debug(String message) {
		try {
			this.runMessageManager.create(this.job, this.getId(), message, null, RunMessageType.DEBUG, false);
		} catch (DataOperationException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void error(Throwable t) {
		try {
			this.runMessageManager.create(this.job, this.getId(), t.getMessage(), t, RunMessageType.ERROR, true);
		} catch (DataOperationException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void metric(String name, BigDecimal value, boolean external) {
		try {
			this.runMetricManager.create(name, value, this.getId(), this.job, external);
		} catch (DataOperationException e) {
			log.error(e.getMessage(), e);
		}
	}

}
