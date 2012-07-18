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

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.FeedSourceId;

/**
 * Manages persistence of {@link FeedSource}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FeedSourceManager {

	@Autowired
	private FeedSourceDao feedDao;

	/**
	 * Finds a feed source by the SHA-1 of the feed url.
	 * 
	 * @param feedUrlSha1
	 *            The SHA-1 of the feed url.
	 * @return The feed source, if found, otherwise null.
	 * @throws DataOperationException
	 */
	public FeedSource findByFeedUrlSha1(final String feedUrlSha1) throws DataOperationException {
		return this.feedDao.findByFeedUrlSha1(feedUrlSha1);
	}

	/**
	 * Find a feed source where the next_poll_date field is in the past.
	 * 
	 * @return A feed source where the next_poll_date field is in the past, if
	 *         available, otherwise null.
	 * @throws DataOperationException
	 */
	public FeedSource getStaleFeedSource() throws DataOperationException {
		return this.feedDao.getStaleFeedSource();
	}

	/**
	 * Saves a feed source, inserting if the ID is empty, otherwise updating.
	 * 
	 * @param feedSource
	 *            The feed source to save.
	 * @throws DataOperationException
	 *             If the feed source could not be saved.
	 */
	public void save(final FeedSource feedSource) throws DataOperationException {
		if (feedSource.getId() == null) {
			feedSource.setId(new FeedSourceId(UUID.randomUUID().toString()));
			this.feedDao.insert(feedSource);
		} else {
			this.feedDao.update(feedSource);
		}

	}

}
