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

import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountSetting;
import com.ajah.user.account.AccountSettingId;
import com.ajah.user.account.AccountSettingStatus;
import com.ajah.user.account.AccountSettingType;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * DAO interface for {@link AccountSetting}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface AccountSettingDao extends AjahDao<AccountSettingId, AccountSetting> {

	/**
	 * Returns a list of {@link AccountSetting}s that match the specified criteria.
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
	List<AccountSetting> list(AccountSettingType type, AccountSettingStatus status, long page, long count) throws DataOperationException;

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
	long count(AccountSettingType type, AccountSettingStatus status) throws DataOperationException;

	List<AccountSetting> list(AccountId accountId) throws DataOperationException;

	AccountSetting find(AccountId accountId, String name) throws DataOperationException;

}