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

import com.ajah.geo.iso.ISOCountry;
import com.ajah.user.UserId;
import com.ajah.user.email.Email;
import com.ajah.user.email.EmailId;
import com.ajah.util.Identifiable;
import com.ajah.util.data.Gender;
import com.ajah.util.data.Month;

/**
 * UserInfo is information about a user that is not important for most
 * operations, but is standard enough that it is not an application-specific
 * setting/property.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public interface UserInfo extends Identifiable<UserId> {

	/**
	 * Sets the user ID.
	 * 
	 * @param userId
	 *            The User ID, should not be null.
	 */
	@Override
	void setId(final UserId userId);

	/**
	 * Sets the first name of the user.
	 * 
	 * @param firstName
	 *            The first name of the user.
	 */
	void setFirstName(final String firstName);

	/**
	 * Sets the middle name of the user.
	 * 
	 * @param middleName
	 *            The middle name of the user.
	 */
	void setMiddleName(final String middleName);

	/**
	 * The last name of the user.
	 * 
	 * @param lastName
	 *            The last name of the user.
	 */
	void setLastName(final String lastName);

	/**
	 * Sets the birth day of the user.
	 * 
	 * @param birthDay
	 *            The birth day of the user, should be >= 0 and <= 31.
	 */
	void setBirthDay(final Integer birthDay);

	/**
	 * Sets she birth month of the user.
	 * 
	 * @param birthMonth
	 *            The birth month of the user, should be >=0 and <=12
	 */
	void setBirthMonth(final Month birthMonth);

	/**
	 * Sets the birth year of the user.
	 * 
	 * @param birthYear
	 *            The birth year of the user, should be 0 or >=1900 and <=
	 *            current year
	 */
	void setBirthYear(final Integer birthYear);

	/**
	 * Returns the user's primary email. Could be null if the user did not sign
	 * up by email.
	 * 
	 * @return Primary email for the user, may be null. Note that this address
	 *         may be inactive.
	 */
	EmailId getPrimaryEmailId();

	/**
	 * Sets the primary email address for the user.
	 * 
	 * @param id
	 *            Unique ID of the {@link Email}, may be null.
	 */
	void setPrimaryEmailId(final EmailId id);

	/**
	 * Returns the first name of the user.
	 * 
	 * @return The first name of the user.
	 */
	String getFirstName();

	/**
	 * Returns the middle name of the user.
	 * 
	 * @return The middle name of the user, may be null.
	 */
	String getMiddleName();

	/**
	 * Returns the last name of the user.
	 * 
	 * @return The last name of the user, may be null.
	 */
	String getLastName();

	/**
	 * Returns the birth day of the user.
	 * 
	 * @return The birth day of the user, may be null.
	 */
	Integer getBirthDay();

	/**
	 * Returns the birth month of the user.
	 * 
	 * @return The birth month of the user, may be null.
	 */
	Month getBirthMonth();

	/**
	 * Returns the birth year of the user.
	 * 
	 * @return The birth year of the user, may be null.
	 */
	Integer getBirthYear();

	/**
	 * Returns the date the user was created.
	 * 
	 * @return The creation date of the user.
	 */
	Date getCreated();

	/**
	 * Sets the date the user was created.
	 * 
	 * @param date
	 *            THe creation date of the user.
	 */
	void setCreated(final Date date);

	/**
	 * Returns the full name in the format [Last], [First] [Middle]
	 * 
	 * @return The full name of the user.
	 */
	String getFullNameLastFirst();

	/**
	 * Returns the full name in the format [First] [Middle] [Last].
	 * 
	 * @return The full name of the user.
	 */
	String getFullName();

	Gender getGender();

	void setGender(final Gender gender);

	void setCurrentCountry(final ISOCountry isoCountry);

}
