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
package com.ajah.user.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserSetting;
import com.ajah.user.UserSettingId;
import com.ajah.user.UserSettingStatus;
import com.ajah.user.UserSettingType;


/**
 *  MySQL-based implementation of {@link UserSettingDao}. 
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserSettingDaoImpl extends AbstractAjahDao<UserSettingId, UserSetting, UserSetting> implements UserSettingDao {

	@Override
	public List<UserSetting> list(UserSettingType type, UserSettingStatus status, long page, long count) throws DataOperationException {
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
	 * @see com.ajah.user.data.UserSettingDao#count(com.ajah.user.UserSettingType,
	 *      com.ajah.user.UserSettingStatus)
	 */
	@Override
	public long count(UserSettingType type, UserSettingStatus status) throws DataOperationException {
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