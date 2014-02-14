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
import com.ajah.spring.jdbc.criteria.SubCriteria;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.account.Account;
import com.ajah.user.account.AccountId;
import com.ajah.user.account.AccountStatus;
import com.ajah.user.account.AccountType;
import com.ajah.util.ArrayUtils;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link AccountDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class AccountDaoImpl extends AbstractAjahDao<AccountId, Account, Account> implements AccountDao {

	/**
	 * @see com.ajah.user.account.data.AccountDao#count(com.ajah.user.account.AccountType,
	 *      com.ajah.user.account.AccountStatus)
	 */
	@Override
	public long count(final AccountType type, final AccountStatus status) throws DataOperationException {
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
	public List<Account> list(final AccountType type, final AccountStatus status, final long page, final long count, final String search, final String[] searchFields) throws DataOperationException {
		final Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		if (!StringUtils.isBlank(search)) {
			if (ArrayUtils.isEmpty(searchFields)) {
				criteria.like("name", search);
			} else if (searchFields.length == 1) {
				criteria.like("name", search);
			} else {
				final SubCriteria subCriteria = new SubCriteria();
				for (final String searchField : searchFields) {
					subCriteria.orLike(searchField, search);
				}
				criteria.and(subCriteria);
			}

		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("name", Order.ASC));
	}

}
