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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Logger;

import com.ajah.thread.ThreadQueue;
import com.ajah.util.AjahUtils;

/**
 * A simple implementation of {@link Worker}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The return type of the get method.
 * 
 */
public class SimpleWorker<T> implements Worker<T> {

	private static final Logger log = Logger.getLogger(SimpleWorker.class.getName());
	private final FutureTask<T> task;
	private boolean cancelRequested;
	private ThreadQueue threadQueue;

	/**
	 * Instantiates with the callable, wrapping it a {@link FutureTask}
	 * automatically. More useful for anonymous definitions.
	 * 
	 * @param callable
	 *            The callable the callable object to execute.
	 */
	public SimpleWorker(final Callable<T> callable) {
		AjahUtils.requireParam(callable, "callable");
		this.task = new FutureTask<>(callable);
	}

	/**
	 * Instantiates with the supplied task.
	 * 
	 * @param task
	 *            The task this worker manages.
	 */
	public SimpleWorker(final FutureTask<T> task) {
		AjahUtils.requireParam(task, "task");
		this.task = task;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cancel(final boolean mayInterruptIfRunning) {
		this.cancelRequested = true;
		// TODO This isn't correct
		return this.task.cancel(mayInterruptIfRunning);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get() throws InterruptedException, ExecutionException {
		log.fine("Get called");
		return this.task.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void go() {
		if (this.threadQueue == null) {
			ThreadQueue.getInstance().execute(this.task);
		}
		this.threadQueue.execute(this.task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCancelRequested() {
		return this.cancelRequested;
	}

}
