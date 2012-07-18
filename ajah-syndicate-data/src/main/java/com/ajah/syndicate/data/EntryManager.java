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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.Entry;
import com.ajah.syndicate.EntryId;
import com.ajah.syndicate.FeedSourceId;
import com.ajah.util.AjahUtils;

/**
 * Manages persistence of {@link Entry}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Transactional(rollbackFor = Exception.class)
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

}
