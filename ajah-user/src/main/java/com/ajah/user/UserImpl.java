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
package com.ajah.user;

import javax.persistence.Transient;

import com.ajah.user.info.UserInfo;

import lombok.Data;

/**
 * Basic implmentation of User interface.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Data
public class UserImpl implements User {

	protected UserId id;
	protected String username;
	protected UserStatus status;
	protected UserStatusReason statusReason;
	protected UserType type;

	@Transient
	protected UserInfo userInfo;

}
