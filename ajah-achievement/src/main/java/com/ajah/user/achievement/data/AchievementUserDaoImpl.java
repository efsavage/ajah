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
import com.ajah.user.UserId;
import com.ajah.user.achievement.AchievementId;
import com.ajah.user.achievement.AchievementUser;
import com.ajah.user.achievement.AchievementUserId;
import com.ajah.user.achievement.AchievementUserStatus;
import com.ajah.user.achievement.AchievementUserType;

/**
 * MySQL-based implementation of {@link AchievementUserDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class AchievementUserDaoImpl extends AbstractAjahDao<AchievementUserId, AchievementUser, AchievementUser> implements AchievementUserDao {

	/**
	 * @see com.ajah.user.achievement.data.AchievementUserDao#count(com.ajah.user.achievement.AchievementUserType,
	 *      com.ajah.user.achievement.AchievementUserStatus)
	 */
	@Override
	public long count(final AchievementUserType type, final AchievementUserStatus status) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.user.achievement.data.AchievementUserDao#findCompleted(com.ajah.user.UserId,
	 *      int)
	 */
	@Override
	public List<AchievementUser> findCompleted(final UserId userId, final int count) throws DataOperationException {
		final Criteria criteria = new Criteria().eq(userId).eq("status", AchievementUserStatus.COMPLETED).orderBy("completed_date", Order.DESC).rows(count);
		return super.list(criteria);
	}

	@Override
	public List<AchievementUser> list(final AchievementUserType type, final AchievementUserStatus status, final long page, final long count) throws DataOperationException {
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
	 * @see com.ajah.user.achievement.data.AchievementUserDao#find(UserId,
	 *      AchievementId)
	 */
	@Override
	public AchievementUser find(UserId userId, AchievementId achievementId) throws DataOperationException {
		return super.find(userId, achievementId);
	}

}
