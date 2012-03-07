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
package com.ajah.log.http;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ajah.log.http.request.RequestEvent;
import com.ajah.log.http.request.data.RequestEventManager;
import com.ajah.spring.jdbc.DatabaseAccessException;

/**
 * A simple async task that saves the request event.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class RequestEventHandler implements Runnable {

	private static final Logger log = Logger.getLogger(RequestEventHandler.class.getName());

	private final RequestEvent requestEvent;
	private final RequestEventManager requestEventManager;

	/**
	 * Public constructor.
	 * 
	 * @param requestEvent
	 *            The event to handle.
	 * @param requestEventManager
	 *            The manager to perform operations with.
	 */
	public RequestEventHandler(final RequestEvent requestEvent, final RequestEventManager requestEventManager) {
		this.requestEvent = requestEvent;
		this.requestEventManager = requestEventManager;
	}

	@Override
	public void run() {
		try {
			this.requestEventManager.save(this.requestEvent);
		} catch (final DatabaseAccessException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
