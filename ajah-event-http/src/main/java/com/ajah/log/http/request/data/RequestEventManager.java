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
package com.ajah.log.http.request.data;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.log.http.request.RequestEvent;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Persistence management for {@link RequestEvent}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Log
public class RequestEventManager {

	@Autowired
	private RequestEventDao requestEventDao;

	/**
	 * Save the request event. Note, always does a <em>delayed</em> insert as
	 * there's no need to edit the records at this time.
	 * 
	 * @param requestEvent
	 * @throws DataOperationException
	 */
	public void save(final RequestEvent requestEvent) throws DataOperationException {
		this.requestEventDao.insert(requestEvent, true);
		log.finest(requestEvent.getId().toString() + " saved");
	}

}
