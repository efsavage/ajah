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
import com.ajah.user.achievement.Achievement;
import com.ajah.user.achievement.AchievementId;
import com.ajah.user.achievement.AchievementStatus;
import com.ajah.user.achievement.AchievementType;

/**
 * MySQL-based implementation of {@link AchievementDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class AchievementDaoImpl extends AbstractAjahDao<AchievementId, Achievement, Achievement> implements AchievementDao {

	/**
	 * @see com.ajah.user.achievement.data.AchievementDao#count(com.ajah.user.achievement.AchievementType,
	 *      com.ajah.user.achievement.AchievementStatus)
	 */
	@Override
	public long count(final AchievementType type, final AchievementStatus status) throws DataOperationException {
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
	public List<Achievement> list(final AchievementType type, final AchievementStatus status, final long page, final long count) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("name", Order.ASC));
	}

}
