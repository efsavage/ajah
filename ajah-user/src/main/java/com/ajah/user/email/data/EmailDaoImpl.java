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

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailStatus;
import com.ajah.user.email.EmailType;
import com.ajah.util.AjahUtils;
import com.ajah.util.data.format.EmailAddress;

/**
 * MySQL-based implementation of {@link EmailDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class EmailDaoImpl extends AbstractAjahDao<EmailId, Email, Email> implements EmailDao {

	/**
	 * @see com.ajah.user.email.data.EmailDao#count(com.ajah.user.email.EmailType,
	 *      com.ajah.user.email.EmailStatus)
	 */
	@Override
	public long count(final UserId userId, final EmailType type, final EmailStatus status) throws DataOperationException {
		final Criteria criteria = new Criteria().eq(userId);
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.user.email.data.EmailDao#find(com.ajah.util.data.format.EmailAddress)
	 */
	@Override
	public Email find(final EmailAddress emailAddress) throws DataOperationException {
		AjahUtils.requireParam(emailAddress, "emailAddress");
		return super.find("address", emailAddress.toString());
	}

	@Override
	public List<Email> list(final UserId userId, final EmailType type, final EmailStatus status, final long page, final long count) throws DataOperationException {
		final Criteria criteria = new Criteria().eq(userId);
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("address", Order.ASC));
	}

}
