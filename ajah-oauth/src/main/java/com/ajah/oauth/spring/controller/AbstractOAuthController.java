/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.oauth.spring.controller;

import javax.servlet.http.HttpServletRequest;

import com.ajah.user.User;
import com.ajah.user.login.LogIn;

/**
 * Basic operations for provider-specific controller.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public abstract class AbstractOAuthController {

	protected static User getUser(final HttpServletRequest request) {
		final LogIn logIn = (LogIn) request.getSession().getAttribute("jjLogIn");
		if (logIn == null) {
			return null;
		}
		return logIn.getUser();
	}

}
