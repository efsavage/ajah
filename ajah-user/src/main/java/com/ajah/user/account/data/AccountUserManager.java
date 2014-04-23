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
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountUser;
import com.ajah.user.account.AccountUserId;
import com.ajah.user.account.AccountUserStatus;
import com.ajah.user.account.AccountUserType;

/**
 * Manages data operations for {@link AccountUser}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
public class AccountUserManager {

	@Autowired
	private AccountUserDao accountUserDao;

	/**
	 * Creates a new {@link AccountUser} with the given properties.
	 * 
	 * @param userId
	 *            The user ID
	 * @param accountId
	 *            The account ID
	 * @param type
	 *            The type of accountUser, required.
	 * @param status
	 *            The status of the accountUser, required.
	 * @return The result of the creation, which will include the new
	 *         accountUser at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AccountUser> create(final AccountId accountId, final UserId userId, final AccountUserType type, final AccountUserStatus status) throws DataOperationException {
		final AccountUser accountUser = new AccountUser();
		accountUser.setAccountId(accountId);
		accountUser.setUserId(userId);
		accountUser.setType(type);
		accountUser.setStatus(status);
		final DataOperationResult<AccountUser> result = save(accountUser);
		return result;
	}

	/**
	 * Marks the entity as {@link AccountUserStatus#DELETED}.
	 * 
	 * @param accountUserId
	 *            The ID of the accountUser to delete.
	 * @return The result of the deletion, will not include the new accountUser
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AccountUserNotFoundException
	 *             If the ID specified did not match any accountUsers.
	 */
	public DataOperationResult<AccountUser> delete(final AccountUserId accountUserId) throws DataOperationException, AccountUserNotFoundException {
		final AccountUser accountUser = load(accountUserId);
		accountUser.setStatus(AccountUserStatus.DELETED);
		final DataOperationResult<AccountUser> result = save(accountUser);
		return result;
	}

	/**
	 * Returns a list of {@link AccountUser}s that match the specified criteria.
	 * 
	 * @param accountId
	 *            The account ID to list users for.
	 * @param type
	 *            The type of accountUser, optional.
	 * @param status
	 *            The status of the accountUser, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link AccountUser}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<AccountUser> list(final AccountId accountId, final AccountUserType type, final AccountUserStatus status, final long page, final long count) throws DataOperationException {
		return this.accountUserDao.list(accountId, type, status, page, count, null, null);
	}

	/**
	 * Returns a list of {@link AccountUser}s that match the specified criteria.
	 * 
	 * @param accountId
	 *            The account ID to list users for.
	 * @param type
	 *            The type of accountUser, optional.
	 * @param status
	 *            The status of the accountUser, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @param search
	 *            A search term to limit results by. Accepts wildcards (%).
	 * @param searchFields
	 *            The fields to search on. If null, and search is not empty,
	 *            will search 'name' field only.
	 * @return A list of {@link AccountUser}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<AccountUser> list(final AccountId accountId, final AccountUserType type, final AccountUserStatus status, final long page, final long count, final String search,
			final String[] searchFields) throws DataOperationException {
		return this.accountUserDao.list(accountId, type, status, page, count, search, searchFields);
	}

	/**
	 * Lists {@link AccountUser}s for a given {@link User}.
	 * 
	 * @param accountId
	 *            The account ID to search on.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link AccountUser}s, or an empty list.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<AccountUser> list(final AccountId accountId, final int page, final int count) throws DataOperationException {
		return this.accountUserDao.list(accountId, page, count);
	}

	/**
	 * Lists {@link AccountUser}s for a given {@link User}.
	 * 
	 * @param userId
	 *            The user ID to search on.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link AccountUser}s, or an empty list.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<AccountUser> list(final UserId userId, final int page, final int count) throws DataOperationException {
		return this.accountUserDao.list(userId, page, count);
	}

	/**
	 * Loads an {@link AccountUser} by it's ID.
	 * 
	 * @param accountUserId
	 *            The ID to load, required.
	 * @return The matching accountUser, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AccountUserNotFoundException
	 *             If the ID specified did not match any accountUsers.
	 */
	public AccountUser load(final AccountUserId accountUserId) throws DataOperationException, AccountUserNotFoundException {
		final AccountUser accountUser = this.accountUserDao.load(accountUserId);
		if (accountUser == null) {
			throw new AccountUserNotFoundException(accountUserId);
		}
		return accountUser;
	}

	/**
	 * Saves an {@link AccountUser}. Assigns a new ID ({@link UUID}) and sets
	 * the creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param accountUser
	 *            The accountUser to save.
	 * @return The result of the save operation, which will include the new
	 *         accountUser at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AccountUser> save(final AccountUser accountUser) throws DataOperationException {
		boolean create = false;
		if (accountUser.getId() == null) {
			accountUser.setId(new AccountUserId(UUID.randomUUID().toString()));
			create = true;
		}
		if (accountUser.getCreated() == null) {
			accountUser.setCreated(new Date());
			create = true;
		}
		if (create) {
			return this.accountUserDao.insert(accountUser);
		}
		final DataOperationResult<AccountUser> result = this.accountUserDao.update(accountUser);
		return result;
	}

}
