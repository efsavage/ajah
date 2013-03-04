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
import org.springframework.transaction.annotation.Transactional;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.Entry;
import com.ajah.syndicate.EntryId;
import com.ajah.syndicate.FeedSourceId;
import com.ajah.util.AjahUtils;
import com.ajah.util.data.HashUtils;

/**
 * Manages persistence of {@link Entry}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log
public class EntryManager {

	@Autowired
	private EntryDao entryDao;

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
	public Entry findByHtmlUrlSha1(final FeedSourceId feedSourceId, final String htmlUrlSha1) throws DataOperationException {
		// TODO this field may not necessarily be unique, order by something?
		return this.entryDao.findByHtmlUrlSha1(feedSourceId, htmlUrlSha1);
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
	public void save(final Entry entry) throws DataOperationException {
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
			entry.setId(new EntryId(UUID.randomUUID().toString()));
			this.entryDao.insert(entry);
		} else {
			this.entryDao.update(entry);
		}
	}

	/**
	 * This will attempt to match an Entry to avoid duplicates.
	 * 
	 * @param entry
	 *            The entry to try and match.
	 * @return The entry or the matched replacement.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	public Entry matchAndSave(Entry entry) throws DataOperationException {
		Entry match = this.entryDao.findMatch(entry.getFeedSourceId(), entry.getHtmlUrlSha1(), entry.getContentSha1());
		if (match != null) {
			log.finest("Exact match found replacing");
			return match;
		}
		// Lets see if this is an update
		match = this.entryDao.findByHtmlUrlSha1(entry.getFeedSourceId(), entry.getHtmlUrlSha1());
		if (match != null) {
			match.setContent(entry.getContent());
			match.setContentSha1(HashUtils.sha1Hex(entry.getContent()));
			match.setFeedId(entry.getFeedId());
			log.finest("Match found updating and replacing");
			save(match);
			return match;
		}
		// TODO match on title or other fields?
		log.finest("Appears to be a new post");
		save(entry);
		return entry;
	}

	/**
	 * Loads an entry by its ID.
	 * 
	 * @param entryId
	 *            The ID to load.
	 * @return The entry, will not be null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws EntryNotFoundException
	 *             If no entry could be found with the specified ID.
	 */
	public Entry load(EntryId entryId) throws DataOperationException, EntryNotFoundException {
		Entry entry = this.entryDao.load(entryId);
		if (entry == null) {
			throw new EntryNotFoundException(entryId);
		}
		return entry;
	}
	
}
