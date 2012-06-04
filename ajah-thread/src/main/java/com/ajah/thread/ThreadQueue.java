/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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
package com.ajah.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.ajah.util.AjahUtils;

/**
 * A wrapper around {@link Executor} to ease configuration.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class ThreadQueue {

	private static final ThreadQueue INSTANCE = new ThreadQueue();
	private Executor executor;

	private ThreadQueue() {
		BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>();
		this.executor = new ThreadPoolExecutor(10, 50, 1, TimeUnit.DAYS, queue);
	}

	/**
	 * Creates a {@link ThreadQueue} around the supplied {@link Executor}.
	 * 
	 * @param executor
	 *            The executor to wrap. Required.
	 */
	public ThreadQueue(Executor executor) {
		AjahUtils.requireParam(executor, "executor");
		this.executor = executor;
	}

	/**
	 * Returns the singleton instance with default configuration.
	 * 
	 * @return The singleton instance with default configuration.
	 */
	public static ThreadQueue getInstance() {
		return INSTANCE;
	}

	/**
	 * Drops a runnable onto the configured Executor.
	 * 
	 * @param runnable
	 *            The job to execute, required.
	 */
	public void execute(Runnable runnable) {
		AjahUtils.requireParam(runnable, "runnable");
		this.executor.execute(runnable);
	}

}
