/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.timer;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

/**
 * This class allows applications that are rate-limited via a quota/restore rate
 * (e.g. Amazon's web services) to easily operate at the proper rate.
 * 
 * No timer or monitor threads are used, an available slot is determined on each
 * request.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class RestorativeQuota {

	@Getter
	private final int quota;
	@Getter
	@Setter
	private long restorationDelay;
	@Getter
	@Setter
	private long sleepDelay = 100;

	private long lastPurge = System.currentTimeMillis();

	private final AtomicLong requests = new AtomicLong(0);

	/**
	 * Creates a quota of a certain size. The size cannot be changed once
	 * instantiated, but the delay can be adjusted.
	 * 
	 * @param quota
	 *            The limit of requests.
	 * @param restorationDelay
	 *            The milliseconds between which an item is restored.
	 */
	public RestorativeQuota(int quota, long restorationDelay) {
		this.quota = quota;
		this.restorationDelay = restorationDelay;
		if (this.sleepDelay < restorationDelay) {
			this.sleepDelay = restorationDelay / quota;
		}
	}

	/**
	 * This method will block until a request is available. The frequency with
	 * which available slots are checked is defined by {@link #getSleepDelay()}.
	 * 
	 * This signature allows for wrapping a target, and blocking the method
	 * being invoked. Example:
	 * 
	 * <code>serviceClient.call(quota.request(requestObject));</code>
	 * 
	 * @param target
	 *            The target we're wrapping, so we can inline this call.
	 * @return The target passed in.
	 * 
	 * @throws InterruptedException
	 *             If the sleep is interrupted.
	 */
	public synchronized <T> T request(T target) throws InterruptedException {
		while (true) {
			long now = System.currentTimeMillis();
			if (this.requests.get() < this.quota) {
				this.requests.incrementAndGet();
				return target;
			}
			boolean sleep = true;
			while (this.lastPurge + this.restorationDelay < now) {
				log.finest("Purging");
				sleep = false;
				// At least one item has been purged, so we'll pop them off and
				// advance the timer to the present.
				if (this.requests.get() > 0) {
					this.requests.decrementAndGet();
				}
				this.lastPurge += this.restorationDelay;
			}

			if (sleep) {
				// No requests were purged, so we'll sleep and try again
				log.finest("Sleeping for " + this.sleepDelay + "ms");
				Thread.sleep(this.sleepDelay);
			}
		}
	}

	/**
	 * Used to set the available requests on a quota to zero and mark is as
	 * purged. Used when a remote service returns an error that a request has
	 * been throttled, perhaps because a previous process has exhausted it.
	 */
	public void exhaust() {
		this.lastPurge = System.currentTimeMillis();
		this.requests.set(this.quota);
	}

}
