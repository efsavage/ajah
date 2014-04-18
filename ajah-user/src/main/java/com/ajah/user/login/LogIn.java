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
package com.ajah.user.login;

import java.util.Date;

import lombok.Data;

import com.ajah.user.User;

/**
 * A login is a data record of an authentication attempt by a user. It should
 * correspond to any change or confirmation of the level of authentication
 * during a session.
 * 
 * For example, in a web application, the initial request of a session may
 * contain a cookie that automatically logs a user in, this would be recorded.
 * Requests made throughout the session would not be recorded, but if the user
 * was re-prompted for a password, such as during a password change, that would
 * be recorded.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Data
public class LogIn {

	protected String ip;
	protected Date created;
	protected LogInStatus status;
	protected LogInSource source;
	protected LogInType type;
	protected String username;
	protected User user;
	protected String token;

}
