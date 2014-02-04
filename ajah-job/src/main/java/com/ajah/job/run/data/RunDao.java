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
package com.ajah.job.run.data;

import java.util.List;

import com.ajah.job.run.Run;
import com.ajah.job.run.RunId;
import com.ajah.job.run.RunStatus;
import com.ajah.job.run.RunType;
import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * DAO interface for {@link Run}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface RunDao extends AjahDao<RunId, Run> {

	/**
	 * Returns a list of {@link Run}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of run, optional.
	 * @param status
	 *            The status of the run, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Run}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<Run> list(final RunType type, final RunStatus status, final long page, final long count) throws DataOperationException;

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The run type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	long count(final RunType type, final RunStatus status) throws DataOperationException;

}
