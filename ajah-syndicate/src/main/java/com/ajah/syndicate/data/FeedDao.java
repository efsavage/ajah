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
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.Feed;
import com.ajah.syndicate.FeedId;
import com.ajah.syndicate.FeedSource;

/**
 * DAO for {@link Feed}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public interface FeedDao extends AjahDao<FeedId, Feed> {

	/**
	 * Updates an existing Feed.
	 * 
	 * @param feed
	 *            The feed to update.
	 * @return The number of rows affected by the update.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	@Override
	int update(final Feed feed) throws DataOperationException;

	/**
	 * Finds the most recent feed for a feed source.
	 * 
	 * @param feedSource
	 *            The feed source to match on.
	 * @return The latest feed, if found, otherwise null.
	 * @throws DataOperationException
	 *             If the query could not be executed
	 */
	Feed getLatestFeed(final FeedSource feedSource) throws DataOperationException;

}
