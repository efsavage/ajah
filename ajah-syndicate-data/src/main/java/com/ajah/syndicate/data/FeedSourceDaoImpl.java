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
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.FeedSourceId;
import com.ajah.syndicate.PollStatus;

/**
 * Dao for {@link FeedSource}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public class FeedSourceDaoImpl extends AbstractAjahDao<FeedSourceId, FeedSource, FeedSource> implements FeedSourceDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FeedSource findByFeedUrlSha1(final String feedUrlSha1) {
		return findByField("feed_url_sha_1", feedUrlSha1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FeedSource getStaleFeedSource() {
		return findByWhere("poll_status=" + PollStatus.ACTIVE.getId() + " AND next_poll_date < (unix_timestamp() * 1000)");
	}

}
