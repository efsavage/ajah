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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.achievement.Achievement;
import com.ajah.user.achievement.AchievementId;
import com.ajah.user.achievement.AchievementStatus;
import com.ajah.user.achievement.AchievementType;

/**
 * Manages data operations for {@link Achievement}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class AchievementManager {

	@Autowired
	private AchievementDao achievementDao;

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count() throws DataOperationException {
		return count(null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The achievement type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final AchievementType type, final AchievementStatus status) throws DataOperationException {
		return this.achievementDao.count(type, status);
	}

	/**
	 * Creates a new {@link Achievement} with the given properties.
	 * 
	 * @param name
	 *            The name of the achievement, required.
	 * @param type
	 *            The type of achievement, required.
	 * @param status
	 *            The status of the achievement, required.
	 * @return The result of the creation, which will include the new
	 *         achievement at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Achievement> create(final String name, final AchievementType type, final AchievementStatus status) throws DataOperationException {
		final Achievement achievement = new Achievement();
		achievement.setName(name);
		achievement.setType(type);
		achievement.setStatus(status);
		return save(achievement);
	}

	/**
	 * Marks the entity as {@link AchievementStatus#DELETED}.
	 * 
	 * @param achievementId
	 *            The ID of the achievement to delete.
	 * @return The result of the deletion, will not include the new achievement
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AchievementNotFoundException
	 *             If the ID specified did not match any achievements.
	 */
	public DataOperationResult<Achievement> delete(final AchievementId achievementId) throws DataOperationException, AchievementNotFoundException {
		final Achievement achievement = load(achievementId);
		achievement.setStatus(AchievementStatus.DELETED);
		return save(achievement);
	}

	/**
	 * Returns a list of {@link Achievement}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of achievement, optional.
	 * @param status
	 *            The status of the achievement, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Achievement}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Achievement> list(final AchievementType type, final AchievementStatus status, final long page, final long count) throws DataOperationException {
		return this.achievementDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link Achievement} by it's ID.
	 * 
	 * @param achievementId
	 *            The ID to load, required.
	 * @return The matching achievement, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AchievementNotFoundException
	 *             If the ID specified did not match any achievements.
	 */
	public Achievement load(final AchievementId achievementId) throws DataOperationException, AchievementNotFoundException {
		final Achievement achievement = this.achievementDao.load(achievementId);
		if (achievement == null) {
			throw new AchievementNotFoundException(achievementId);
		}
		return achievement;
	}

	/**
	 * Saves an {@link Achievement}. Assigns a new ID ({@link UUID}) and sets
	 * the creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param achievement
	 *            The achievement to save.
	 * @return The result of the save operation, which will include the new
	 *         achievement at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Achievement> save(final Achievement achievement) throws DataOperationException {
		boolean create = false;
		if (achievement.getId() == null) {
			achievement.setId(new AchievementId(UUID.randomUUID().toString()));
			create = true;
		}
		if (achievement.getCreated() == null) {
			achievement.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<Achievement> result = this.achievementDao.insert(achievement);
			log.fine("Created Achievement " + achievement.getName() + " [" + achievement.getId() + "]");
			return result;
		}
		final DataOperationResult<Achievement> result = this.achievementDao.update(achievement);
		log.fine("Updated Achievement " + achievement.getName() + " [" + achievement.getId() + "]");
		return result;
	}

}
