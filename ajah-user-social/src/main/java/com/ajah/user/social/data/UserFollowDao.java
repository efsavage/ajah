/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.social.data;

import java.util.List;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.social.UserFollow;
import com.ajah.user.social.UserFollowId;
import com.ajah.user.social.UserFollowStatus;
import com.ajah.user.social.UserFollowType;

/**
 * DAO interface for {@link UserFollow}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface UserFollowDao extends AjahDao<UserFollowId, UserFollow> {

	/**
	 * Returns a list of {@link UserFollow}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of userFollow, optional.
	 * @param status
	 *            The status of the userFollow, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link UserFollow}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	List<UserFollow> list(final UserFollowType type, final UserFollowStatus status, final long page, final long count) throws DataOperationException;

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The userFollow type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	long count(final UserFollowType type, final UserFollowStatus status) throws DataOperationException;

}
