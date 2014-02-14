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
import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountSetting;
import com.ajah.user.account.AccountSettingId;
import com.ajah.user.account.AccountSettingStatus;
import com.ajah.user.account.AccountSettingType;

/**
 * MySQL-based implementation of {@link AccountSettingDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class AccountSettingDaoImpl extends AbstractAjahDao<AccountSettingId, AccountSetting, AccountSetting> implements AccountSettingDao {

	/**
	 * @see com.ajah.user.account.data.AccountSettingDao#count(com.ajah.user.account.AccountSettingType,
	 *      com.ajah.user.account.AccountSettingStatus)
	 */
	@Override
	public long count(final AccountSettingType type, final AccountSettingStatus status) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @throws DataOperationException
	 * @see com.ajah.user.account.data.AccountSettingDao#find(com.ajah.user.account.AccountId,
	 *      java.lang.String)
	 */
	@Override
	public AccountSetting find(final AccountId accountId, final String name) throws DataOperationException {
		return super.find(new Criteria().eq(accountId).eq("name", name));
	}

	/**
	 * @throws DataOperationException
	 * @see com.ajah.user.account.data.AccountSettingDao#list(com.ajah.user.account.AccountId)
	 */
	@Override
	public List<AccountSetting> list(final AccountId accountId) throws DataOperationException {
		return super.list(accountId);
	}

	@Override
	public List<AccountSetting> list(final AccountSettingType type, final AccountSettingStatus status, final long page, final long count) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("name", Order.ASC));
	}

}
