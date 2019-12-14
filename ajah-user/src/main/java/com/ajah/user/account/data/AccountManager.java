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
import com.ajah.user.account.Account;
import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountStatus;
import com.ajah.user.account.AccountType;
import com.ajah.util.StringUtils;

/**
 * Manages data operations for {@link Account}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
public class AccountManager {

	@Autowired
	private AccountDao accountDao;

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
	 *            The account type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final AccountType type, final AccountStatus status) throws DataOperationException {
		return this.accountDao.count(type, status);
	}

	/**
	 * Creates a new {@link Account} with the given properties.
	 * 
	 * @param name
	 *            The name of the account, required.
	 * @param type
	 *            The type of account, required.
	 * @param status
	 *            The status of the account, required.
	 * @return The result of the creation, which will include the new account at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Account> create(final String name, final AccountType type, final AccountStatus status) throws DataOperationException {
		final Account account = new Account();
		account.setName(name);
		account.setUrlToken(StringUtils.toCleanUrlToken(name));
		account.setType(type);
		account.setStatus(status);
		return save(account);
	}

	/**
	 * Marks the entity as {@link AccountStatus#DELETED}.
	 * 
	 * @param accountId
	 *            The ID of the account to delete.
	 * @return The result of the deletion, will not include the new account at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AccountNotFoundException
	 *             If the ID specified did not match any accounts.
	 */
	public DataOperationResult<Account> delete(final AccountId accountId) throws DataOperationException, AccountNotFoundException {
		final Account account = load(accountId);
		account.setStatus(AccountStatus.DELETED);
		return save(account);
	}

	/**
	 * Returns a list of {@link Account}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of account, optional.
	 * @param status
	 *            The status of the account, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Account}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Account> list(final AccountType type, final AccountStatus status, final long page, final long count) throws DataOperationException {
		return this.accountDao.list(type, status, page, count, null, null);
	}

	/**
	 * Returns a list of {@link Account}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of account, optional.
	 * @param status
	 *            The status of the account, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @param search
	 *            A search term to limit results by. Accepts wildcards (%).
	 * @param searchFields
	 *            The fields to search on. If null, and search is not empty,
	 *            will search 'name' field only.
	 * @return A list of {@link Account}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Account> list(final AccountType type, final AccountStatus status, final long page, final long count, final String search, final String[] searchFields) throws DataOperationException {
		return this.accountDao.list(type, status, page, count, search, searchFields);
	}

	/**
	 * Loads an {@link Account} by it's ID.
	 * 
	 * @param accountId
	 *            The ID to load, required.
	 * @return The matching account, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws AccountNotFoundException
	 *             If the ID specified did not match any accounts.
	 */
	public Account load(final AccountId accountId) throws DataOperationException, AccountNotFoundException {
		final Account account = this.accountDao.load(accountId);
		if (account == null) {
			throw new AccountNotFoundException(accountId);
		}
		return account;
	}

	/**
	 * Saves an {@link Account}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param account
	 *            The account to save.
	 * @return The result of the save operation, which will include the new
	 *         account at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Account> save(final Account account) throws DataOperationException {
		boolean create = false;
		if (account.getId() == null) {
			account.setId(new AccountId(UUID.randomUUID().toString()));
			create = true;
		}
		if (account.getCreated() == null) {
			account.setCreated(new Date());
			create = true;
		}
		if (create) {
			return this.accountDao.insert(account);
		}
		return this.accountDao.update(account);
	}

}
