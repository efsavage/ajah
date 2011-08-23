/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.user.UserId;
import com.ajah.user.info.UserInfoImpl;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserInfoDao extends AbstractAjahDao<UserId, UserInfoImpl> {

	// private static final Logger log =
	// Logger.getLogger(UserDao.class.getName());

//	/**
//	 * INSERTs a {@link UserInfo} entity.
//	 * 
//	 * @param userInfo
//	 *            UserInfo entity to insert, required.
//	 */
//	@Override
//	public int insert(UserInfo userInfo) {
//		AjahUtils.requireParam(userInfo, "userInfo");
//		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
//		return this.jdbcTemplate
//				.update("INSERT INTO user_info (user_id, first_name, middle_name, last_name, birth_day, birth_month, birth_year, primary_email_id) VALUES (?,?,?,?,?,?,?,?)",
//						new Object[] { userInfo.getId().getId(), userInfo.getFirstName(), userInfo.getMiddleName(), userInfo.getLastName(),
//								userInfo.getBirthDay(), userInfo.getBirthMonth() == null ? null : Integer.valueOf(userInfo.getBirthMonth().getId()),
//								userInfo.getBirthYear(), userInfo.getPrimaryEmailId().getId() });
//	}

}