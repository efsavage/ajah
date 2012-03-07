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
package com.ajah.user.info;

import java.util.Date;

import lombok.Data;

import com.ajah.user.UserId;
import com.ajah.user.email.EmailId;
import com.ajah.util.AjahUtils;
import com.ajah.util.data.Month;

/**
 * UserInfo is information about a user that is not important for most
 * operations, but is standard enough that it is not an application-specific
 * setting/property.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
@Data
public class UserInfoImpl implements UserInfo {

	private UserId id;
	private UserSource source;
	private Date created;
	private Date passwordChanged;
	private String firstName;
	private String middleName;
	private String lastName;
	private String title;
	private String gender;
	private Month birthMonth;
	private Integer birthDay;
	private Integer birthYear;
	private EmailId primaryEmailId;

	public UserInfoImpl() {

	}

	/**
	 * Instantiates a userinfo with userId, which should not be null.
	 * 
	 * @param userId
	 *            ID of user, required.
	 */
	public UserInfoImpl(final UserId userId) {
		AjahUtils.requireParam(userId, "User ID");
		this.id = userId;
	}

}
