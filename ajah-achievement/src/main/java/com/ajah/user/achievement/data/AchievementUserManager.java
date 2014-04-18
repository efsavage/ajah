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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.achievement.Achievement;
import com.ajah.user.achievement.AchievementId;
import com.ajah.user.achievement.AchievementUser;
import com.ajah.user.achievement.AchievementUserId;
import com.ajah.user.achievement.AchievementUserStatus;
import com.ajah.user.achievement.AchievementUserType;

/**
 * Manages data operations for {@link AchievementUser}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class AchievementUserManager {

	@Autowired
	private AchievementUserDao achievementUserDao;

	@Autowired
	private AchievementManager achievementManager;

	public List<AchievementUser> checkAcheivements(final User user, final String tag) {
		final List<AchievementUser> achievementUsers = new ArrayList<>();
		return achievementUsers;
	}

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
	 *            The achievementUser type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final AchievementUserType type, final AchievementUserStatus status) throws DataOperationException {
		return this.achievementUserDao.count(type, status);
	}

	/**
	 * Creates a new {@link AchievementUser} with the given properties.
	 * 
	 * @param achievementId
	 *            The ID of the {@link Achievement} being tracked.
	 * @param userId
	 *            The ID of the {@link User} being tracked.
	 * @param type
	 *            The type of achievementUser, required.
	 * @param status
	 *            The status of the achievementUser, required.
	 * @return The result of the creation, which will include the new
	 *         achievementUser at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AchievementUser> create(final AchievementId achievementId, final UserId userId, final AchievementUserType type, final AchievementUserStatus status)
			throws DataOperationException {
		final AchievementUser achievementUser = new AchievementUser();
		achievementUser.setAchievementId(achievementId);
		achievementUser.setUserId(userId);
		achievementUser.setType(type);
		achievementUser.setStatus(status);
		final DataOperationResult<AchievementUser> result = save(achievementUser);
		return result;
	}

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
	public List<AchievementUser> list(final AchievementUserType type, final AchievementUserStatus status, final long page, final long count) throws DataOperationException {
		return this.achievementUserDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link AchievementUser} by it's ID.
	 * 
	 * @param achievementUserId
	 *            The ID to load, required.
	 * @return The matching achievementUser, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AchievementUserNotFoundException
	 *             If the ID specified did not match any achievementUsers.
	 */
	public AchievementUser load(final AchievementUserId achievementUserId) throws DataOperationException, AchievementUserNotFoundException {
		final AchievementUser achievementUser = this.achievementUserDao.load(achievementUserId);
		if (achievementUser == null) {
			throw new AchievementUserNotFoundException(achievementUserId);
		}
		return achievementUser;
	}

	/**
	 * Saves an {@link AchievementUser}. Assigns a new ID ({@link UUID}) and
	 * sets the creation date if necessary. If either of these elements are set,
	 * will perform an insert. Otherwise will perform an update.
	 * 
	 * @param achievementUser
	 *            The achievementUser to save.
	 * @return The result of the save operation, which will include the new
	 *         achievementUser at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AchievementUser> save(final AchievementUser achievementUser) throws DataOperationException {
		boolean create = false;
		if (achievementUser.getId() == null) {
			achievementUser.setId(new AchievementUserId(UUID.randomUUID().toString()));
			create = true;
		}
		if (achievementUser.getCreated() == null) {
			achievementUser.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<AchievementUser> result = this.achievementUserDao.insert(achievementUser);
			log.fine("Created AchievementUser " + achievementUser.getId());
			return result;
		}
		final DataOperationResult<AchievementUser> result = this.achievementUserDao.update(achievementUser);
		log.fine("Updated AchievementUser " + achievementUser.getId());
		return result;
	}

	public List<AchievementUser> findCompleted(UserId userId, int count, boolean loadAchievements) throws DataOperationException, AchievementNotFoundException {
		List<AchievementUser> achievementUsers = this.achievementUserDao.findCompleted(userId, count);
		if (loadAchievements) {
			for (AchievementUser achievementUser : achievementUsers) {
				achievementUser.setAchievement(this.achievementManager.load(achievementUser.getAchievementId()));
			}
		}
		return achievementUsers;
	}

}
