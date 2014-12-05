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

import java.util.List;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailVerification;
import com.ajah.user.email.EmailVerificationId;
import com.ajah.user.email.EmailVerificationStatus;
import com.ajah.user.email.EmailVerificationType;

/**
 * DAO interface for {@link EmailVerification}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface EmailVerificationDao extends AjahDao<EmailVerificationId, EmailVerification> {

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
	List<EmailVerification> list(final EmailVerificationType type, final EmailVerificationStatus status, final long page, final long count) throws DataOperationException;

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
	long count(final EmailVerificationType type, final EmailVerificationStatus status) throws DataOperationException;

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

	EmailVerification find(final String code) throws DataOperationException;

	EmailVerification recent(final EmailId emailId) throws DataOperationException;

}
