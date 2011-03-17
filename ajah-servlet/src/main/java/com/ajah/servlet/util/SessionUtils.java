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
package com.ajah.servlet.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ajah.util.AjahUtils;

/**
 * Utilities that deal with {@link HttpSession}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class SessionUtils {

	/**
	 * A more explicit heavy-duty version of {@link HttpSession#invalidate()}.
	 * This method will remove all attributes and clear all cookies, in addition
	 * to calling {@link HttpSession#invalidate()}.
	 * 
	 * @param request
	 *            Request to get cookies and session from, required.
	 * @param response
	 *            Response to set updated cookies on. Not required, but cookies
	 *            cannot be cleared if null.
	 */
	public static void eradicate(HttpServletRequest request, HttpServletResponse response) {
		AjahUtils.requireParam(request, "request");
		AjahUtils.requireParam(response, "response");
		HttpSession session = request.getSession(false);
		if (session != null) {
			clearSessionAttributes(session);
			session.invalidate();
		}
		CookieUtils.clearAllCookies(request, response);
	}

	/**
	 * This method will look for a session on the request, and if one is found
	 * call {@link #clearSessionAttributes(HttpSession)}.
	 * 
	 * @param request
	 *            request on which to find a session to clear, required.
	 */
	public static void clearSessionAttributes(HttpServletRequest request) {
		AjahUtils.requireParam(request, "request");
		clearSessionAttributes(request.getSession(false));
	}

	/**
	 * This method will call {@link HttpSession#removeAttribute(String)} on each
	 * attribute to ensure that they are cleared.
	 * 
	 * @param session
	 *            session to clear, required.
	 */
	public static void clearSessionAttributes(HttpSession session) {
		AjahUtils.requireParam(session, "session");
		Enumeration<?> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			session.removeAttribute((String) names.nextElement());
		}
	}

}