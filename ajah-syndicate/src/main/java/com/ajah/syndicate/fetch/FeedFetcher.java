/*
 * PROPRIETARY and CONFIDENTIAL
 * 
 * Copyright 2012 Magellan Distribution Corporation
 * 
 * All rights reserved.
 */
package com.ajah.syndicate.fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.Feed;
import com.ajah.syndicate.FeedEntry;
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.PollStatus;
import com.ajah.syndicate.SyndicationException;
import com.ajah.syndicate.data.FeedEntryManager;
import com.ajah.syndicate.data.FeedManager;
import com.ajah.syndicate.data.FeedSourceManager;
import com.ajah.syndicate.rome.RomeUtils;
import com.ajah.util.StringUtils;
import com.ajah.util.data.XmlString;
import com.ajah.util.date.DateUtils;

/**
 * Simple fetcher that will pull feeds and save entries.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class FeedFetcher {

	private static void tempError(final FeedSource feedSource) {
		if (feedSource.getPollStatus() == PollStatus.ERROR_TMP) {
			if (feedSource.getPollStatusSince() == null) {
				feedSource.setPollStatusSince(new Date());
			} else if (Days.daysBetween(new DateTime(feedSource.getPollStatus()), new DateTime()).getDays() > 7) {
				// We've failed for over a week, kill it.
				feedSource.setNextPoll(null);
				feedSource.setPollStatus(PollStatus.ERROR_PERM);
			}
		} else if (feedSource.getPollStatus() == PollStatus.ACTIVE) {
			feedSource.setPollStatusSince(new Date());
			feedSource.setPollStatus(PollStatus.ERROR_TMP);
		} else {
			log.severe("We shouldn't have gotten here!");
			feedSource.setNextPoll(DateUtils.addHours(6));
		}
	}

	@Autowired
	FeedSourceManager feedSourceManager;

	@Autowired
	FeedManager feedManager;

	@Autowired
	FeedEntryManager entryManager;

	private final List<EntryListener> entryListeners = new ArrayList<>();

	/**
	 * Adds a listener to the list of listeners to fire when an entry is found.
	 * 
	 * @param entryListener
	 *            The listener to add.
	 */
	public void addListener(final EntryListener entryListener) {
		this.entryListeners.add(entryListener);
	}

	/**
	 * Handles a feed based on its response, if necessary.
	 * 
	 * @param feedSource
	 *            The feed source to handle.
	 * @param response
	 *            The response from the fetch attempt.
	 * @return true if processing should continue, false if it should be
	 *         aborted.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	private boolean handle(final FeedSource feedSource, final HttpResponse response) throws DataOperationException {
		final int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			if (feedSource.getPollStatus() != PollStatus.ACTIVE) {
				feedSource.setPollStatus(PollStatus.ACTIVE);
				feedSource.setPollStatusSince(null);
				this.feedSourceManager.save(feedSource);
			}
			return true;
		} else if (statusCode == 404 || statusCode == 500) {
			log.warning("404: " + feedSource.getFeedUrl());
			tempError(feedSource);
		} else {
			log.severe(statusCode + ": " + feedSource.getFeedUrl());
		}
		if (feedSource.getPollStatus().isActive()) {
			feedSource.setNextPoll(DateUtils.addHours(6));
		} else {
			feedSource.setNextPoll(null);
		}
		this.feedSourceManager.save(feedSource);
		return false;
	}

	/**
	 * Polls until there are no more stale feeds.
	 * 
	 * @throws DataOperationException
	 */
	public void poll() throws DataOperationException {
		while (true) {
			final FeedSource feedSource = this.feedSourceManager.getStaleFeedSource();
			if (feedSource == null) {
				log.finest("No feed source to poll");
				return;
			}
			log.fine("Polling " + feedSource.getTitle() + " [" + feedSource.getId() + "]");

			try (final CloseableHttpClient http = HttpClientBuilder.create().build()) {
				final HttpGet get = new HttpGet(feedSource.getFeedUrl());
				try (final CloseableHttpResponse response = http.execute(get)) {
					final String rawFeed = EntityUtils.toString(response.getEntity());
					// log.finest(rawFeed);
					EntityUtils.consume(response.getEntity());
					if (!handle(feedSource, response)) {
						continue;
					}
					final Feed feed = RomeUtils.createFeed(new XmlString(rawFeed), feedSource);
					log.fine("Found " + feed.getEntries().size() + " entries");
					this.feedManager.save(feed, true);
					for (final EntryListener entryListener : this.entryListeners) {
						for (final FeedEntry entry : feed.getEntries()) {
							entryListener.handle(entry);
						}
					}
					if (!StringUtils.isBlank(feed.getTitle())) {
						feedSource.setTitle(feed.getTitle());
					}
					if (!StringUtils.isBlank(feed.getLink())) {
						feedSource.setHtmlUrl(feed.getLink());
					}
				}
				feedSource.setNextPoll(DateUtils.addMinutes(feedSource.getFetchFrequency()));
				this.feedSourceManager.save(feedSource);
			} catch (SyndicationException | IOException | RuntimeException e) {
				log.log(Level.WARNING, e.getMessage(), e);
				tempError(feedSource);
				this.feedSourceManager.save(feedSource);
			}
		}
	}

	/**
	 * Finds a stale feed and fetches it, saving it to the database and invoking
	 * any listeners needed. This will run until interrupted by a serious
	 * exception.
	 * 
	 * @throws InterruptedException
	 *             If the thread was interrupted while sleeping (while waiting
	 *             for a feed to fetch).
	 * @throws DataOperationException
	 *             If a database query could not be executed.
	 */
	public void run() throws InterruptedException, DataOperationException {
		while (true) {
			poll();
		}

	}

}
