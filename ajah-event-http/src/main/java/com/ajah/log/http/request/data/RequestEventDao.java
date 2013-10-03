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

import com.ajah.log.http.request.RequestEvent;
import com.ajah.log.http.request.RequestEventId;
import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Data operations on the "request_event" table.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public interface RequestEventDao extends AjahDao<RequestEventId, RequestEvent> {

	/**
	 * Inserts a RequestEvent into the database.
	 * 
	 * @param requestEvent
	 *            The requestEvent to insert.
	 * @param delayed
	 *            Should the insert be delayed?
	 * @return The result of the operation. If delayed is true the number of
	 *         rows affected will always be zero.
	 * @throws DataOperationException
	 *             If the record could not be inserted.
	 */
	DataOperationResult<RequestEvent> insert(final RequestEvent requestEvent, final boolean delayed) throws DataOperationException;

}
