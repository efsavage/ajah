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
package com.ajah.job.run;

import java.util.Date;

/**
 * Thrown when a run has exceeded it's allowed execution time.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class RunDurationExceededException extends Exception {

	private final Date start;
	private final Date lastActivity;
	private final long maxDuration;

	/**
	 * Basic constructor with information for reporting.
	 * 
	 * @param start
	 *            The time the run started.
	 * @param lastActivity
	 *            The time the exception occurred.
	 * @param maxDuration
	 *            The maximum duration permitted.
	 */
	public RunDurationExceededException(Date start, Date lastActivity, long maxDuration) {
		this.start = start;
		this.lastActivity = lastActivity;
		this.maxDuration = maxDuration;
	}

	/**
	 * The time the run started.
	 * 
	 * @return the start
	 */
	public Date getStart() {
		return this.start;
	}

	/**
	 * The time the exception occurred.
	 * 
	 * @return The time the exception occurred.
	 */
	public Date getLastActivity() {
		return this.lastActivity;
	}

	/**
	 * The maximum duration permitted.
	 * 
	 * @return The maximum duration permitted.
	 */
	public long getMaxDuration() {
		return this.maxDuration;
	}

}
