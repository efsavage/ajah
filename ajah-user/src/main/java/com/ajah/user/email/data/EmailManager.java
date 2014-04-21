/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.email.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailStatus;
import com.ajah.user.email.EmailType;
import com.ajah.util.data.format.EmailAddress;

/**
 * Manages data operations for {@link Email}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class EmailManager {

	@Autowired
	private EmailDao emailDao;

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final UserId userId) throws DataOperationException {
		return count(userId, null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The email type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final UserId userId, final EmailType type, final EmailStatus status) throws DataOperationException {
		return this.emailDao.count(userId, type, status);
	}

	/**
	 * Creates a new {@link Email} with the given properties.
	 * 
	 * @param userId
	 * 
	 * @param address
	 *            The actual address of the email, required.
	 * @param type
	 *            The type of email, required.
	 * @param status
	 *            The status of the email, required.
	 * @return The result of the creation, which will include the new email at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Email> create(final UserId userId, final String address, final EmailType type, final EmailStatus status) throws DataOperationException {
		final Email email = new Email();
		email.setUserId(userId);
		email.setAddress(address);
		email.setType(type);
		email.setStatus(status);
		final DataOperationResult<Email> result = save(email);
		return result;
	}

	/**
	 * Marks the entity as {@link EmailStatus#DELETED}.
	 * 
	 * @param emailId
	 *            The ID of the email to delete.
	 * @return The result of the deletion, will not include the new email at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws EmailNotFoundException
	 *             If the ID specified did not match any emails.
	 */
	public DataOperationResult<Email> delete(final EmailId emailId) throws DataOperationException, EmailNotFoundException {
		final Email email = load(emailId);
		email.setStatus(EmailStatus.DELETED);
		final DataOperationResult<Email> result = save(email);
		return result;
	}

	/**
	 * Locates an email by the address field.
	 * 
	 * @param emailAddress
	 *            The address to search for.
	 * @return The email entity, if found, otherwise null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public Email find(final EmailAddress emailAddress) throws DataOperationException {
		return this.emailDao.find(emailAddress);
	}

	/**
	 * Returns a list of {@link Email}s that match the specified criteria.
	 * 
	 * @param userId
	 * 
	 * @param type
	 *            The type of email, optional.
	 * @param status
	 *            The status of the email, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Email}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Email> list(final UserId userId, final EmailType type, final EmailStatus status, final long page, final long count) throws DataOperationException {
		return this.emailDao.list(userId, type, status, page, count);
	}

	/**
	 * Loads an {@link Email} by it's ID.
	 * 
	 * @param emailId
	 *            The ID to load, required.
	 * @return The matching email, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws EmailNotFoundException
	 *             If the ID specified did not match any emails.
	 */
	public Email load(final EmailId emailId) throws DataOperationException, EmailNotFoundException {
		final Email email = this.emailDao.load(emailId);
		if (email == null) {
			throw new EmailNotFoundException(emailId);
		}
		return email;
	}

	/**
	 * Saves an {@link Email}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param email
	 *            The email to save.
	 * @return The result of the save operation, which will include the new
	 *         email at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Email> save(final Email email) throws DataOperationException {
		boolean create = false;
		if (email.getId() == null) {
			email.setId(new EmailId(UUID.randomUUID().toString()));
			create = true;
		}
		if (email.getCreated() == null) {
			email.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<Email> result = this.emailDao.insert(email);
			log.fine("Created Email " + email.getAddress() + " [" + email.getId() + "]");
			return result;
		}
		final DataOperationResult<Email> result = this.emailDao.update(email);
		log.fine("Updated Email " + email.getAddress() + " [" + email.getId() + "]");
		return result;
	}

}
