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

import com.ajah.user.account.AccountUser;
import com.ajah.user.account.AccountUserId;
import com.ajah.user.account.AccountUserStatus;
import com.ajah.user.account.AccountUserType;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;

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
	List<AccountUser> list(AccountUserType type, AccountUserStatus status, long page, long count) throws DataOperationException;

}