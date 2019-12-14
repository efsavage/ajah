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
package com.ajah.user.email.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.PermissionDeniedException;
import com.ajah.user.UserId;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailVerification;
import com.ajah.user.email.EmailVerificationId;
import com.ajah.user.email.EmailVerificationStatus;
import com.ajah.user.email.EmailVerificationType;
import com.ajah.util.data.HashUtils;

import lombok.extern.java.Log;

/**
 * Manages data operations for {@link EmailVerification}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class EmailVerificationManager {

	@Autowired
	private EmailManager emailManager;

	@Autowired
	private EmailVerificationDao emailVerificationDao;

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
	 *            The emailVerification type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final EmailVerificationType type, final EmailVerificationStatus status) throws DataOperationException {
		return this.emailVerificationDao.count(type, status);
	}

	/**
	 * Creates a new {@link EmailVerification} with the given properties.
	 * 
	 * @param userId
	 *            The user to verify the email for.
	 * @param emailId
	 *            The email to verify.
	 * @return The result of the creation, which will include the new
	 *         emailVerification at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<EmailVerification> create(final UserId userId, final EmailId emailId) throws DataOperationException {
		final EmailVerification emailVerification = new EmailVerification();
		emailVerification.setEmailId(emailId);
		emailVerification.setStatus(EmailVerificationStatus.NEW);
		emailVerification.setType(EmailVerificationType.STANDARD);
		emailVerification.setCode(HashUtils.sha1Hex(userId.toString() + emailId.toString() + UUID.randomUUID().toString()));
		final DataOperationResult<EmailVerification> result = save(emailVerification);
		return result;
	}

	/**
	 * Marks the entity as {@link EmailVerificationStatus#ERROR}.
	 * 
	 * @param emailVerificationId
	 *            The ID of the emailVerification to delete.
	 * @return The result of the deletion, will not include the new
	 *         emailVerification at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws EmailVerificationNotFoundException
	 *             If the ID specified did not match any emailVerifications.
	 */
	public DataOperationResult<EmailVerification> error(final EmailVerificationId emailVerificationId) throws DataOperationException, EmailVerificationNotFoundException {
		final EmailVerification emailVerification = load(emailVerificationId);
		if (emailVerification.getStatus() == EmailVerificationStatus.REDEEEMED) {
			log.warning("Attempt to flag email verification " + emailVerification.getId() + " as error, but it has already been redeemed");
			return new DataOperationResult<EmailVerification>(emailVerification, 0);
		}
		emailVerification.setStatus(EmailVerificationStatus.ERROR);
		final DataOperationResult<EmailVerification> result = save(emailVerification);
		return result;
	}

	/**
	 * Returns a list of {@link EmailVerification}s that match the specified
	 * criteria.
	 * 
	 * @param type
	 *            The type of emailVerification, optional.
	 * @param status
	 *            The status of the emailVerification, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link EmailVerification}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<EmailVerification> list(final EmailVerificationType type, final EmailVerificationStatus status, final long page, final long count) throws DataOperationException {
		return this.emailVerificationDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link EmailVerification} by it's ID.
	 * 
	 * @param emailVerificationId
	 *            The ID to load, required.
	 * @return The matching emailVerification, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws EmailVerificationNotFoundException
	 *             If the ID specified did not match any emailVerifications.
	 */
	public EmailVerification load(final EmailVerificationId emailVerificationId) throws DataOperationException, EmailVerificationNotFoundException {
		final EmailVerification emailVerification = this.emailVerificationDao.load(emailVerificationId);
		if (emailVerification == null) {
			throw new EmailVerificationNotFoundException(emailVerificationId);
		}
		return emailVerification;
	}

	public EmailVerification recent(final EmailId emailId) throws DataOperationException {
		return this.emailVerificationDao.recent(emailId);
	}

	/**
	 * Saves an {@link EmailVerification}. Assigns a new ID ({@link UUID}) and
	 * sets the creation date if necessary. If either of these elements are set,
	 * will perform an insert. Otherwise will perform an update.
	 * 
	 * @param emailVerification
	 *            The emailVerification to save.
	 * @return The result of the save operation, which will include the new
	 *         emailVerification at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<EmailVerification> save(final EmailVerification emailVerification) throws DataOperationException {
		boolean create = false;
		if (emailVerification.getId() == null) {
			emailVerification.setId(new EmailVerificationId(UUID.randomUUID().toString()));
			create = true;
		}
		if (emailVerification.getCreated() == null) {
			emailVerification.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<EmailVerification> result = this.emailVerificationDao.insert(emailVerification);
			log.fine("Created EmailVerification " + emailVerification.getCode() + " [" + emailVerification.getId() + "]");
			return result;
		}
		final DataOperationResult<EmailVerification> result = this.emailVerificationDao.update(emailVerification);
		log.fine("Updated EmailVerification " + emailVerification.getCode() + " [" + emailVerification.getId() + "]");
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
		return this.emailVerificationDao.searchCount(search);
	}

	public boolean verify(final UserId userId, final String code) throws EmailVerificationNotFoundException, DataOperationException, EmailNotFoundException, PermissionDeniedException {
		final EmailVerification emailVerification = this.emailVerificationDao.find(code);
		if (emailVerification == null) {
			throw new EmailVerificationNotFoundException(code);
		}
		final Email email = this.emailManager.load(emailVerification.getEmailId());
		if (!email.getUserId().equals(userId)) {
			throw new PermissionDeniedException();
		}
		if (!email.isVerified()) {
			email.setVerified(true);
			this.emailManager.save(email);
			emailVerification.setStatus(EmailVerificationStatus.REDEEEMED);
			save(emailVerification);
			return true;
		}
		return false;
	}

}
