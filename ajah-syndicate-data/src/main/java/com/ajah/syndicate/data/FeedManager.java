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

import com.ajah.spring.jdbc.DatabaseAccessException;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.syndicate.Feed;
import com.ajah.syndicate.FeedId;
import com.ajah.syndicate.FeedSource;

/**
 * Manages persistence of {@link Feed}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
public class FeedManager {

	@Autowired
	private FeedDao feedDao;

	/**
	 * Finds the most recent version of a feed for a {@link FeedSource}.
	 * 
	 * @param feedSource
	 *            The feed source to query on.
	 * @return The most recent feed for the feed source, or null.
	 * @throws DatabaseAccessException
	 */
	public Feed getLatestFeed(FeedSource feedSource) throws DatabaseAccessException {
		return this.feedDao.find(new Criteria().eq("feed_source_id", feedSource).orderBy("created", Order.DESC));
	}

	/**
	 * Saves a feed, inserting if there is no ID set, otherwise updating.
	 * 
	 * @param feed
	 *            The feed to save.
	 * @throws DatabaseAccessException
	 *             If the feed could not be saved.
	 */
	public void save(Feed feed) throws DatabaseAccessException {
		if (feed.getId() == null) {
			feed.setId(new FeedId(UUID.randomUUID().toString()));
			this.feedDao.insert(feed);
		} else {
			this.feedDao.update(feed);
		}
	}

	/**
	 * Inserts a feed.
	 * 
	 * @param feed
	 *            The feed to insert.
	 * @throws DatabaseAccessException
	 *             If the feed could not be inserted.
	 */
	public void insert(Feed feed) throws DatabaseAccessException {
		this.feedDao.insert(feed);
	}

}
