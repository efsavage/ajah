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
package com.ajah.user.social.data;

import java.util.List;

import com.ajah.user.social.UserFollow;
import com.ajah.user.social.UserFollowId;
import com.ajah.user.social.UserFollowStatus;
import com.ajah.user.social.UserFollowType;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;


/**
 *  MySQL-based implementation of {@link UserFollowDao}. 
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserFollowDaoImpl extends AbstractAjahDao<UserFollowId, UserFollow, UserFollow> implements UserFollowDao {

	@Override
	public List<UserFollow> list(UserFollowType type, UserFollowStatus status, long page, long count) throws DataOperationException {
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
	 * @see com.ajah.user.social.data.UserFollowDao#count(com.ajah.user.social.UserFollowType,
	 *      com.ajah.user.social.UserFollowStatus)
	 */
	@Override
	public long count(UserFollowType type, UserFollowStatus status) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

}