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
import com.ajah.user.achievement.AchievementId;
import com.ajah.user.achievement.AchievementTag;
import com.ajah.user.achievement.AchievementTagId;
import com.ajah.user.achievement.AchievementTagStatus;
import com.ajah.user.achievement.AchievementTagType;

/**
 * DAO interface for {@link AchievementTag}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface AchievementTagDao extends AjahDao<AchievementTagId, AchievementTag> {

	/**
	 * Returns a list of {@link AchievementTag}s that match the specified
	 * criteria.
	 * 
	 * @param type
	 *            The type of achievementTag, optional.
	 * @param status
	 *            The status of the achievementTag, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link AchievementTag}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<AchievementTag> list(final AchievementTagType type, final AchievementTagStatus status, final long page, final long count) throws DataOperationException;

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The achievementTag type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	long count(final AchievementTagType type, final AchievementTagStatus status) throws DataOperationException;

	/**
	 * Lists the tags matching an achievement.
	 * 
	 * @param achievementId
	 *            The ID of the achievement to match.
	 * @return The list of tags, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<AchievementTag> list(AchievementId achievementId) throws DataOperationException;

}
