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
package com.ajah.user.login;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

import com.ajah.user.User;
import com.ajah.user.info.UserInfo;
import com.ajah.util.Identifiable;

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
public class LogIn implements Identifiable<LogInId> {

	private LogInId id;
	private LogInStatus status;
	private LogInSource source;
	private LogInType type;
	private Date created;
	protected String username;
	protected String ip;
	/**
	 * retry can be used for an intermittent failure, or some sequence where it
	 * is cleaner to re-attempt the login (such as automatic reactivation).
	 */
	private boolean retry;

	@Transient
	protected User user;

	@Transient
	protected UserInfo userInfo;

	@Transient
	protected String token;

}
