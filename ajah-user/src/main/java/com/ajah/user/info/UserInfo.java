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

import com.ajah.user.UserId;

/**
 * UserInfo is information about a user that is not important for most
 * operations, but is standard enough that it is not an application-specific
 * setting/property.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public interface UserInfo {

	/**
	 * Unique ID of the user.
	 * 
	 * @return Unique ID of the user, may be null.
	 */
	UserId getUserId();

	/**
	 * Sets the user ID.
	 * 
	 * @param userId
	 *            The User ID, should not be null.
	 */
	void setUserId(UserId userId);

	/**
	 * Sets the first name of the user.
	 * 
	 * @param firstName
	 *            The first name of the user.
	 */
	void setFirstName(String firstName);

	/**
	 * Sets the middle name of the user.
	 * 
	 * @param middleName
	 *            The middle name of the user.
	 */
	void setMiddleName(String middleName);

	/**
	 * The last name of the user.
	 * 
	 * @param lastName
	 *            The last name of the user.
	 */
	void setLastName(String lastName);

	/**
	 * Sets the birth day of the user.
	 * 
	 * @param birthDay
	 *            The birth day of the user, should be >= 0 and <= 31.
	 */
	void setBirthDay(int birthDay);

	/**
	 * Sets she birth month of the user.
	 * 
	 * @param birthMonth
	 *            The birth month of the user, should be >=0 and <=12
	 */
	void setBirthMonth(int birthMonth);

	/**
	 * Sets the birth year of the user.
	 * 
	 * @param birthYear
	 *            The birth year of the user, should be 0 or >=1900 and <=
	 *            current year
	 */
	void setBirthYear(int birthYear);

}