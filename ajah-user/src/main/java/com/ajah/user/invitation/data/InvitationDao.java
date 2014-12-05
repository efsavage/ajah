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

import java.util.List;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.invitation.Invitation;
import com.ajah.user.invitation.InvitationId;
import com.ajah.user.invitation.InvitationStatus;
import com.ajah.user.invitation.InvitationType;

/**
 * DAO interface for {@link Invitation}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface InvitationDao extends AjahDao<InvitationId, Invitation> {

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
	List<Invitation> list(final InvitationType type, final InvitationStatus status, final long page, final long count) throws DataOperationException;

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
	long count(final InvitationType type, final InvitationStatus status) throws DataOperationException;

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	int searchCount(final String search) throws DataOperationException;

	/**
	 * Finds an invitation by it's {@link Invitation#getReference()}.
	 * 
	 * @param reference
	 *            The reference ID
	 * @return The first matching invitation for this reference.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	Invitation findByReference(final String reference) throws DataOperationException;

}
