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
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.FeedEntry;
import com.ajah.syndicate.FeedEntryId;
import com.ajah.syndicate.FeedSourceId;
import com.ajah.util.AjahUtils;
import com.ajah.util.data.HashUtils;

/**
 * Manages persistence of {@link FeedEntry}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Log
public class FeedEntryManager {

	@Autowired
	private FeedEntryDao entryDao;

	/**
	 * Fetches an Entry by the SHA-1 of it's html url.
	 * 
	 * @param feedSourceId
	 *            The ID of the FeedSource this entry is from.
	 * @param htmlUrlSha1
	 *            The SHA-1 of the Entry's html url.
	 * @return The Entry, if found, otherwise null.
	 * @throws DataOperationException
	 */
	public FeedEntry findByHtmlUrlSha1(final FeedSourceId feedSourceId, final String htmlUrlSha1) throws DataOperationException {
		// TODO this field may not necessarily be unique, order by something?
		return this.entryDao.findByHtmlUrlSha1(feedSourceId, htmlUrlSha1);
	}

	/**
	 * Finds a list of entries for a feed source with a specified set of
	 * tags/categories.
	 * 
	 * @param feedSourceId
	 *            The feed source to search on.
	 * @param categories
	 *            The categories the entries must have.
	 * @param orCategories
	 *            If true, the categories are an OR and only one must match,
	 *            otherwise the entry must match all of them.
	 * @return List of matching entries.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<FeedEntry> list(final FeedSourceId feedSourceId, final String[] categories, final boolean orCategories) throws DataOperationException {
		return this.entryDao.list(feedSourceId, categories, orCategories);
	}

	/**
	 * Loads an entry by its ID.
	 * 
	 * @param entryId
	 *            The ID to load.
	 * @return The entry, will not be null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws FeedEntryNotFoundException
	 *             If no entry could be found with the specified ID.
	 */
	public FeedEntry load(final FeedEntryId entryId) throws DataOperationException, FeedEntryNotFoundException {
		final FeedEntry entry = this.entryDao.load(entryId);
		if (entry == null) {
			throw new FeedEntryNotFoundException(entryId);
		}
		return entry;
	}

	/**
	 * This will attempt to match an Entry to avoid duplicates.
	 * 
	 * @param candidate
	 *            The entry to try and match.
	 * @return The entry or the matched replacement.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	public FeedEntry matchAndSave(final FeedEntry candidate) throws DataOperationException {
		FeedEntry entry = this.entryDao.findMatch(candidate.getFeedSourceId(), candidate.getHtmlUrlSha1(), candidate.getContentSha1());
		if (entry == null) {
			entry = this.entryDao.findByHtmlUrlSha1(candidate.getFeedSourceId(), candidate.getHtmlUrlSha1());
		}
		if (entry == null) {
			entry = new FeedEntry();
		}
		int hash = entry.hashCode();
		entry.setContent(candidate.getContent());
		entry.setDescription(candidate.getDescription());
		entry.setCategories(candidate.getCategories());
		entry.setContentSha1(HashUtils.sha1Hex(candidate.getContent() + candidate.getDescription() + candidate.getCategories()));
		entry.setFeedId(candidate.getFeedId());
		if (entry.hashCode() != hash) {
			save(entry);
		}
		save(entry);
		return entry;
	}

	/**
	 * Saves an entry, inserting if the ID is not set, otherwise updating. Will
	 * set created date if that is null.
	 * 
	 * @param entry
	 *            The entry to save.
	 * @throws DataOperationException
	 *             if the entry could not be saved.
	 */
	public void save(final FeedEntry entry) throws DataOperationException {
		if (entry.getPublished().getTime() < 0) {
			log.warning("Very old date, ignoring");
			entry.setPublished(new Date(0));
		}
		AjahUtils.requireParam(entry.getFeedId(), "entry.feedId");
		AjahUtils.requireParam(entry.getFeedSourceId(), "entry.feedSourceId");
		if (entry.getCreated() == null) {
			entry.setCreated(new Date());
		}
		if (entry.getId() == null) {
			entry.setId(new FeedEntryId(UUID.randomUUID().toString()));
			this.entryDao.insert(entry);
		} else {
			this.entryDao.update(entry);
		}
	}

}
