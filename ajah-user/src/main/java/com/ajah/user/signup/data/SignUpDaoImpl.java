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

import com.ajah.user.signup.SignUp;
import com.ajah.user.signup.SignUpId;
import com.ajah.user.signup.SignUpStatus;
import com.ajah.user.signup.SignUpType;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.util.StringUtils;


/**
 *  MySQL-based implementation of {@link SignUpDao}. 
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class SignUpDaoImpl extends AbstractAjahDao<SignUpId, SignUp, SignUp> implements SignUpDao {

	@Override
	public List<SignUp> list(SignUpType type, SignUpStatus status, long page, long count) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("username", Order.ASC));
	}

	/**
	 * @see com.ajah.user.signup.data.SignUpDao#count(com.ajah.user.signup.SignUpType,
	 *      com.ajah.user.signup.SignUpStatus)
	 */
	@Override
	public long count(SignUpType type, SignUpStatus status) throws DataOperationException {
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
	 * @see com.ajah.user.signup.data.SignUpDao#searchCount(String search)
	 */
	@Override
	public int searchCount(String search) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (!StringUtils.isBlank(search)) {
			criteria.like("username", "%" + search.replaceAll("\\*", "%") + "%");
		}
		return super.count(criteria);
	}

}