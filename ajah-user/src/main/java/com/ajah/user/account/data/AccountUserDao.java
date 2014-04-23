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

import java.util.List;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.account.Account;
import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountUser;
import com.ajah.user.account.AccountUserId;
import com.ajah.user.account.AccountUserStatus;
import com.ajah.user.account.AccountUserType;

/**
 * DAO interface for {@link AccountUser}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface AccountUserDao extends AjahDao<AccountUserId, AccountUser> {

	/**
	 * Returns a list of {@link AccountUser}s that match the specified criteria.
	 * 
	 * @param accountId
	 *            The ID of the account to list users for.
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
	List<AccountUser> list(final AccountId accountId, final AccountUserType type, final AccountUserStatus status, final long page, final long count, final String search, final String[] searchFields)
			throws DataOperationException;

	/**
	 * Lists {@link AccountUser}s for a given {@link User}.
	 * 
	 * @param userId
	 *            The userId to search on.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link AccountUser}s, or an empty list.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<AccountUser> list(final UserId userId, final int page, final int count) throws DataOperationException;

	/**
	 * Lists {@link AccountUser}s for a given {@link Account}.
	 * 
	 * @param accountId
	 *            The accountId to search on.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link AccountUser}s, or an empty list.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<AccountUser> list(final AccountId accountId, final int page, final int count) throws DataOperationException;

}
