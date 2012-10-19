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

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.syndicate.Entry;
import com.ajah.syndicate.EntryId;
import com.ajah.syndicate.FeedSourceId;

/**
 * Dao for {@link Entry}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public class EntryDaoImpl extends AbstractAjahDao<EntryId, Entry, Entry> implements EntryDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entry findByHtmlUrlSha1(final FeedSourceId feedSourceId, final String htmlUrlSha1) throws DataOperationException {
		return find(new Criteria().eq("feed_source_id", feedSourceId).eq("html_url_sha_1", htmlUrlSha1));
	}

}
