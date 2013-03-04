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

import java.util.Date;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.FeedSourceId;
import com.ajah.syndicate.FeedSourceStatus;
import com.ajah.syndicate.FeedSourceType;
import com.ajah.syndicate.PollStatus;
import com.ajah.util.data.HashUtils;

/**
 * Manages persistence of {@link FeedSource}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Log
public class FeedSourceManager {

	@Autowired
	private FeedSourceDao feedDao;

	/**
	 * Creates a new source.
	 * 
	 * @param url
	 *            The URL of the {@link FeedSource}.
	 * @return The new source.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	private FeedSource create(final String url) throws DataOperationException {
		final FeedSource feedSource = new FeedSource();
		feedSource.setFeedUrl(url);
		feedSource.setTitle(url);
		feedSource.setFeedUrlSha1(HashUtils.sha1Hex(url));
		feedSource.setPollStatus(PollStatus.ACTIVE);
		feedSource.setType(FeedSourceType.RSS);
		feedSource.setStatus(FeedSourceStatus.ACTIVE);
		feedSource.setNextPoll(new Date());
		save(feedSource);
		return feedSource;
	}

	/**
	 * Finds a feed by it's full URL.
	 * 
	 * @param url
	 *            The URL of the {@link FeedSource}.
	 * @return The matching source, if found.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	public FeedSource findByFeedUrl(final String url) throws DataOperationException {
		return findByFeedUrlSha1(HashUtils.sha1Hex(url));
	}

	/**
	 * Finds a feed source by the SHA-1 of the feed url.
	 * 
	 * @param feedUrlSha1
	 *            The SHA-1 of the feed url.
	 * @return The feed source, if found, otherwise null.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	public FeedSource findByFeedUrlSha1(final String feedUrlSha1) throws DataOperationException {
		return this.feedDao.findByFeedUrlSha1(feedUrlSha1);
	}

	/**
	 * Finds a feed by it's full URL. If none is found, creates a new one.
	 * 
	 * @param url
	 *            The URL of the {@link FeedSource}.
	 * @return The new or matching source.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	public FeedSource findOrCreateByFeedUrl(final String url) throws DataOperationException {
		FeedSource feedSource = findByFeedUrlSha1(HashUtils.sha1Hex(url));
		if (feedSource == null) {
			feedSource = create(url);
		}
		return feedSource;
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
		if (feedSource.getNextPoll() == null) {
			feedSource.setNextPoll(new Date());
		}
		if (feedSource.getId() == null) {
			feedSource.setId(new FeedSourceId(UUID.randomUUID().toString()));
			feedSource.setCreated(new Date());
			feedSource.setModified(feedSource.getCreated());
			this.feedDao.insert(feedSource);
		} else {
			feedSource.setModified(new Date());
			this.feedDao.update(feedSource);
		}

	}

	/**
	 * Attempts to find a matching feed source, and creates a new one if
	 * necessary.
	 * 
	 * @param candidate
	 *            The candidate source to me matched or saved.
	 * @return The matched or saved feed source.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	public FeedSource findOrCreate(FeedSource candidate) throws DataOperationException {
		FeedSource feedSource = findByFeedUrlSha1(HashUtils.sha1Hex(candidate.getFeedUrl()));
		if (feedSource == null) {
			feedSource = candidate;
			save(feedSource);
		} else {
			log.fine("Duplicate feed found");
			// TODO Update other information?
		}
		return feedSource;
	}

}
