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
import com.ajah.user.group.Group;
import com.ajah.user.group.GroupId;
import com.ajah.user.group.GroupStatus;
import com.ajah.user.group.GroupType;

/**
 * Manages data operations for {@link Group}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class GroupManager {

	@Autowired
	private GroupDao groupDao;

	/**
	 * Saves an {@link Group}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param group
	 *            The group to save.
	 * @return The result of the save operation, which will include the new
	 *         group at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Group> save(Group group) throws DataOperationException {
		boolean create = false;
		if (group.getId() == null) {
			group.setId(new GroupId(UUID.randomUUID().toString()));
			create = true;
		}
		if (group.getCreated() == null) {
			group.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<Group> result = this.groupDao.insert(group);
			log.fine("Created Group " + group.getName() + " [" + group.getId() + "]");
			return result;
		}
		DataOperationResult<Group> result = this.groupDao.update(group);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated Group " + group.getName() + " [" + group.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link Group} by it's ID.
	 * 
	 * @param groupId
	 *            The ID to load, required.
	 * @return The matching group, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws GroupNotFoundException
	 *             If the ID specified did not match any groups.
	 */
	public Group load(GroupId groupId) throws DataOperationException, GroupNotFoundException {
		Group group = this.groupDao.load(groupId);
		if (group == null) {
			throw new GroupNotFoundException(groupId);
		}
		return group;
	}

	/**
	 * Returns a list of {@link Group}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The GroupType to filter on, optional.
	 * @param status
	 *            The GroupStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Group}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Group> list(String search, GroupType type, GroupStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.groupDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link Group} with the given properties.
	 * 
	 * @param name
	 *            The name of the group, required.
	 * @param type
	 *            The type of group, required.
	 * @param status
	 *            The status of the group, required.
	 * @return The result of the creation, which will include the new group at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Group> create(String name, GroupType type, GroupStatus status) throws DataOperationException {
		Group group = new Group();
		group.setName(name);
		group.setType(type);
		group.setStatus(status);
		DataOperationResult<Group> result = save(group);
		return result;
	}

	/**
	 * Marks the entity as {@link GroupStatus#DELETED}.
	 * 
	 * @param groupId
	 *            The ID of the group to delete.
	 * @return The result of the deletion, will not include the new group at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws GroupNotFoundException
	 *             If the ID specified did not match any groups.
	 */
	public DataOperationResult<Group> delete(GroupId groupId) throws DataOperationException, GroupNotFoundException {
		Group group = load(groupId);
		group.setStatus(GroupStatus.DELETED);
		DataOperationResult<Group> result = save(group);
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
	 *            The group type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final GroupType type, final GroupStatus status) throws DataOperationException {
		return this.groupDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The GroupType to filter on, optional.
	 * @param status
	 *            The GroupStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, GroupType type, GroupStatus status) throws DataOperationException {
		return this.groupDao.searchCount(search, type, status);
	}

}
