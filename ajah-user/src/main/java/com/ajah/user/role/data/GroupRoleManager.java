/*
 *  Copyright 2015-2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.role.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.group.GroupId;
import com.ajah.user.role.GroupRole;
import com.ajah.user.role.GroupRoleId;
import com.ajah.user.role.GroupRoleStatus;
import com.ajah.user.role.GroupRoleType;
import com.ajah.user.role.RoleId;

import lombok.extern.java.Log;

/**
 * Manages data operations for {@link GroupRole}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="https://github.com/efsavage">github.com/efsavage</a>.
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Log
public class GroupRoleManager {

	@Autowired
	private GroupRoleDao groupRoleDao;

	/**
	 * Saves an {@link GroupRole}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param groupRole
	 *            The groupRole to save.
	 * @return The result of the save operation, which will include the new
	 *         groupRole at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<GroupRole> save(GroupRole groupRole) throws DataOperationException {
		boolean create = false;
		if (groupRole.getId() == null) {
			groupRole.setId(new GroupRoleId(UUID.randomUUID().toString()));
			create = true;
		}
		if (groupRole.getCreated() == null) {
			groupRole.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<GroupRole> result = this.groupRoleDao.insert(groupRole);
			log.fine("Created GroupRole " + groupRole.getId());
			return result;
		}
		DataOperationResult<GroupRole> result = this.groupRoleDao.update(groupRole);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated GroupRole " + groupRole.getId());
		}
		return result;
	}

	/**
	 * Loads an {@link GroupRole} by it's ID.
	 * 
	 * @param groupRoleId
	 *            The ID to load, required.
	 * @return The matching groupRole, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws GroupRoleNotFoundException
	 *             If the ID specified did not match any groupRoles.
	 */
	public GroupRole load(GroupRoleId groupRoleId) throws DataOperationException, GroupRoleNotFoundException {
		GroupRole groupRole = this.groupRoleDao.load(groupRoleId);
		if (groupRole == null) {
			throw new GroupRoleNotFoundException(groupRoleId);
		}
		return groupRole;
	}

	/**
	 * Returns a list of {@link GroupRole}s that match the specified criteria.
	 * 
	 * @param groupId
	 *            The group to filter on, optional.
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The GroupRoleType to filter on, optional.
	 * @param status
	 *            The GroupRoleStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link GroupRole}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<GroupRole> list(GroupId groupId, String search, GroupRoleType type, GroupRoleStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.groupRoleDao.list(groupId, search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link GroupRole} with the given properties.
	 * 
	 * @param type
	 *            The type of groupRole, required.
	 * @param status
	 *            The status of the groupRole, required.
	 * @return The result of the creation, which will include the new groupRole
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<GroupRole> create(GroupId groupId, RoleId roleId, GroupRoleType type, GroupRoleStatus status) throws DataOperationException {
		GroupRole groupRole = new GroupRole();
		groupRole.setGroupId(groupId);
		groupRole.setRoleId(roleId);
		groupRole.setType(type);
		groupRole.setStatus(status);
		return save(groupRole);
	}

	/**
	 * Marks the entity as {@link GroupRoleStatus#DELETED}.
	 * 
	 * @param groupRoleId
	 *            The ID of the groupRole to delete.
	 * @return The result of the deletion, will not include the new groupRole at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws GroupRoleNotFoundException
	 *             If the ID specified did not match any groupRoles.
	 */
	public DataOperationResult<GroupRole> delete(GroupRoleId groupRoleId) throws DataOperationException, GroupRoleNotFoundException {
		GroupRole groupRole = load(groupRoleId);
		groupRole.setStatus(GroupRoleStatus.DELETED);
		return save(groupRole);
	}

	/**
	 * Returns a count of all records.
	 * @param groupId 
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(GroupId groupId) throws DataOperationException {
		return count(groupId,null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * @param groupId
	 *            The group to filter on, optional.
	 * @param type
	 *            The groupRole type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(GroupId groupId, final GroupRoleType type, final GroupRoleStatus status) throws DataOperationException {
		return this.groupRoleDao.count(groupId,type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param groupId
	 *            The group to filter on, optional.
	 * @param search
	 *            The search query.
	 * @param type
	 *            The GroupRoleType to filter on, optional.
	 * @param status
	 *            The GroupRoleStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(GroupId groupId, String search, GroupRoleType type, GroupRoleStatus status) throws DataOperationException {
		return this.groupRoleDao.searchCount(groupId, search, type, status);
	}

	public List<GroupRole> list(GroupId groupId, GroupRoleStatus status) throws DataOperationException {
		return this.groupRoleDao.list(groupId, status);
	}

}
