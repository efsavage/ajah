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
	public DataOperationResult<AccountUser> save(AccountUser accountUser) throws DataOperationException {
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
		DataOperationResult<AccountUser> result = this.accountUserDao.update(accountUser);
		return result;
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
	public AccountUser load(AccountUserId accountUserId) throws DataOperationException, AccountUserNotFoundException {
		AccountUser accountUser = this.accountUserDao.load(accountUserId);
		if (accountUser == null) {
			throw new AccountUserNotFoundException(accountUserId);
		}
		return accountUser;
	}

	/**
	 * Returns a list of {@link AccountUser}s that match the specified criteria.
	 * 
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
	public List<AccountUser> list(AccountUserType type, AccountUserStatus status, long page, long count) throws DataOperationException {
		return this.accountUserDao.list(type, status, page, count);
	}

	/**
	 * Creates a new {@link AccountUser} with the given properties.
	 * 
	 * @param name
	 *            The name of the accountUser, required.
	 * @param type
	 *            The type of accountUser, required.
	 * @param status
	 *            The status of the accountUser, required.
	 * @return The result of the creation, which will include the new
	 *         accountUser at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<AccountUser> create(String name, AccountUserType type, AccountUserStatus status) throws DataOperationException {
		AccountUser accountUser = new AccountUser();
		accountUser.setName(name);
		accountUser.setType(type);
		accountUser.setStatus(status);
		DataOperationResult<AccountUser> result = save(accountUser);
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
	public DataOperationResult<AccountUser> delete(AccountUserId accountUserId) throws DataOperationException, AccountUserNotFoundException {
		AccountUser accountUser = load(accountUserId);
		accountUser.setStatus(AccountUserStatus.DELETED);
		DataOperationResult<AccountUser> result = save(accountUser);
		return result;
	}

}
