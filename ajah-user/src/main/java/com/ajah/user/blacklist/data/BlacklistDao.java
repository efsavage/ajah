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
package com.ajah.user.blacklist.data;

import java.util.List;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.blacklist.Blacklist;
import com.ajah.user.blacklist.BlacklistId;
import com.ajah.user.blacklist.BlacklistStatus;
import com.ajah.user.blacklist.BlacklistType;

/**
 * DAO interface for {@link Blacklist}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface BlacklistDao extends AjahDao<BlacklistId, Blacklist> {

	/**
	 * Returns a list of {@link Blacklist}s that match the specified criteria.
	 * 
	 * @param part1
	 *            The first part of the blacklist
	 * @param part2
	 *            The second part of the blacklist.
	 * @param type
	 *            The type of blacklist, optional.
	 * @param status
	 *            The status of the blacklist, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Blacklist}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<Blacklist> list(final String part1, final String part2, final BlacklistType type, final BlacklistStatus status, final long page, final long count) throws DataOperationException;

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The blacklist type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	long count(final BlacklistType type, final BlacklistStatus status) throws DataOperationException;

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	int searchCount(final String search) throws DataOperationException;

}
