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
package com.ajah.user.alert.data;

import java.util.List;

import com.ajah.user.alert.UserAlert;
import com.ajah.user.alert.UserAlertId;
import com.ajah.user.alert.UserAlertStatus;
import com.ajah.user.alert.UserAlertType;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.criteria.SubCriteria;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.util.StringUtils;


/**
 *  MySQL-based implementation of {@link UserAlertDao}. 
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserAlertDaoImpl extends AbstractAjahDao<UserAlertId, UserAlert, UserAlert> implements UserAlertDao {

	@Override
	public List<UserAlert> list(UserAlertType type, UserAlertStatus status, long page, long count) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("created_date", Order.DESC));
	}

	/**
	 * @see com.ajah.user.alert.data.UserAlertDao#count(com.ajah.user.alert.UserAlertType,
	 *      com.ajah.user.alert.UserAlertStatus)
	 */
	@Override
	public long count(UserAlertType type, UserAlertStatus status) throws DataOperationException {
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
	 * @see com.ajah.user.alert.data.UserAlertDao#searchCount(String search)
	 */
	@Override
	public int searchCount(String search) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (!StringUtils.isBlank(search)) {
			String pattern = "%" + search.replaceAll("\\*", "%") + "%";
			criteria.and(new SubCriteria().orLike("subject", pattern).orLike("body", pattern));
		}
		return super.count(criteria);
	}

}