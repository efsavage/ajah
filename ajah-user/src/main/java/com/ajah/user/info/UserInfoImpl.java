/*
 *  Copyright 2011-2013 Eric F. Savage, code@efsavage.com
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
import com.ajah.util.StringUtils;
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
	private String displayName;

	/**
	 * Empty public constructor.
	 */
	public UserInfoImpl() {
		// Empty
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

	/**
	 * Returns the full name in the format [First] [Middle] [Last].
	 * 
	 * @return The full name of the user.
	 */
	@Override
	public String getFullName() {
		if (StringUtils.isBlank(this.lastName)) {
			if (StringUtils.isBlank(this.firstName)) {
				if (StringUtils.isBlank(this.middleName)) {
					return "";
				}
				return this.middleName + (this.middleName.length() == 1 ? "." : "");
			}
			return this.lastName;
		}
		// Last name is populated
		if (StringUtils.isBlank(this.firstName)) {
			if (StringUtils.isBlank(this.middleName)) {
				return this.lastName;
			}
			return (this.middleName.length() == 1 ? "." : "") + this.lastName;
		}
		// First and last name are populated
		if (StringUtils.isBlank(this.middleName)) {
			return this.firstName + " " + this.lastName;
		}
		return this.firstName + " " + (this.middleName.length() == 1 ? "." : "") + " " + this.lastName;
	}

	/**
	 * Returns the full name in the format [Last], [First] [Middle]
	 * 
	 * @return The full name of the user.
	 */
	@Override
	public String getFullNameLastFirst() {
		if (StringUtils.isBlank(this.lastName)) {
			if (StringUtils.isBlank(this.firstName)) {
				if (StringUtils.isBlank(this.middleName)) {
					return "";
				}
				return this.middleName + (this.middleName.length() == 1 ? "." : "");
			}
			return this.lastName;
		}
		// Last name is populated
		if (StringUtils.isBlank(this.firstName)) {
			if (StringUtils.isBlank(this.middleName)) {
				return this.lastName;
			}
			return this.lastName + ", " + (this.middleName.length() == 1 ? "." : "");
		}
		// First and last name are populated
		if (StringUtils.isBlank(this.middleName)) {
			return this.lastName + ", " + this.firstName;
		}
		return this.lastName + ", " + this.firstName + " " + (this.middleName.length() == 1 ? "." : "");
	}
}
