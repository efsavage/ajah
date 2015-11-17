/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.memo.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.memo.UserMemo;
import com.ajah.user.memo.UserMemoId;
import com.ajah.user.memo.UserMemoStatus;
import com.ajah.user.memo.UserMemoType;

/**
 * Manages data operations for {@link UserMemo}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class UserMemoManager {

	@Autowired
	private UserMemoDao userMemoDao;

	/**
	 * Saves an {@link UserMemo}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param userMemo
	 *            The userMemo to save.
	 * @return The result of the save operation, which will include the new
	 *         userMemo at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserMemo> save(UserMemo userMemo) throws DataOperationException {
		boolean create = false;
		if (userMemo.getId() == null) {
			userMemo.setId(new UserMemoId(UUID.randomUUID().toString()));
			create = true;
		}
		if (userMemo.getCreated() == null) {
			userMemo.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<UserMemo> result = this.userMemoDao.insert(userMemo);
			log.fine("Created UserMemo " + userMemo.getName() + " [" + userMemo.getId() + "]");
			return result;
		}
		DataOperationResult<UserMemo> result = this.userMemoDao.update(userMemo);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated UserMemo " + userMemo.getName() + " [" + userMemo.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link UserMemo} by it's ID.
	 * 
	 * @param userMemoId
	 *            The ID to load, required.
	 * @return The matching userMemo, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserMemoNotFoundException
	 *             If the ID specified did not match any userMemos.
	 */
	public UserMemo load(UserMemoId userMemoId) throws DataOperationException, UserMemoNotFoundException {
		UserMemo userMemo = this.userMemoDao.load(userMemoId);
		if (userMemo == null) {
			throw new UserMemoNotFoundException(userMemoId);
		}
		return userMemo;
	}

	/**
	 * Returns a list of {@link UserMemo}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The UserMemoType to filter on, optional.
	 * @param status
	 *            The UserMemoStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link UserMemo}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<UserMemo> list(String search, UserId userId, UserMemoType type, UserMemoStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.userMemoDao.list(search, userId, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link UserMemo} with the given properties.
	 * 
	 * @param name
	 *            The name of the userMemo, required.
	 * @return The result of the creation, which will include the new userMemo
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserMemo> create(UserId userId, UserId authorUserId, String name, String memo) throws DataOperationException {
		UserMemo userMemo = new UserMemo();
		userMemo.setUserId(userId);
		userMemo.setAuthorUserId(authorUserId);
		userMemo.setName(name);
		userMemo.setMemo(memo);
		userMemo.setType(UserMemoType.STANDARD);
		userMemo.setStatus(UserMemoStatus.ACTIVE);
		DataOperationResult<UserMemo> result = save(userMemo);
		return result;
	}

	/**
	 * Marks the entity as {@link UserMemoStatus#DELETED}.
	 * 
	 * @param userMemoId
	 *            The ID of the userMemo to delete.
	 * @return The result of the deletion, will not include the new userMemo at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserMemoNotFoundException
	 *             If the ID specified did not match any userMemos.
	 */
	public DataOperationResult<UserMemo> delete(UserMemoId userMemoId) throws DataOperationException, UserMemoNotFoundException {
		UserMemo userMemo = load(userMemoId);
		userMemo.setStatus(UserMemoStatus.DELETED);
		DataOperationResult<UserMemo> result = save(userMemo);
		return result;
	}

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count() throws DataOperationException {
		return count(null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The userMemo type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final UserMemoType type, final UserMemoStatus status) throws DataOperationException {
		return this.userMemoDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The UserMemoType to filter on, optional.
	 * @param status
	 *            The UserMemoStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, UserMemoType type, UserMemoStatus status) throws DataOperationException {
		return this.userMemoDao.searchCount(search, type, status);
	}

}
