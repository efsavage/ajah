/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserSetting;
import com.ajah.user.UserSettingId;
import com.ajah.user.UserSettingStatus;
import com.ajah.user.UserSettingType;

/**
 * Manages data operations for {@link UserSetting}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class UserSettingManager {

	@Autowired
	private UserSettingDao userSettingDao;

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
	 *            The userSetting type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final UserSettingType type, final UserSettingStatus status) throws DataOperationException {
		return this.userSettingDao.count(type, status);
	}

	/**
	 * Creates a new {@link UserSetting} with the given properties.
	 * 
	 * @param name
	 *            The name of the userSetting, required.
	 * @param type
	 *            The type of userSetting, required.
	 * @param status
	 *            The status of the userSetting, required.
	 * @return The result of the creation, which will include the new
	 *         userSetting at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserSetting> create(final String name, final UserSettingType type, final UserSettingStatus status) throws DataOperationException {
		final UserSetting userSetting = new UserSetting();
		userSetting.setName(name);
		userSetting.setType(type);
		userSetting.setStatus(status);
		final DataOperationResult<UserSetting> result = save(userSetting);
		return result;
	}

	/**
	 * Marks the entity as {@link UserSettingStatus#DELETED}.
	 * 
	 * @param userSettingId
	 *            The ID of the userSetting to delete.
	 * @return The result of the deletion, will not include the new userSetting
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserSettingNotFoundException
	 *             If the ID specified did not match any userSettings.
	 */
	public DataOperationResult<UserSetting> delete(final UserSettingId userSettingId) throws DataOperationException, UserSettingNotFoundException {
		final UserSetting userSetting = load(userSettingId);
		userSetting.setStatus(UserSettingStatus.DELETED);
		final DataOperationResult<UserSetting> result = save(userSetting);
		return result;
	}

	/**
	 * Returns a list of {@link UserSetting}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of userSetting, optional.
	 * @param status
	 *            The status of the userSetting, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link UserSetting}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<UserSetting> list(final UserSettingType type, final UserSettingStatus status, final long page, final long count) throws DataOperationException {
		return this.userSettingDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link UserSetting} by it's ID.
	 * 
	 * @param userSettingId
	 *            The ID to load, required.
	 * @return The matching userSetting, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserSettingNotFoundException
	 *             If the ID specified did not match any userSettings.
	 */
	public UserSetting load(final UserSettingId userSettingId) throws DataOperationException, UserSettingNotFoundException {
		final UserSetting userSetting = this.userSettingDao.load(userSettingId);
		if (userSetting == null) {
			throw new UserSettingNotFoundException(userSettingId);
		}
		return userSetting;
	}

	/**
	 * Saves an {@link UserSetting}. Assigns a new ID ({@link UUID}) and sets
	 * the creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param userSetting
	 *            The userSetting to save.
	 * @return The result of the save operation, which will include the new
	 *         userSetting at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserSetting> save(final UserSetting userSetting) throws DataOperationException {
		boolean create = false;
		if (userSetting.getId() == null) {
			userSetting.setId(new UserSettingId(UUID.randomUUID().toString()));
			create = true;
		}
		if (userSetting.getCreated() == null) {
			userSetting.setCreated(new Date());
			create = true;
		}
		if (create) {
			return this.userSettingDao.insert(userSetting);
		}
		final DataOperationResult<UserSetting> result = this.userSettingDao.update(userSetting);
		return result;
	}

}
