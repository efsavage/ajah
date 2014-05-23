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
package com.ajah.user.alert.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.alert.UserAlert;
import com.ajah.user.alert.UserAlertId;
import com.ajah.user.alert.UserAlertResponseType;
import com.ajah.user.alert.UserAlertStatus;
import com.ajah.user.alert.UserAlertType;

/**
 * Manages data operations for {@link UserAlert}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class UserAlertManager {

	@Autowired
	private UserAlertDao userAlertDao;

	/**
	 * Saves an {@link UserAlert}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param userAlert
	 *            The userAlert to save.
	 * @return The result of the save operation, which will include the new
	 *         userAlert at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserAlert> save(UserAlert userAlert) throws DataOperationException {
		boolean create = false;
		if (userAlert.getId() == null) {
			userAlert.setId(new UserAlertId(UUID.randomUUID().toString()));
			create = true;
		}
		if (userAlert.getCreated() == null) {
			userAlert.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<UserAlert> result = this.userAlertDao.insert(userAlert);
			log.fine("Created UserAlert " + userAlert.getSubject() + " [" + userAlert.getId() + "]");
			return result;
		}
		DataOperationResult<UserAlert> result = this.userAlertDao.update(userAlert);
		log.fine("Updated UserAlert " + userAlert.getSubject() + " [" + userAlert.getId() + "]");
		return result;
	}

	/**
	 * Loads an {@link UserAlert} by it's ID.
	 * 
	 * @param userAlertId
	 *            The ID to load, required.
	 * @return The matching userAlert, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserAlertNotFoundException
	 *             If the ID specified did not match any userAlerts.
	 */
	public UserAlert load(UserAlertId userAlertId) throws DataOperationException, UserAlertNotFoundException {
		UserAlert userAlert = this.userAlertDao.load(userAlertId);
		if (userAlert == null) {
			throw new UserAlertNotFoundException(userAlertId);
		}
		return userAlert;
	}

	/**
	 * Returns a list of {@link UserAlert}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of userAlert, optional.
	 * @param status
	 *            The status of the userAlert, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link UserAlert}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<UserAlert> list(UserAlertType type, UserAlertStatus status, long page, long count) throws DataOperationException {
		return this.userAlertDao.list(type, status, page, count);
	}

	/**
	 * Creates a new {@link UserAlert} with the given properties.
	 * 
	 * @param userId
	 *            The user the messages is for.
	 * @param subject
	 *            The subject of the userAlert, required if body is null.
	 * @param body
	 *            The body of the message, required if subject is null.
	 * @param type
	 *            The type of userAlert, required.
	 * @param responseType
	 *            The type of response desired.
	 * @param expiration
	 *            The date the message expires even without a response. Nulls
	 *            permitted.
	 * @param preserve
	 *            Keep this message after a response?
	 * @return The result of the creation, which will include the new userAlert
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<UserAlert> create(UserId userId, String subject, String body, UserAlertType type, UserAlertResponseType responseType, Date expiration, boolean preserve)
			throws DataOperationException {
		UserAlert userAlert = new UserAlert();
		userAlert.setUserId(userId);
		userAlert.setSubject(subject);
		userAlert.setBody(body);
		userAlert.setType(type);
		userAlert.setResponseType(responseType);
		userAlert.setExpiration(expiration);
		userAlert.setPreserve(preserve);
		DataOperationResult<UserAlert> result = save(userAlert);
		return result;
	}

	/**
	 * Marks the entity as {@link UserAlertStatus#DELETED}.
	 * 
	 * @param userAlertId
	 *            The ID of the userAlert to delete.
	 * @return The result of the deletion, will not include the new userAlert at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws UserAlertNotFoundException
	 *             If the ID specified did not match any userAlerts.
	 */
	public DataOperationResult<UserAlert> delete(UserAlertId userAlertId) throws DataOperationException, UserAlertNotFoundException {
		UserAlert userAlert = load(userAlertId);
		userAlert.setStatus(UserAlertStatus.DELETED);
		DataOperationResult<UserAlert> result = save(userAlert);
		return result;
	}

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count() throws DataOperationException {
		return count(null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The userAlert type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final UserAlertType type, final UserAlertStatus status) throws DataOperationException {
		return this.userAlertDao.count(type, status);
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
	public int searchCount(String search) throws DataOperationException {
		return this.userAlertDao.searchCount(search);
	}

}
