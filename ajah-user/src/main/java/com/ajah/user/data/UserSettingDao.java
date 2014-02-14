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

import java.util.List;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserSetting;
import com.ajah.user.UserSettingId;
import com.ajah.user.UserSettingStatus;
import com.ajah.user.UserSettingType;

/**
 * DAO interface for {@link UserSetting}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface UserSettingDao extends AjahDao<UserSettingId, UserSetting> {

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
	List<UserSetting> list(final UserSettingType type, final UserSettingStatus status, final long page, final long count) throws DataOperationException;

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
	long count(final UserSettingType type, final UserSettingStatus status) throws DataOperationException;

}
