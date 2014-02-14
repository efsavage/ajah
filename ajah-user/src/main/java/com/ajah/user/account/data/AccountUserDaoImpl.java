/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.account.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountUser;
import com.ajah.user.account.AccountUserId;
import com.ajah.user.account.AccountUserStatus;
import com.ajah.user.account.AccountUserType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link AccountUserDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class AccountUserDaoImpl extends AbstractAjahDao<AccountUserId, AccountUser, AccountUser> implements AccountUserDao {

	@Override
	public String getTableName() {
		return "account__user";
	}

	@Override
	public List<AccountUser> list(final AccountId accountId, final AccountUserType type, final AccountUserStatus status, final long page, final long count, final String search,
			final String[] searchFields) throws DataOperationException {
		final Criteria criteria = new Criteria().eq(accountId);
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		if (!StringUtils.isBlank(search)) {

		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("created_date", Order.ASC));
	}

	/**
	 * @see com.ajah.user.account.data.AccountUserDao#list(com.ajah.user.UserId)
	 */
	@Override
	public List<AccountUser> list(final AccountId accountId, final int page, final int count) throws DataOperationException {
		return super.list(accountId, page, count);
	}

	/**
	 * @see com.ajah.user.account.data.AccountUserDao#list(com.ajah.user.UserId)
	 */
	@Override
	public List<AccountUser> list(final UserId userId, final int page, final int count) throws DataOperationException {
		return super.list(userId, page, count);
	}
}
