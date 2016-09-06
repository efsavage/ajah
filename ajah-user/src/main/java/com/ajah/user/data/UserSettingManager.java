/*
 *  Copyright 2013-2016 Eric F. Savage, code@efsavage.com
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.UserSetting;
import com.ajah.user.UserSettingId;
import com.ajah.user.UserSettingKey;
import com.ajah.user.UserSettingStatus;
import com.ajah.user.UserSettingType;
import com.ajah.user.audit.UserAuditField;
import com.ajah.user.audit.UserAuditType;
import com.ajah.user.audit.data.UserAuditManager;
import com.ajah.util.compare.CompareUtils;

/**
 * Manages data operations for {@link UserSetting}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
public class UserSettingManager {

	@Autowired
	private UserSettingDao userSettingDao;

	@Autowired
	private UserAuditManager userAuditManager;

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

	public UserSetting find(final UserId userId, final String name) throws DataOperationException {
		return this.userSettingDao.find(userId, name);
	}

	/**
	 * Returns a list of {@link UserSetting}s that match the specified criteria.
	 * 
	 * @param userId
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
	public List<UserSetting> list(final UserId userId, final UserSettingType type, final UserSettingStatus status, final long page, final long count) throws DataOperationException {
		return this.userSettingDao.list(userId, type, status, page, count);
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
	private DataOperationResult<UserSetting> save(final UserSetting userSetting) throws DataOperationException {
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

	public UserSetting set(final UserId userId, final String name, final boolean value, final UserId staffUserId, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException {
		return set(userId, name, String.valueOf(value), staffUserId, userComment, staffComment, ip, headers);
	}

	public UserSetting set(final UserId userId, final UserSettingKey key, final boolean value, final UserId staffUserId, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException {
		return set(userId, key.getName(), String.valueOf(value), staffUserId, userComment, staffComment, ip, headers);
	}

	public UserSetting set(final UserId userId, final UserSettingKey key, final String value, final UserId staffUserId, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException {
		return set(userId, key.getName(), value, staffUserId, userComment, staffComment, ip, headers);
	}

	public UserSetting set(final UserId userId, final String name, final Boolean value, final UserId staffUserId, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException {
		return set(userId, name, value == null ? null : String.valueOf(value), staffUserId, userComment, staffComment, ip, headers);
	}

	public UserSetting set(final UserId userId, final String name, final long value, final UserId staffUserId, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException {
		return 	set(userId, name, String.valueOf(value), staffUserId, userComment, staffComment, ip, headers);
	}

	public UserSetting set(final UserId userId, final String name, final String value, final UserId staffUserId, String userComment, String staffComment, String ip, String headers)
			throws DataOperationException {
		UserSetting userSetting = find(userId, name);
		if (userSetting == null) {
			userSetting = new UserSetting();
			userSetting.setName(name);
			userSetting.setUserId(userId);
			userSetting.setType(UserSettingType.STANDARD);
			userSetting.setStatus(UserSettingStatus.ACTIVE);
		}
		if (CompareUtils.compare(value, userSetting.getValue(), true) == 0) {
			return userSetting;
		}
		String oldValue = userSetting.getValue();
		userSetting.setValue(value);
		save(userSetting);
		this.userAuditManager.create(userId, staffUserId, UserAuditField.USER_SETTING, oldValue, value, staffUserId == null ? UserAuditType.USER : UserAuditType.ADMIN, userComment, staffComment, ip,
				headers);
		return userSetting;
	}

	/**
	 * Finds a {@link UserSetting} for a specific user/key combination.
	 * 
	 * @param userId
	 *            The user to look up.
	 * @param key
	 *            The name of the setting to look up.
	 * @return The setting, if found, otherwise null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public UserSetting find(UserId userId, UserSettingKey key) throws DataOperationException {
		return find(userId, key.getName());
	}

	/**
	 * Finds any settings for a specific name/value combination.
	 * 
	 * @param key
	 *            The key to look up.
	 * @param value
	 *            The value to match on.
	 * @return A list of user settings, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<UserSetting> list(UserSettingKey key, String value) throws DataOperationException {
		return this.userSettingDao.list(key, value);
	}

}
