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
package com.ajah.user.role.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.role.Role;
import com.ajah.user.role.RoleId;
import com.ajah.user.role.RoleStatus;
import com.ajah.user.role.RoleType;

import lombok.extern.java.Log;

/**
 * Manages data operations for {@link Role}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class RoleManager {

	@Autowired
	private RoleDao roleDao;

	/**
	 * Saves an {@link Role}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param role
	 *            The role to save.
	 * @return The result of the save operation, which will include the new role
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Role> save(Role role) throws DataOperationException {
		boolean create = false;
		if (role.getId() == null) {
			role.setId(new RoleId(UUID.randomUUID().toString()));
			create = true;
		}
		if (role.getCreated() == null) {
			role.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<Role> result = this.roleDao.insert(role);
			log.fine("Created Role " + role.getName() + " [" + role.getId() + "]");
			return result;
		}
		DataOperationResult<Role> result = this.roleDao.update(role);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated Role " + role.getName() + " [" + role.getId() + "]");
		}
		return result;
	}

	/**
	 * Loads an {@link Role} by it's ID.
	 * 
	 * @param roleId
	 *            The ID to load, required.
	 * @return The matching role, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RoleNotFoundException
	 *             If the ID specified did not match any roles.
	 */
	public Role load(RoleId roleId) throws DataOperationException, RoleNotFoundException {
		Role role = this.roleDao.load(roleId);
		if (role == null) {
			throw new RoleNotFoundException(roleId);
		}
		return role;
	}

	/**
	 * Returns a list of {@link Role}s that match the specified criteria.
	 * 
	 * @param search
	 *            The search field, optional.
	 * @param type
	 *            The RoleType to filter on, optional.
	 * @param status
	 *            The RoleStatus to filter on, optional.
	 * @param sort
	 *            The field to sort by, defaults to id, optional.
	 * @param order
	 *            The order to sort by The order to sort by, defaults to
	 *            ascending, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Role}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Role> list(String search, RoleType type, RoleStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		return this.roleDao.list(search, type, status, sort, order, page, count);
	}

	/**
	 * Creates a new {@link Role} with the given properties.
	 * 
	 * @param id
	 *            The unique ID of the role, required.
	 * @param name
	 *            The name of the role, required.
	 * @param type
	 *            The type of role, required.
	 * @param status
	 *            The status of the role, required.
	 * @return The result of the creation, which will include the new role at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Role> create(String id, String name, String description, RoleType type, RoleStatus status) throws DataOperationException {
		Role role = new Role();
		role.setId(new RoleId(id));
		role.setName(name);
		role.setDescription(description);
		role.setType(type);
		role.setStatus(status);
		return save(role);
	}

	/**
	 * Marks the entity as {@link RoleStatus#INACTIVE}.
	 * 
	 * @param roleId
	 *            The ID of the role to deactivate.
	 * @return The result of the operation , will not include the new role at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RoleNotFoundException
	 *             If the ID specified did not match any roles.
	 */
	public DataOperationResult<Role> deactivate(RoleId roleId) throws DataOperationException, RoleNotFoundException {
		Role role = load(roleId);
		role.setStatus(RoleStatus.INACTIVE);
		return save(role);
	}

	/**
	 * Marks the entity as {@link RoleStatus#ACTIVE}.
	 * 
	 * @param roleId
	 *            The ID of the role to activate.
	 * @return The result of the operation , will not include the new role at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RoleNotFoundException
	 *             If the ID specified did not match any roles.
	 */
	public DataOperationResult<Role> activate(RoleId roleId) throws DataOperationException, RoleNotFoundException {
		Role role = load(roleId);
		role.setStatus(RoleStatus.INACTIVE);
		return save(role);
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
	 *            The role type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final RoleType type, final RoleStatus status) throws DataOperationException {
		return this.roleDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @param type
	 *            The RoleType to filter on, optional.
	 * @param status
	 *            The RoleStatus to filter on, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search, RoleType type, RoleStatus status) throws DataOperationException {
		return this.roleDao.searchCount(search, type, status);
	}

	/**
	 * Finds an {@link Role} by it's ID, returning null if it does not exist.
	 * 
	 * @param roleId
	 *            The ID to load, required.
	 * @return The matching role, if found. May return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public Role find(RoleId roleId) throws DataOperationException {
		return this.roleDao.load(roleId);
	}

}
