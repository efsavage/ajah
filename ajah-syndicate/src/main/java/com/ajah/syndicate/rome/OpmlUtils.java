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
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.JDOMException;

import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.FeedSourceStatus;
import com.ajah.syndicate.FeedSourceType;
import com.ajah.syndicate.PollStatus;
import com.ajah.syndicate.opml.Opml;
import com.ajah.syndicate.opml.Outline;
import com.ajah.util.CollectionUtils;
import com.ajah.util.StringUtils;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SAXBuilder;
import com.sun.syndication.io.impl.OPML10Parser;

/**
 * Utilities for dealing with OPML files.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class OpmlUtils {

	private static final Logger log = Logger.getLogger(OpmlUtils.class.getName());

	/**
	 * Parses a JDOM document into an Ajah {@link Opml}.
	 * 
	 * @param doc
	 *            The document to parse.
	 * @return The constructed Opml instance.
	 * @throws FeedException
	 *             if the document could not be parsed.
	 */
	public static Opml parse(Document doc) throws FeedException {
		com.sun.syndication.feed.opml.Opml syndOpml = (com.sun.syndication.feed.opml.Opml) new OPML10Parser().parse(doc, false);
		Opml opml = new Opml();
		opml.setOutlines(createOutlines(syndOpml));
		return opml;
	}

	private static List<Outline> createOutlines(com.sun.syndication.feed.opml.Opml syndOpml) {
		@SuppressWarnings("unchecked")
		List<com.sun.syndication.feed.opml.Outline> syndOutlines = syndOpml.getOutlines();
		return createOutlines(syndOutlines, null);
	}

	private static List<Outline> createOutlines(List<com.sun.syndication.feed.opml.Outline> syndOutlines, Outline parent) {
		List<Outline> outlines = new ArrayList<>(syndOutlines.size());
		for (com.sun.syndication.feed.opml.Outline syndOutline : syndOutlines) {
			outlines.add(createOutline(syndOutline, parent));
		}
		return outlines;
	}

	private static Outline createOutline(com.sun.syndication.feed.opml.Outline syndOutline, Outline parent) {
		Outline outline = new Outline();
		@SuppressWarnings("unchecked")
		List<com.sun.syndication.feed.opml.Outline> children = syndOutline.getChildren();
		outline.setOutlines(createOutlines(children, outline));

		outline.setText(syndOutline.getText());
		outline.setTitle(syndOutline.getTitle());
		outline.setHtmlUrl(syndOutline.getHtmlUrl());
		outline.setXmlUrl(syndOutline.getXmlUrl());
		outline.setType(syndOutline.getType());
		outline.setParent(parent);

		return outline;
	}

	/**
	 * Parses a JDOM document into an Ajah {@link Opml} via
	 * {@link #parse(Document)}.
	 * 
	 * @see #parse(Document)
	 * @param file
	 *            The file to parse.
	 * @return The constructed Opml instance.
	 * @throws FeedException
	 *             if the document could not be parsed.
	 * @throws JDOMException
	 *             If the document could not be parsed by JDOM.
	 * @throws IOException
	 *             If the file could not be read.
	 */
	public static Opml parse(File file) throws FeedException, JDOMException, IOException {
		Document doc = new SAXBuilder(false).build(file);
		return parse(doc);
	}

	/**
	 * Extracts a list of FeedSources from an {@link Opml} instance.
	 * 
	 * @param opml
	 *            The opml to investigate.
	 * @return The list of feedSources, may be empty but will not be null.
	 */
	public static List<FeedSource> extractFeedSources(Opml opml) {
		List<FeedSource> feedSources = new ArrayList<>();
		extractFeedSources(opml.getOutlines(), feedSources);
		return feedSources;
	}

	private static void extractFeedSources(List<Outline> outlines, List<FeedSource> feedSources) {
		for (Outline outline : outlines) {
			if (!CollectionUtils.isEmpty(outline.getOutlines())) {
				extractFeedSources(outline.getOutlines(), feedSources);
			}
			if (!StringUtils.isBlank(outline.getXmlUrl())) {
				feedSources.add(extractFeedSource(outline));
			}
		}
	}

	private static FeedSource extractFeedSource(Outline outline) {
		FeedSource feedSource = new FeedSource();
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
}
