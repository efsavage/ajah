/*
 * PROPRIETARY and CONFIDENTIAL
 * 
 * Copyright 2012 Magellan Distribution Corporation
 * 
 * All rights reserved.
 */
package com.ajah.syndicate.fetch;

import java.io.IOException;

import lombok.extern.java.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.data.EntryManager;
import com.ajah.syndicate.data.FeedManager;
import com.ajah.syndicate.data.FeedSourceManager;

/**
 * Simple utility for finding {@link FeedSource}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
@Component
public class FeedDiscovery {

	@Autowired
	FeedSourceManager feedSourceManager;

	@Autowired
	FeedManager feedManager;

	@Autowired
	EntryManager entryManager;

	/**
	 * Pulls a page and attempts to discover a feed for it via
	 * link[rel='alternate'].
	 * 
	 * @param url
	 *            The URL of the page to try and discover the feed for.
	 * @return The feedsource if matched or created, may be null.
	 * @throws ClientProtocolException
	 *             If the page could not be pulled.
	 * @throws IOException
	 *             If the page could not be pulled.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	public FeedSource discover(final String url) throws ClientProtocolException, IOException, DataOperationException {
		log.fine("Discovering feed for " + url);
		final HttpClient client = new DefaultHttpClient();
		final HttpGet get = new HttpGet(url);
		final HttpResponse response = client.execute(get);
		final String html = EntityUtils.toString(response.getEntity());
		final Document doc = Jsoup.parse(html);
		final Elements alternateLinks = doc.select("link");
		for (final Element alternateLink : alternateLinks) {
			if ("alternate".equals(alternateLink.attr("rel"))) {
				if ("application/rss+xml".equals(alternateLink.attr("type"))) {
					log.fine("Found rss link " + alternateLink.attr("href"));
					final String rss = alternateLink.attr("href");
					return this.feedSourceManager.findOrCreateByFeedUrl(rss);
				}
				log.fine("Found alternate link " + alternateLink.html());
			} else {
				log.fine("Found link " + alternateLink.html());
			}
		}
		return null;
	}

}
