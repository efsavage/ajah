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
package com.ajah.user.group.data;

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
import com.ajah.user.group.GroupId;
import com.ajah.user.group.GroupUser;
import com.ajah.user.group.GroupUserId;
import com.ajah.user.group.GroupUserStatus;
import com.ajah.user.group.GroupUserType;

/**
 * Manages data operations for {@link GroupUser}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class GroupUserManager {

	@Autowired
	private GroupUserDao groupUserDao;

	/**
	 * Saves an {@link GroupUser}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param groupUser
	 *            The groupUser to save.
	 * @return The result of the save operation, which will include the new
	 *         groupUser at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<GroupUser> save(GroupUser groupUser) throws DataOperationException {
		boolean create = false;
		if (groupUser.getId() == null) {
			groupUser.setId(new GroupUserId(UUID.randomUUID().toString()));
			create = true;
		}
		if (groupUser.getCreated() == null) {
			groupUser.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<GroupUser> result = this.groupUserDao.insert(groupUser);
			log.fine("Created GroupUser " + groupUser.getId());
			return result;
		}
		DataOperationResult<GroupUser> result = this.groupUserDao.update(groupUser);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated GroupUser " + groupUser.getId());
		}
		return result;
	}

	/**
	 * Loads an {@link GroupUser} by it's ID.
	 * 
	 * @param groupUserId
	 *            The ID to load, required.
	 * @return The matching groupUser, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws GroupUserNotFoundException
	 *             If the ID specified did not match any groupUsers.
	 */
	public GroupUser load(GroupUserId groupUserId) throws DataOperationException, GroupUserNotFoundException {
		GroupUser groupUser = this.groupUserDao.load(groupUserId);
		if (groupUser == null) {
			throw new GroupUserNotFoundException(groupUserId);
		}
		return groupUser;
	}

	/**
	 * Returns a list of {@link GroupUser}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The GroupUserType to filter on, optional.
	 * @param status
	 *            The GroupUserStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link GroupUser}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<GroupUser> list(String search, GroupUserType type, GroupUserStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.groupUserDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link GroupUser} with the given properties.
	 * 
	 * @param type
	 *            The type of groupUser, required.
	 * @param status
	 *            The status of the groupUser, required.
	 * @return The result of the creation, which will include the new groupUser
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<GroupUser> create(GroupId groupId, UserId userId, GroupUserType type, GroupUserStatus status) throws DataOperationException {
		GroupUser groupUser = new GroupUser();
		groupUser.setGroupId(groupId);
		groupUser.setUserId(userId);
		groupUser.setType(type);
		groupUser.setStatus(status);
		DataOperationResult<GroupUser> result = save(groupUser);
		return result;
	}

	/**
	 * Marks the entity as {@link GroupUserStatus#DELETED}.
	 * 
	 * @param groupUserId
	 *            The ID of the groupUser to delete.
	 * @return The result of the deletion, will not include the new groupUser at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws GroupUserNotFoundException
	 *             If the ID specified did not match any groupUsers.
	 */
	public DataOperationResult<GroupUser> delete(GroupUserId groupUserId) throws DataOperationException, GroupUserNotFoundException {
		GroupUser groupUser = load(groupUserId);
		groupUser.setStatus(GroupUserStatus.DELETED);
		DataOperationResult<GroupUser> result = save(groupUser);
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
	 *            The groupUser type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final GroupUserType type, final GroupUserStatus status) throws DataOperationException {
		return this.groupUserDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The GroupUserType to filter on, optional.
	 * @param status
	 *            The GroupUserStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, GroupUserType type, GroupUserStatus status) throws DataOperationException {
		return this.groupUserDao.searchCount(search, type, status);
	}

}
