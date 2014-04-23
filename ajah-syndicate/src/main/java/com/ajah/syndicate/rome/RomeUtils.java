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
package com.ajah.syndicate.rome;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.ajah.html.HtmlUtils;
import com.ajah.syndicate.Entry;
import com.ajah.syndicate.Feed;
import com.ajah.syndicate.FeedId;
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.SyndicationException;
import com.ajah.util.StringUtils;
import com.ajah.util.data.HashUtils;
import com.ajah.util.data.XmlString;
import com.ajah.util.net.AjahMimeType;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

/**
 * 
 * Handles all interoperation with Rome classes.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class RomeUtils {

	/**
	 * Creates Ajah entries from a Rome feed.
	 * 
	 * @param syndFeed
	 *            The feed to create entries for.
	 * @return The list of Entries, may be empty, will not be null.
	 */
	private static List<Entry> createEntries(final SyndFeed syndFeed, final Feed feed) {
		@SuppressWarnings("unchecked")
		final List<SyndEntry> syndEntries = syndFeed.getEntries();
		if (syndEntries == null || syndEntries.size() < 1) {
			return Collections.emptyList();
		}
		final List<Entry> entries = new ArrayList<>(syndEntries.size());
		for (final SyndEntry syndEntry : syndEntries) {
			final Entry entry = createEntry(syndEntry, feed);
			entries.add(entry);
		}
		return entries;
	}

	/**
	 * Convert a Rome entry to an Ajah entry.
	 * 
	 * @param syndEntry
	 *            The entry to convert.
	 * @return The converted entry. Will not be null.
	 */
	private static Entry createEntry(final SyndEntry syndEntry, final Feed feed) {
		final Entry entry = new Entry();
		entry.setFeedId(feed.getId());
		entry.setFeedSourceId(feed.getFeedSourceId());
		entry.setAuthor(syndEntry.getAuthor());
		entry.setTitle(syndEntry.getTitle());
		entry.setHtmlUrl(syndEntry.getUri());
		entry.setHtmlUrlSha1(HashUtils.sha1Hex(syndEntry.getUri()));
		entry.setPublished(syndEntry.getPublishedDate());
		entry.setUpdated(syndEntry.getUpdatedDate());
		@SuppressWarnings("unchecked")
		final List<SyndContent> contents = syndEntry.getContents();
		if (contents.size() < 1) {
			log.finest("Contents are empty");
			entry.setContentType(AjahMimeType.TEXT_PLAIN);
		} else if (contents.size() > 1) {
			log.warning(contents.size() + " contents in one entry");
		}
		for (final SyndContent content : contents) {
			if (content.getType() == null) {
				// TODO see if it's actually html
				entry.setContentType(AjahMimeType.TEXT_PLAIN);
			} else {
				entry.setContentType(AjahMimeType.get(content.getType()));
			}
			entry.setContent(content.getValue());
			entry.setContentSha1(HashUtils.sha1Hex(content.getValue()));

			if (!entry.getContentType().isText()) {
				log.warning("Non-text type of content: " + content.getType());
			}
		}

		if (syndEntry.getDescription() != null) {
			AjahMimeType descriptionType = null;
			if (syndEntry.getDescription().getType() == null) {
				// TODO see if it's actually html
				descriptionType = AjahMimeType.TEXT_PLAIN;
			} else {
				descriptionType = AjahMimeType.get(syndEntry.getDescription().getType());
			}
			switch (descriptionType) {
			case TEXT_PLAIN:
				entry.setDescription(HtmlUtils.toBodyHtml(syndEntry.getDescription().getValue()));
				break;
			case TEXT_HTML:
				entry.setDescription(syndEntry.getDescription().getValue());
				break;
			default:
				entry.setDescription(HtmlUtils.toBodyHtml(syndEntry.getDescription().getValue()));
				log.warning("Non-text type of description: " + descriptionType + " [" + syndEntry.getDescription().getType() + "]");
			}
		}
		if (StringUtils.isBlank(entry.getTitle())) {
			if (!StringUtils.isBlank(entry.getDescription())) {
				entry.setTitle(Jsoup.clean(StringUtils.truncate(entry.getDescription(), 100), Whitelist.simpleText()));
			} else if (!StringUtils.isBlank(entry.getContent())) {
				entry.setTitle(entry.getContent().substring(0, 100));
			}
		}
		if (StringUtils.isBlank(entry.getContent()) && StringUtils.isBlank(entry.getDescription()) && StringUtils.isBlank(entry.getTitle())) {
			log.warning("Title, contents and description are all null");
		}
		return entry;
	}

	/**
	 * Convert a Rome feed to an Ajah feed.
	 * 
	 * @param syndFeed
	 *            The Rome feed object to convert.
	 * @param sha1
	 *            The sha1 of the original feed.
	 * @param feedSource
	 *            The source of the original feed.
	 * @return A Feed, created from the syndFeed parameter
	 */
	public static Feed createFeed(final SyndFeed syndFeed, final String sha1, final FeedSource feedSource) {
		final Feed feed = new Feed();
		feed.setId(new FeedId(UUID.randomUUID().toString()));
		feed.setFeedSourceId(feedSource.getId());
		feed.setAuthor(syndFeed.getAuthor());
		feed.setUrl(syndFeed.getUri());
		if (StringUtils.isBlank(syndFeed.getTitle())) {
			feed.setTitle(feedSource.getTitle());
		} else {
			feed.setTitle(syndFeed.getTitle());
		}
		feed.setLink(syndFeed.getLink());
		feed.setEntries(createEntries(syndFeed, feed));
		feed.setCreated(new Date());
		feed.setPublished(syndFeed.getPublishedDate());
		feed.setSha1(sha1);
		return feed;
	}

	/**
	 * Creates a feed from an {@link XmlString}.
	 * 
	 * @param xmlString
	 *            The XML version of the feed.
	 * @param feedSource
	 *            The source of the feed.
	 * @return A Feed instance created from the XML.
	 * @throws SyndicationException
	 *             If the xml could not be parsed or did not have valid data.
	 * 
	 */
	public static Feed createFeed(final XmlString xmlString, final FeedSource feedSource) throws SyndicationException {
		final SyndFeedInput input = new SyndFeedInput();
		try {
			final SyndFeed syndFeed = input.build(new StringReader(xmlString.toString()));
			return createFeed(syndFeed, xmlString.getSha1(), feedSource);
		} catch (final IllegalArgumentException e) {
			throw new SyndicationException(e.getMessage());
		} catch (final FeedException e) {
			throw new SyndicationException(e);
		}
	}
}
