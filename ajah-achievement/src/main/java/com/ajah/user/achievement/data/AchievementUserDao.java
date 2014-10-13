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

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.achievement.AchievementId;
import com.ajah.user.achievement.AchievementUser;
import com.ajah.user.achievement.AchievementUserId;
import com.ajah.user.achievement.AchievementUserStatus;
import com.ajah.user.achievement.AchievementUserType;

/**
 * DAO interface for {@link AchievementUser}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface AchievementUserDao extends AjahDao<AchievementUserId, AchievementUser> {

	/**
	 * Returns a list of {@link AchievementUser}s that match the specified
	 * criteria.
	 * 
	 * @param type
	 *            The type of achievementUser, optional.
	 * @param status
	 *            The status of the achievementUser, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link AchievementUser}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<AchievementUser> list(final AchievementUserType type, final AchievementUserStatus status, final long page, final long count) throws DataOperationException;

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The achievementUser type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	long count(final AchievementUserType type, final AchievementUserStatus status) throws DataOperationException;

	List<AchievementUser> findCompleted(final UserId userId, final int count) throws DataOperationException;

	/**
	 * Finds a user's achievement record.
	 * 
	 * @param userId
	 *            The user to look up.
	 * @param achievementId
	 *            The achievement to look up.
	 * @return The user's achievement progress, may be null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	AchievementUser find(UserId userId, AchievementId achievementId) throws DataOperationException;

}
