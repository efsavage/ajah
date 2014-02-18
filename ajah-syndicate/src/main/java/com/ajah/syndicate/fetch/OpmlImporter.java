/*
 * PROPRIETARY and CONFIDENTIAL
 * 
 * Copyright 2012 Magellan Distribution Corporation
 * 
 * All rights reserved.
 */
package com.ajah.syndicate.fetch;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.extern.java.Log;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.SyndicationException;
import com.ajah.syndicate.data.EntryManager;
import com.ajah.syndicate.data.FeedManager;
import com.ajah.syndicate.data.FeedSourceManager;
import com.ajah.syndicate.opml.Opml;
import com.ajah.syndicate.rome.OpmlUtils;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SAXBuilder;

/**
 * Simple fetcher that will pull feeds and save entries.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Component
@Log
public class OpmlImporter {

	@Autowired
	FeedSourceManager feedSourceManager;

	@Autowired
	FeedManager feedManager;

	@Autowired
	EntryManager entryManager;

	/**
	 * Imports an opml file and creates feed sources for it.
	 * 
	 * @param file
	 *            The file to import
	 * @throws InterruptedException
	 *             If the thread was interrupted while sleeping (while waiting
	 *             for a feed to fetch).
	 * @throws DataOperationException
	 *             If a database query could not be executed.
	 * @throws IOException
	 *             If a feed could not be fetched.
	 * @throws SyndicationException
	 *             If there was an error parsing the feed.
	 * @throws JDOMException
	 * @throws FeedException
	 */
	public void importFile(final File file) throws InterruptedException, DataOperationException, IOException, SyndicationException, JDOMException, FeedException {
		final Document doc = new SAXBuilder(false).build(file);
		final Opml opml = OpmlUtils.parse(doc);
		final List<FeedSource> feedSources = OpmlUtils.extractFeedSources(opml);
		for (final FeedSource candidate : feedSources) {
			this.feedSourceManager.findOrCreate(candidate);
			log.fine("Imported " + candidate.getTitle());
		}

	}

}
