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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.FeedSourceStatus;
import com.ajah.syndicate.FeedSourceType;
import com.ajah.syndicate.PollStatus;
import com.ajah.syndicate.SyndicationException;
import com.ajah.syndicate.opml.Opml;
import com.ajah.syndicate.opml.Outline;
import com.ajah.util.CollectionUtils;
import com.ajah.util.StringUtils;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.impl.OPML10Parser;

/**
 * Utilities for dealing with OPML files.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class OpmlUtils {

	private static Outline createOutline(final com.sun.syndication.feed.opml.Outline syndOutline, final Outline parent) {
		final Outline outline = new Outline();
		@SuppressWarnings("unchecked")
		final List<com.sun.syndication.feed.opml.Outline> children = syndOutline.getChildren();
		outline.setOutlines(createOutlines(children, outline));

		outline.setText(syndOutline.getText());
		outline.setTitle(syndOutline.getTitle());
		outline.setHtmlUrl(syndOutline.getHtmlUrl());
		outline.setXmlUrl(syndOutline.getXmlUrl());
		outline.setType(syndOutline.getType());
		outline.setParent(parent);

		return outline;
	}

	private static List<Outline> createOutlines(final com.sun.syndication.feed.opml.Opml syndOpml) {
		@SuppressWarnings("unchecked")
		final List<com.sun.syndication.feed.opml.Outline> syndOutlines = syndOpml.getOutlines();
		return createOutlines(syndOutlines, null);
	}

	private static List<Outline> createOutlines(final List<com.sun.syndication.feed.opml.Outline> syndOutlines, final Outline parent) {
		final List<Outline> outlines = new ArrayList<>(syndOutlines.size());
		for (final com.sun.syndication.feed.opml.Outline syndOutline : syndOutlines) {
			outlines.add(createOutline(syndOutline, parent));
		}
		return outlines;
	}

	private static FeedSource extractFeedSource(final Outline outline) {
		final FeedSource feedSource = new FeedSource();
		feedSource.setTitle(outline.getTitle());
		feedSource.setHtmlUrl(outline.getHtmlUrl());
		feedSource.setFeedUrl(outline.getXmlUrl());
		if (!outline.getTitle().equals(outline.getText())) {
			feedSource.setDescription(outline.getText());
		}
		if ("rss".equals(outline.getType())) {
			feedSource.setType(FeedSourceType.RSS);
		} else {
			log.warning("Unknown type " + outline.getType());
			feedSource.setType(FeedSourceType.UNKNOWN);
		}
		feedSource.setStatus(FeedSourceStatus.ACTIVE);
		feedSource.setPollStatus(PollStatus.ACTIVE);
		return feedSource;
	}

	private static void extractFeedSources(final List<Outline> outlines, final List<FeedSource> feedSources) {
		for (final Outline outline : outlines) {
			if (!CollectionUtils.isEmpty(outline.getOutlines())) {
				extractFeedSources(outline.getOutlines(), feedSources);
			}
			if (!StringUtils.isBlank(outline.getXmlUrl())) {
				feedSources.add(extractFeedSource(outline));
			}
		}
	}

	/**
	 * Extracts a list of FeedSources from an {@link Opml} instance.
	 * 
	 * @param opml
	 *            The opml to investigate.
	 * @return The list of feedSources, may be empty but will not be null.
	 */
	public static List<FeedSource> extractFeedSources(final Opml opml) {
		final List<FeedSource> feedSources = new ArrayList<>();
		extractFeedSources(opml.getOutlines(), feedSources);
		return feedSources;
	}

	/**
	 * Parses a JDOM document into an Ajah {@link Opml}.
	 * 
	 * @param doc
	 *            The document to parse.
	 * @return The constructed Opml instance.
	 * @throws SyndicationException
	 *             if the document could not be parsed.
	 */
	public static Opml parse(final Document doc) throws SyndicationException {
		com.sun.syndication.feed.opml.Opml syndOpml;
		try {
			syndOpml = (com.sun.syndication.feed.opml.Opml) new OPML10Parser().parse(doc, false);
		} catch (IllegalArgumentException | FeedException e) {
			throw new SyndicationException(e);
		}
		final Opml opml = new Opml();
		opml.setOutlines(createOutlines(syndOpml));
		return opml;
	}

	/**
	 * Parses a JDOM document into an Ajah {@link Opml} via
	 * {@link #parse(Document)}.
	 * 
	 * @see #parse(Document)
	 * @param file
	 *            The file to parse.
	 * @return The constructed Opml instance.
	 * @throws IOException
	 *             If the file could not be read.
	 * @throws SyndicationException
	 */
	public static Opml parse(final File file) throws IOException, SyndicationException {
		Document doc;
		try {
			doc = new SAXBuilder(false).build(file);
		} catch (final JDOMException e) {
			throw new SyndicationException(e);
		}
		return parse(doc);
	}
}
