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
package com.ajah.user.account.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountSetting;
import com.ajah.user.account.AccountSettingId;
import com.ajah.user.account.AccountSettingStatus;
import com.ajah.user.account.AccountSettingType;
import com.ajah.util.BooleanUtils;

/**
 * Manages data operations for {@link AccountSetting}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
public class AccountSettingManager {

	@Autowired
	private AccountSettingDao accountSettingDao;

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
	 *            The accountSetting type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final AccountSettingType type, final AccountSettingStatus status) throws DataOperationException {
		return this.accountSettingDao.count(type, status);
	}

	/**
	 * Creates a new {@link AccountSetting} with the given properties.
	 * 
	 * @param name
	 *            The name of the accountSetting, required.
	 * @param type
	 *            The type of accountSetting, required.
	 * @param status
	 *            The status of the accountSetting, required.
	 * @return The result of the creation, which will include the new
	 *         accountSetting at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AccountSetting> create(final String name, final AccountSettingType type, final AccountSettingStatus status) throws DataOperationException {
		final AccountSetting accountSetting = new AccountSetting();
		accountSetting.setName(name);
		accountSetting.setType(type);
		accountSetting.setStatus(status);
		return save(accountSetting);
	}

	/**
	 * Marks the entity as {@link AccountSettingStatus#DELETED}.
	 * 
	 * @param accountSettingId
	 *            The ID of the accountSetting to delete.
	 * @return The result of the deletion, will not include the new
	 *         accountSetting at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AccountSettingNotFoundException
	 *             If the ID specified did not match any accountSettings.
	 */
	public DataOperationResult<AccountSetting> delete(final AccountSettingId accountSettingId) throws DataOperationException, AccountSettingNotFoundException {
		final AccountSetting accountSetting = load(accountSettingId);
		accountSetting.setStatus(AccountSettingStatus.DELETED);
		return save(accountSetting);
	}

	private AccountSetting find(final AccountId accountId, final String name) throws DataOperationException {
		return this.accountSettingDao.find(accountId, name);
	}

	public boolean getBoolean(final AccountId accountId, final String name, final boolean defaultValue) throws DataOperationException {
		final AccountSetting accountSetting = find(accountId, name);
		if (accountSetting == null) {
			return defaultValue;
		}
		return BooleanUtils.coerce(accountSetting.getValue(), defaultValue);
	}

	public List<AccountSetting> list(final AccountId accountId) throws DataOperationException {
		return this.accountSettingDao.list(accountId);
	}

	/**
	 * Returns a list of {@link AccountSetting}s that match the specified
	 * criteria.
	 * 
	 * @param type
	 *            The type of accountSetting, optional.
	 * @param status
	 *            The status of the accountSetting, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link AccountSetting}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<AccountSetting> list(final AccountSettingType type, final AccountSettingStatus status, final long page, final long count) throws DataOperationException {
		return this.accountSettingDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link AccountSetting} by it's ID.
	 * 
	 * @param accountSettingId
	 *            The ID to load, required.
	 * @return The matching accountSetting, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AccountSettingNotFoundException
	 *             If the ID specified did not match any accountSettings.
	 */
	public AccountSetting load(final AccountSettingId accountSettingId) throws DataOperationException, AccountSettingNotFoundException {
		final AccountSetting accountSetting = this.accountSettingDao.load(accountSettingId);
		if (accountSetting == null) {
			throw new AccountSettingNotFoundException(accountSettingId);
		}
		return accountSetting;
	}

	/**
	 * Saves an {@link AccountSetting}. Assigns a new ID ({@link UUID}) and sets
	 * the creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param accountSetting
	 *            The accountSetting to save.
	 * @return The result of the save operation, which will include the new
	 *         accountSetting at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AccountSetting> save(final AccountSetting accountSetting) throws DataOperationException {
		boolean create = false;
		if (accountSetting.getId() == null) {
			accountSetting.setId(new AccountSettingId(UUID.randomUUID().toString()));
			create = true;
		}
		if (accountSetting.getCreated() == null) {
			accountSetting.setCreated(new Date());
			create = true;
		}
		if (create) {
			return this.accountSettingDao.insert(accountSetting);
		}
		return this.accountSettingDao.update(accountSetting);
	}
}
