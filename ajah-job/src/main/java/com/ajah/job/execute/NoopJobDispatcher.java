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

import lombok.extern.java.Log;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * An implementation of {@link JobDispatcher} that does nothing.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class NoopJobDispatcher implements JobDispatcher {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// Empty
	}

	/**
	 * @see com.ajah.job.execute.JobDispatcher#poll()
	 */
	@Override
	public void poll() {
		log.fine("poll() invoked, no jobs being dispatched");
	}

}
