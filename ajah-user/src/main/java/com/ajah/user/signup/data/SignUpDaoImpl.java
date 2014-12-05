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
package com.ajah.user.signup.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.signup.SignUp;
import com.ajah.user.signup.SignUpId;
import com.ajah.user.signup.SignUpStatus;
import com.ajah.user.signup.SignUpType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link SignUpDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class SignUpDaoImpl extends AbstractAjahDao<SignUpId, SignUp, SignUp> implements SignUpDao {

	/**
	 * @see com.ajah.user.signup.data.SignUpDao#count(com.ajah.user.signup.SignUpType,
	 *      com.ajah.user.signup.SignUpStatus)
	 */
	@Override
	public long count(final SignUpType type, final SignUpStatus status) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	@Override
	public List<SignUp> list(final SignUpType type, final SignUpStatus status, final long page, final long count) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("username", Order.ASC));
	}

	/**
	 * @see com.ajah.user.signup.data.SignUpDao#searchCount(String search)
	 */
	@Override
	public int searchCount(final String search) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (!StringUtils.isBlank(search)) {
			criteria.like("username", "%" + search.replaceAll("\\*", "%") + "%");
		}
		return super.count(criteria);
	}

}
