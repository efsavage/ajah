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
package com.ajah.user.audit.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.audit.UserAudit;
import com.ajah.user.audit.UserAuditField;
import com.ajah.user.audit.UserAuditId;
import com.ajah.user.audit.UserAuditType;

/**
 * Manages data operations for {@link UserAudit}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class UserAuditManager {

	@Autowired
	private UserAuditDao userAuditDao;

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
	 *            The userAudit type to limit to, optional.
	 * @param field
	 *            The field to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int count(final UserAuditType type, final UserAuditField field) throws DataOperationException {
		return this.userAuditDao.count(type, field);
	}

	/**
	 * Creates a new {@link UserAudit} with the given properties.
	 * 
	 * @param userId
	 *            The ID of the user to create an audit for.
	 * @param field
	 *            The name of the userAudit, required.
	 * @param oldValue
	 *            The old value.
	 * @param newValue
	 *            The new value. Note that if this matches the old value no save
	 *            will happen and method will return null.
	 * @param type
	 * @param headers2
	 * @param
	 * @return The result of the creation, which will include the new userAudit
	 *         at {@link DataOperationResult#getEntity()}. If the values are the
	 *         same, returns null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserAudit> create(final UserId userId, final UserId staffUserId, final UserAuditField field, final String oldValue, final String newValue, final UserAuditType type,
			String userComment, String staffComment, final String ip, final String headers) throws DataOperationException {
		final UserAudit userAudit = new UserAudit();
		userAudit.setUserId(userId);
		userAudit.setStaffUserId(staffUserId);
		userAudit.setField(field);
		userAudit.setOldValue(oldValue);
		userAudit.setNewValue(newValue);
		userAudit.setType(type);
		userAudit.setUserComment(userComment);
		userAudit.setStaffComment(staffComment);
		userAudit.setIp(ip);
		userAudit.setHeaders(headers);
		final DataOperationResult<UserAudit> result = save(userAudit);
		return result;
	}

	/**
	 * Returns a list of {@link UserAudit}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of userAudit, optional.
	 * @param field
	 *            The field of the userAudit, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link UserAudit}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<UserAudit> list(final UserAuditType type, final UserAuditField field, final long page, final long count) throws DataOperationException {
		return this.userAuditDao.list(type, field, page, count);
	}

	/**
	 * Loads an {@link UserAudit} by it's ID.
	 * 
	 * @param userAuditId
	 *            The ID to load, required.
	 * @return The matching userAudit, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserAuditNotFoundException
	 *             If the ID specified did not match any userAudits.
	 */
	public UserAudit load(final UserAuditId userAuditId) throws DataOperationException, UserAuditNotFoundException {
		final UserAudit userAudit = this.userAuditDao.load(userAuditId);
		if (userAudit == null) {
			throw new UserAuditNotFoundException(userAuditId);
		}
		return userAudit;
	}

	/**
	 * Saves an {@link UserAudit}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param userAudit
	 *            The userAudit to save.
	 * @return The result of the save operation, which will include the new
	 *         userAudit at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserAudit> save(final UserAudit userAudit) throws DataOperationException {
		boolean create = false;
		if (userAudit.getId() == null) {
			userAudit.setId(new UserAuditId(UUID.randomUUID().toString()));
			create = true;
		}
		if (userAudit.getCreated() == null) {
			userAudit.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<UserAudit> result = this.userAuditDao.insert(userAudit);
			log.fine("Created UserAudit " + userAudit.getId());
			return result;
		}
		final DataOperationResult<UserAudit> result = this.userAuditDao.update(userAudit);
		log.fine("Updated UserAudit " + userAudit.getId());
		return result;
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(final String search) throws DataOperationException {
		return this.userAuditDao.searchCount(search);
	}

}
