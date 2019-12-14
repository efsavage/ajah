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
package com.ajah.syndicate.data;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.FeedSourceId;

/**
 * DAO for {@link FeedSource}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public interface FeedSourceDao extends AjahDao<FeedSourceId, FeedSource> {

	/**
	 * Updates a feed source that exists.
	 * 
	 * @param feedSource
	 *            The feed source to update.
	 * @return The number of rows affected by the update.
	 */
	@Override
	DataOperationResult<FeedSource> update(final FeedSource feedSource);

	/**
	 * Finds an active, stale feed source that needs to be polled.
	 * 
	 * @return An active, stale feed source that should be polled.
	 * @throws DataOperationException
	 *             If the query could not be executed
	 */
	FeedSource getStaleFeedSource() throws DataOperationException;

	/**
	 * Finds a feed by the SHA-1 of it's url.
	 * 
	 * @param feedUrlSha1
	 *            The SHA-1 of the url.
	 * @return The feed source, if found, otherwise null.
	 * @throws DataOperationException
	 *             If the query could not be executed
	 */
	FeedSource findByFeedUrlSha1(final String feedUrlSha1) throws DataOperationException;

}
