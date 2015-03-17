/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.signup;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

import com.ajah.geo.iso.ISOCountry;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserSourceId;
import com.ajah.util.Identifiable;
import com.ajah.util.data.Month;

/**
 * A signup is a registration, tracking information that doesn't necessarily
 * need to be tied to a user but is useful to associate to the registration
 * event.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class SignUp implements Identifiable<SignUpId> {

	private SignUpId id;
	private String username;
	private SignUpStatus status;
	private SignUpType type;
	private Date created;
	private UserId userId;

	protected Integer birthDay;
	protected Month birthMonth;
	protected Integer birthYear;

	protected String ip;
	protected String userAgent;

	protected UserSourceId source;
	protected String promoCode;
	protected String referralSource;
	protected String referralSourceOther;

	protected String email;
	protected String address1;
	protected String address2;
	protected String address3;
	protected String city;
	protected String state;
	protected ISOCountry country;

	@Transient
	User user;

	@Transient
	UserInfo userInfo;

}
