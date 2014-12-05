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
package com.ajah.user.invitation.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.User;
import com.ajah.user.info.UserInfo;
import com.ajah.user.invitation.Invitation;
import com.ajah.user.invitation.InvitationChannel;
import com.ajah.user.invitation.InvitationId;
import com.ajah.user.invitation.InvitationSender;
import com.ajah.user.invitation.InvitationStatus;
import com.ajah.user.invitation.InvitationType;

/**
 * Manages data operations for {@link Invitation}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class InvitationManager {

	@Autowired
	private InvitationDao invitationDao;

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
	 *            The invitation type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final InvitationType type, final InvitationStatus status) throws DataOperationException {
		return this.invitationDao.count(type, status);
	}

	/**
	 * Creates a new {@link Invitation} with the given properties.
	 * 
	 * @param user
	 *            The user who is sending the invitation.
	 * @param userInfo
	 *            The address the invitation is being sent to, required.
	 * @param address
	 *            The type of invitation, required.
	 * @param message
	 * @param targetId
	 * @param targetType
	 * @param channel
	 *            The delivery channel for the invite.
	 * @param type
	 *            The purpose of the invitation.
	 * @param sender
	 *            The delivery mechanism.
	 * @param lang
	 * @return The result of the creation, which will include the new invitation
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Invitation> create(final User user, final UserInfo userInfo, final String address, final String targetType, final String targetId, final String message,
			final InvitationChannel channel, final InvitationType type, final InvitationSender sender, final String lang) throws DataOperationException {
		final Invitation invitation = new Invitation();
		invitation.setUserId(user.getId());
		invitation.setAddress(address);
		invitation.setChannel(channel);
		invitation.setType(type);
		invitation.setTargetType(targetType);
		invitation.setTargetId(targetId);
		invitation.setMessage(message);
		invitation.setLang(lang);
		invitation.setStatus(InvitationStatus.UNSENT);
		final DataOperationResult<Invitation> result = save(invitation);
		sender.send(invitation, user, userInfo);
		return result;
	}

	/**
	 * Marks the entity as {@link InvitationStatus#DELETED}.
	 * 
	 * @param invitationId
	 *            The ID of the invitation to delete.
	 * @return The result of the deletion, will not include the new invitation
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws InvitationNotFoundException
	 *             If the ID specified did not match any invitations.
	 */
	public DataOperationResult<Invitation> delete(final InvitationId invitationId) throws DataOperationException, InvitationNotFoundException {
		final Invitation invitation = load(invitationId);
		invitation.setStatus(InvitationStatus.DELETED);
		final DataOperationResult<Invitation> result = save(invitation);
		return result;
	}

	/**
	 * Finds an invitation by it's {@link Invitation#getReference()}.
	 * 
	 * @param reference
	 *            The reference ID
	 * @return The first matching invitation for this reference.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public Invitation findByReference(final String reference) throws DataOperationException {
		return this.invitationDao.findByReference(reference);
	}

	/**
	 * Returns a list of {@link Invitation}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of invitation, optional.
	 * @param status
	 *            The status of the invitation, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Invitation}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Invitation> list(final InvitationType type, final InvitationStatus status, final long page, final long count) throws DataOperationException {
		return this.invitationDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link Invitation} by it's ID.
	 * 
	 * @param invitationId
	 *            The ID to load, required.
	 * @return The matching invitation, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws InvitationNotFoundException
	 *             If the ID specified did not match any invitations.
	 */
	public Invitation load(final InvitationId invitationId) throws DataOperationException, InvitationNotFoundException {
		final Invitation invitation = this.invitationDao.load(invitationId);
		if (invitation == null) {
			throw new InvitationNotFoundException(invitationId);
		}
		return invitation;
	}

	/**
	 * Saves an {@link Invitation}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param invitation
	 *            The invitation to save.
	 * @return The result of the save operation, which will include the new
	 *         invitation at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Invitation> save(final Invitation invitation) throws DataOperationException {
		boolean create = false;
		if (invitation.getId() == null) {
			invitation.setId(new InvitationId(UUID.randomUUID().toString()));
			create = true;
		}
		if (invitation.getCreated() == null) {
			invitation.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<Invitation> result = this.invitationDao.insert(invitation);
			log.fine("Created Invitation " + invitation.getAddress() + " [" + invitation.getId() + "]");
			return result;
		}
		final DataOperationResult<Invitation> result = this.invitationDao.update(invitation);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated Invitation " + invitation.getAddress() + " [" + invitation.getId() + "]");
		}
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
		return this.invitationDao.searchCount(search);
	}

}
