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
package com.ajah.user.signup;

import java.util.Date;

import lombok.Data;

import com.ajah.user.User;
import com.ajah.user.UserType;
import com.ajah.user.info.UserSourceId;

/**
 * A signUp is a bean used during a registration attempt by a user.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Data
public class SignUp {

	protected String ip;
	protected Date created;
	protected SignUpStatus status;
	protected UserSourceId source;
	protected UserType type;
	protected String username;
	protected User user;

}
