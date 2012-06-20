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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple gang that can execute a list of jobs.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class SimpleWorkerGang {

	private final List<Worker<?>> workers = new ArrayList<>();

	private final boolean autoStart;

	/**
	 * Constructor with autoStart enabled.
	 */
	public SimpleWorkerGang() {
		this.autoStart = true;
	}

	/**
	 * Public constructor.
	 * 
	 * @param autoStart
	 *            Should jobs be started as they are added?
	 */
	public SimpleWorkerGang(final boolean autoStart) {
		this.autoStart = autoStart;
	}

	/**
	 * Adds a worker to this gang.
	 * 
	 * @param worker
	 *            The worker to add to the gang. If autostart is active it will
	 *            begin execution immediately.
	 */
	public void add(final Worker<?> worker) {
		this.workers.add(worker);
		if (this.autoStart) {
			worker.go();
		}
	}

	/**
	 * Invokes {@link Worker#go()} on all workers in this gang. This isn't
	 * necessary if autoStart is true.
	 */
	public void go() {
		for (final Worker<?> worker : this.workers) {
			worker.go();
		}
	}

}
