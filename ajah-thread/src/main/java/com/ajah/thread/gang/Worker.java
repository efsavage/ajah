/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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
package com.ajah.thread.gang;

import java.util.concurrent.ExecutionException;

/**
 * A worker is an asynchronous job that is part of a Gang. It does work in a
 * thread and returns a value.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The type of object to return.
 * 
 */
public interface Worker<T> {

	/**
	 * Cancel this job, rollback or cleanup may happen. Once a cancel is
	 * requested, it may not be "uncancelled".
	 * 
	 * @param mayInterruptIfRunning
	 *            If false, the job will only be cancelled if it is not yet
	 *            running.
	 * 
	 * @return True if the job was successfully cancelled as a result of this
	 *         call or previously, otherwise false.
	 */
	boolean cancel(boolean mayInterruptIfRunning);

	/**
	 * Returns the value. This will wait until the job is complete or the job
	 * timed out.
	 * 
	 * @return The result of the job, may be null.
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	T get() throws InterruptedException, ExecutionException;

	/**
	 * Determines if a request to cancel this job has been made.
	 * 
	 * @return true if a request to cancel this job has been made, otherwise
	 *         false.
	 */
	boolean isCancelRequested();

	/**
	 * Begin executing the job.
	 */
	void go();

}