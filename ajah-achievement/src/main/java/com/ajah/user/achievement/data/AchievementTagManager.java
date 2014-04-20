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
import com.ajah.user.achievement.AchievementTag;
import com.ajah.user.achievement.AchievementTagId;
import com.ajah.user.achievement.AchievementTagStatus;
import com.ajah.user.achievement.AchievementTagType;

/**
 * Manages data operations for {@link AchievementTag}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class AchievementTagManager {

	@Autowired
	private AchievementTagDao achievementTagDao;

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
	 *            The achievementTag type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final AchievementTagType type, final AchievementTagStatus status) throws DataOperationException {
		return this.achievementTagDao.count(type, status);
	}

	/**
	 * Creates a new {@link AchievementTag} with the given properties.
	 * 
	 * @param name
	 *            The name of the achievementTag, required.
	 * @param type
	 *            The type of achievementTag, required.
	 * @param status
	 *            The status of the achievementTag, required.
	 * @return The result of the creation, which will include the new
	 *         achievementTag at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AchievementTag> create(final String name, final AchievementTagType type, final AchievementTagStatus status) throws DataOperationException {
		final AchievementTag achievementTag = new AchievementTag();
		achievementTag.setName(name);
		achievementTag.setType(type);
		achievementTag.setStatus(status);
		final DataOperationResult<AchievementTag> result = save(achievementTag);
		return result;
	}

	/**
	 * Marks the entity as {@link AchievementTagStatus#DELETED}.
	 * 
	 * @param achievementTagId
	 *            The ID of the achievementTag to delete.
	 * @return The result of the deletion, will not include the new
	 *         achievementTag at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AchievementTagNotFoundException
	 *             If the ID specified did not match any achievementTags.
	 */
	public DataOperationResult<AchievementTag> delete(final AchievementTagId achievementTagId) throws DataOperationException, AchievementTagNotFoundException {
		final AchievementTag achievementTag = load(achievementTagId);
		achievementTag.setStatus(AchievementTagStatus.DELETED);
		final DataOperationResult<AchievementTag> result = save(achievementTag);
		return result;
	}

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
	public List<AchievementTag> list(final AchievementTagType type, final AchievementTagStatus status, final long page, final long count) throws DataOperationException {
		return this.achievementTagDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link AchievementTag} by it's ID.
	 * 
	 * @param achievementTagId
	 *            The ID to load, required.
	 * @return The matching achievementTag, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AchievementTagNotFoundException
	 *             If the ID specified did not match any achievementTags.
	 */
	public AchievementTag load(final AchievementTagId achievementTagId) throws DataOperationException, AchievementTagNotFoundException {
		final AchievementTag achievementTag = this.achievementTagDao.load(achievementTagId);
		if (achievementTag == null) {
			throw new AchievementTagNotFoundException(achievementTagId);
		}
		return achievementTag;
	}

	/**
	 * Saves an {@link AchievementTag}. Assigns a new ID ({@link UUID}) and sets
	 * the creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param achievementTag
	 *            The achievementTag to save.
	 * @return The result of the save operation, which will include the new
	 *         achievementTag at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AchievementTag> save(final AchievementTag achievementTag) throws DataOperationException {
		boolean create = false;
		if (achievementTag.getId() == null) {
			achievementTag.setId(new AchievementTagId(UUID.randomUUID().toString()));
			create = true;
		}
		if (achievementTag.getCreated() == null) {
			achievementTag.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<AchievementTag> result = this.achievementTagDao.insert(achievementTag);
			log.fine("Created AchievementTag " + achievementTag.getName() + " [" + achievementTag.getId() + "]");
			return result;
		}
		final DataOperationResult<AchievementTag> result = this.achievementTagDao.update(achievementTag);
		log.fine("Updated AchievementTag " + achievementTag.getName() + " [" + achievementTag.getId() + "]");
		return result;
	}

}
