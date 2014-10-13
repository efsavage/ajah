/*
 *  Copyright 2012-2013 Eric F. Savage, code@efsavage.com
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

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.FeedEntry;
import com.ajah.syndicate.FeedEntryId;
import com.ajah.syndicate.FeedSourceId;

/**
 * Dao for {@link FeedEntry}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public interface FeedEntryDao extends AjahDao<FeedEntryId, FeedEntry> {

	/**
	 * Update an entry that already exists.
	 * 
	 * @param entry
	 *            The entry to update.
	 * @return The number of rows affected by the update.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	@Override
	DataOperationResult<FeedEntry> update(final FeedEntry entry) throws DataOperationException;

	/**
	 * Finds an entry by {@link FeedEntry#getHtmlUrlSha1()} and feed source.
	 * 
	 * @param feedSourceId
	 *            The feed source.
	 * @param htmlUrlSha1
	 *            The SHA-1 of the URL.
	 * @return The matching entry, if found, otherwise null.
	 * @throws DataOperationException
	 */
	FeedEntry findByHtmlUrlSha1(final FeedSourceId feedSourceId, final String htmlUrlSha1) throws DataOperationException;

	FeedEntry findMatch(final FeedSourceId feedId, final String htmlUrlSha1, final String contentSha1) throws DataOperationException;

	List<FeedEntry> list(FeedSourceId feedSourceId, String[] categories, boolean orCategories) throws DataOperationException;

}
