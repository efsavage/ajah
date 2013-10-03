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

import com.ajah.user.account.Account;
import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountStatus;
import com.ajah.user.account.AccountType;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * DAO interface for {@link Account}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface AccountDao extends AjahDao<AccountId, Account> {

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
	List<Account> list(AccountType type, AccountStatus status, long page, long count) throws DataOperationException;

}