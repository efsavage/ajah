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
package com.ajah.user.social.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.social.UserFollow;
import com.ajah.user.social.UserFollowId;
import com.ajah.user.social.UserFollowStatus;
import com.ajah.user.social.UserFollowType;

/**
 * Manages data operations for {@link UserFollow}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class UserFollowManager {

	@Autowired
	private UserFollowDao userFollowDao;

	/**
	 * Saves an {@link UserFollow}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param userFollow
	 *            The userFollow to save.
	 * @return The result of the save operation, which will include the new
	 *         userFollow at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserFollow> save(UserFollow userFollow) throws DataOperationException {
		boolean create = false;
		if (userFollow.getId() == null) {
			userFollow.setId(new UserFollowId(UUID.randomUUID().toString()));
			create = true;
		}
		if (userFollow.getCreated() == null) {
			userFollow.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<UserFollow> result = this.userFollowDao.insert(userFollow);
			log.fine("Created UserFollow " + userFollow.getId());
			return result;
		}
		DataOperationResult<UserFollow> result = this.userFollowDao.update(userFollow);
		log.fine("Updated UserFollow " + userFollow.getId());
		return result;
	}

	/**
	 * Loads an {@link UserFollow} by it's ID.
	 * 
	 * @param userFollowId
	 *            The ID to load, required.
	 * @return The matching userFollow, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserFollowNotFoundException
	 *             If the ID specified did not match any userFollows.
	 */
	public UserFollow load(UserFollowId userFollowId) throws DataOperationException, UserFollowNotFoundException {
		UserFollow userFollow = this.userFollowDao.load(userFollowId);
		if (userFollow == null) {
			throw new UserFollowNotFoundException(userFollowId);
		}
		return userFollow;
	}

	/**
	 * Returns a list of {@link UserFollow}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of userFollow, optional.
	 * @param status
	 *            The status of the userFollow, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link UserFollow}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<UserFollow> list(UserFollowType type, UserFollowStatus status, long page, long count) throws DataOperationException {
		return this.userFollowDao.list(type, status, page, count);
	}

	/**
	 * Creates a new {@link UserFollow} with the given properties.
	 * 
	 * @param name
	 *            The name of the userFollow, required.
	 * @param type
	 *            The type of userFollow, required.
	 * @param status
	 *            The status of the userFollow, required.
	 * @return The result of the creation, which will include the new userFollow
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserFollow> create(UserId userId, UserId followedUserId, UserFollowType type, UserFollowStatus status) throws DataOperationException {
		UserFollow userFollow = new UserFollow();
		userFollow.setUserId(userId);
		userFollow.setFollowedUserId(followedUserId);
		userFollow.setType(type);
		userFollow.setStatus(status);
		DataOperationResult<UserFollow> result = save(userFollow);
		return result;
	}

	/**
	 * Marks the entity as {@link UserFollowStatus#DELETED}.
	 * 
	 * @param userFollowId
	 *            The ID of the userFollow to delete.
	 * @return The result of the deletion, will not include the new userFollow
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserFollowNotFoundException
	 *             If the ID specified did not match any userFollows.
	 */
	public DataOperationResult<UserFollow> delete(UserFollowId userFollowId) throws DataOperationException, UserFollowNotFoundException {
		UserFollow userFollow = load(userFollowId);
		userFollow.setStatus(UserFollowStatus.DELETED);
		DataOperationResult<UserFollow> result = save(userFollow);
		return result;
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
	 *            The userFollow type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final UserFollowType type, final UserFollowStatus status) throws DataOperationException {
		return this.userFollowDao.count(type, status);
	}

}
