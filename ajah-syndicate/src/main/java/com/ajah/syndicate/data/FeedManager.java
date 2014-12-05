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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.Feed;
import com.ajah.syndicate.FeedEntry;
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
@Transactional(rollbackFor = Exception.class)
public class FeedManager {

	@Autowired
	private FeedDao feedDao;

	@Autowired
	private FeedEntryManager entryManager;

	/**
	 * Finds the most recent version of a feed for a {@link FeedSource}.
	 * 
	 * @param feedSource
	 *            The feed source to query on.
	 * @return The most recent feed for the feed source, or null.
	 * @throws DataOperationException
	 */
	public Feed getLatestFeed(final FeedSource feedSource) throws DataOperationException {
		return this.feedDao.getLatestFeed(feedSource);
	}

	/**
	 * Inserts a feed.
	 * 
	 * @param feed
	 *            The feed to insert.
	 * @throws DataOperationException
	 *             If the feed could not be inserted.
	 */
	public void insert(final Feed feed) throws DataOperationException {
		this.feedDao.insert(feed);
	}

	/**
	 * Saves a feed, inserting if there is no ID set, otherwise updating.
	 * 
	 * @param feed
	 *            The feed to save.
	 * @param saveEntries
	 *            If true, also save the entries for this feed.
	 * @throws DataOperationException
	 *             If the feed could not be saved.
	 */
	public void save(final Feed feed, final boolean saveEntries) throws DataOperationException {
		final List<FeedEntry> entries = new ArrayList<>();
		if (feed.getId() == null) {
			feed.setId(new FeedId(UUID.randomUUID().toString()));
			this.feedDao.insert(feed);
		} else {
			this.feedDao.update(feed);
		}
		if (saveEntries && feed.getEntries() != null) {
			for (final FeedEntry entry : feed.getEntries()) {
				if (entry.getId() != null) {
					entries.add(entry);
					this.entryManager.save(entry);
				} else {
					final FeedEntry match = this.entryManager.matchAndSave(entry);
					entries.add(match);
				}
			}
			feed.setEntries(entries);
		}
	}
}
