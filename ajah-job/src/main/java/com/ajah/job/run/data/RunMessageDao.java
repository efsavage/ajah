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
package com.ajah.job.run.data;

import java.util.List;

import com.ajah.job.run.RunMessage;
import com.ajah.job.run.RunMessageId;
import com.ajah.job.run.RunMessageStatus;
import com.ajah.job.run.RunMessageType;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * DAO interface for {@link RunMessage}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface RunMessageDao extends AjahDao<RunMessageId, RunMessage> {

	/**
	 * Returns a list of {@link RunMessage}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of runMessage, optional.
	 * @param status
	 *            The status of the runMessage, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link RunMessage}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<RunMessage> list(RunMessageType type, RunMessageStatus status, long page, long count) throws DataOperationException;

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The runMessage type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	long count(RunMessageType type, RunMessageStatus status) throws DataOperationException;

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	int searchCount(String search) throws DataOperationException;

}