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
package com.ajah.user.achievement.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.achievement.AchievementId;
import com.ajah.user.achievement.AchievementTag;
import com.ajah.user.achievement.AchievementTagId;
import com.ajah.user.achievement.AchievementTagStatus;
import com.ajah.user.achievement.AchievementTagType;

/**
 * MySQL-based implementation of {@link AchievementTagDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class AchievementTagDaoImpl extends AbstractAjahDao<AchievementTagId, AchievementTag, AchievementTag> implements AchievementTagDao {

	/**
	 * @see com.ajah.user.achievement.data.AchievementTagDao#count(com.ajah.user.achievement.AchievementTagType,
	 *      com.ajah.user.achievement.AchievementTagStatus)
	 */
	@Override
	public long count(final AchievementTagType type, final AchievementTagStatus status) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	@Override
	public List<AchievementTag> list(final AchievementTagType type, final AchievementTagStatus status, final long page, final long count) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("name", Order.ASC));
	}

	/**
	 * @see com.ajah.user.achievement.data.AchievementTagDao#list(AchievementId)
	 */
	@Override
	public List<AchievementTag> list(AchievementId achievementId) throws DataOperationException {
		return super.list(achievementId);
	}

}
