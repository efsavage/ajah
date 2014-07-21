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

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.email.EmailId;
import com.ajah.user.email.EmailVerification;
import com.ajah.user.email.EmailVerificationId;
import com.ajah.user.email.EmailVerificationStatus;
import com.ajah.user.email.EmailVerificationType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link EmailVerificationDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class EmailVerificationDaoImpl extends AbstractAjahDao<EmailVerificationId, EmailVerification, EmailVerification> implements EmailVerificationDao {

	@Override
	public List<EmailVerification> list(EmailVerificationType type, EmailVerificationStatus status, long page, long count) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("name", Order.ASC));
	}

	/**
	 * @see com.ajah.user.email.data.EmailVerificationDao#count(com.ajah.user.email.EmailVerificationType,
	 *      com.ajah.user.email.EmailVerificationStatus)
	 */
	@Override
	public long count(EmailVerificationType type, EmailVerificationStatus status) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.user.email.data.EmailVerificationDao#searchCount(String
	 *      search)
	 */
	@Override
	public int searchCount(String search) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (!StringUtils.isBlank(search)) {
			criteria.like("name", "%" + search.replaceAll("\\*", "%") + "%");
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.user.email.data.EmailVerificationDao#find(String)
	 */
	@Override
	public EmailVerification find(String code) throws DataOperationException {
		return super.find(new Criteria().eq("code", code));
	}

	/**
	 * @see com.ajah.user.email.data.EmailVerificationDao#recent(EmailId)
	 */
	@Override
	public EmailVerification recent(EmailId emailId) throws DataOperationException {
		return super.find(new Criteria().eq(emailId).orderBy("created_date", Order.DESC).rows(1));
	}

}
