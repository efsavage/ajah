/*
 *  Copyright 2011-2013 Eric F. Savage, code@efsavage.com
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

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailImpl;
import com.ajah.util.AjahUtils;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class EmailDaoImpl extends AbstractAjahDao<EmailId, Email, EmailImpl> implements EmailDao {

	@Override
	public Email findByAddress(final String address) throws DataOperationException {
		return super.findByField("address", address);
	}

	/**
	 * INSERTs an {@link Email} entity.
	 * 
	 * @param email
	 *            The {@link Email} to insert.
	 * @return Number of rows affected.
	 */
	@Override
	public DataOperationResult<Email> insert(final Email email) {
		// TODO Necessary?
		AjahUtils.requireParam(email, "email");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		return new DataOperationResult<>(email, this.jdbcTemplate.update("INSERT INTO email (email_id, user_id, address, status) VALUES (?,?,?,?)", new Object[] { email.getId().getId(),
				email.getUserId().getId(), email.getAddress().toString(), email.getStatus().getId() }));
	}

}
