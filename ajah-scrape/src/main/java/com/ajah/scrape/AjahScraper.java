/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.scrape;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ajah.http.cache.DiskCache;
import com.ajah.http.err.HttpException;
import com.ajah.util.StringUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class AjahScraper {

	private final long timeout = 86_400_000;

	public Document getDocument(final ScrapedLink link) throws IOException, URISyntaxException, HttpException {
		log.fine("Fetching " + link.getText() + " at " + link.getHref());
		final String html = DiskCache.get(new URI(link.getHref()), this.timeout);
		return Jsoup.parse(html, link.getHref());
	}

	public Document getDocument(final URI uri) throws IOException, HttpException {
		log.fine("Fetching " + uri);
		final String html = DiskCache.get(uri, this.timeout);
		return Jsoup.parse(html, uri.toASCIIString());
	}

	public List<ScrapedLink> scrapeLinks(final URI uri, final String linkPattern, final boolean regex, final boolean ignoreEmptyText) throws IOException, HttpException {
		log.finest("Scraping links from: " + uri.toString());
		final String html = DiskCache.get(uri, this.timeout);
		final Document doc = Jsoup.parse(html, uri.toASCIIString());

		Elements links = null;
		if (StringUtils.isBlank(linkPattern) || regex) {
			throw new IllegalArgumentException("Regex not supported");
		}
		links = doc.select(linkPattern);

		final List<ScrapedLink> scrapedLinks = new ArrayList<>();
		for (final Element link : links) {
			if (!StringUtils.isBlank(link.attr("href"))) {
				if (ignoreEmptyText && StringUtils.isBlank(link.text())) {
					continue;
				}
				String href = link.attr("href");
				if (href.startsWith("/")) {
					href = uri.getScheme() + "://" + uri.getHost() + href;
				}
				final ScrapedLink scrapedLink = new ScrapedLink(href, link.text());
				scrapedLinks.add(scrapedLink);
			}
		}
		return scrapedLinks;
	}

	public ScrapedTable scrapeTable(final URI uri, final String tablePattern, final boolean regex) throws IOException, HttpException {
		final String html = DiskCache.get(uri, this.timeout);
		final Document doc = Jsoup.parse(html);

		Element table = null;
		if (StringUtils.isBlank(tablePattern) || regex) {
			final Elements tables = doc.select("table");
			if (regex) {
				if (tablePattern.startsWith("#")) {
					for (final Element candidate : tables) {
						if (candidate.id().matches(tablePattern)) {
							table = candidate;
							break;
						}
					}
				} else {
					throw new IllegalArgumentException("Unsupported regex pattern: " + tablePattern);
				}
			}
		} else {
			final Elements tables = doc.select(tablePattern);
			table = tables.get(0);
		}
		if (table == null) {
			log.warning("No table found");
			log.warning(html);
			return null;
		}

		log.finest("Found table");
		final ScrapedTable scrapedTable = new ScrapedTable();
		final Elements rows = table.select("tr");
		for (final Element row : rows) {
			final ScrapedRow scrapedRow = new ScrapedRow();
			scrapedRow.setCssClass(row.className());
			final Elements cells = row.select("td");
			for (final Element cell : cells) {
				final Elements links = table.select("a");
				String href = null;
				if (links.size() == 1) {
					href = links.get(0).attr("href");
				}
				final ScrapedCell scrapedCell = new ScrapedCell(cell.text(), href, cell.html());
				scrapedRow.add(scrapedCell);
			}
			scrapedTable.add(scrapedRow);
		}
		return scrapedTable;
	}
}
