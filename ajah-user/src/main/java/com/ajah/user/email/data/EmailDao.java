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

import java.util.List;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailStatus;
import com.ajah.user.email.EmailType;
import com.ajah.util.data.format.EmailAddress;

/**
 * DAO interface for {@link Email}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface EmailDao extends AjahDao<EmailId, Email> {

	/**
	 * Returns a list of {@link Email}s that match the specified criteria.
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
	List<Email> list(EmailType type, EmailStatus status, long page, long count) throws DataOperationException;

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
	long count(EmailType type, EmailStatus status) throws DataOperationException;

	/**
	 * Locates an email by the address field.
	 * 
	 * @param emailAddress
	 *            The address to search for.
	 * @return The email entity, if found, otherwise null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	Email find(EmailAddress emailAddress) throws DataOperationException;

}
