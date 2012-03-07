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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ajah.util.AjahUtils;

/**
 * Utilities that deal with {@link HttpSession}s.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public class SessionUtils {

	/**
	 * Adds a message to list that is stored in the session context.
	 * 
	 * @param message
	 * @param session
	 */
	public static void addConfirmationMessage(final String message, final HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> confirmationMessages = (List<String>) session.getAttribute("ajahConfirmationMessages");
		if (confirmationMessages == null) {
			confirmationMessages = new ArrayList<>();
			session.setAttribute("ajahConfirmationMessages", confirmationMessages);
		}
		confirmationMessages.add(message);
	}

	/**
	 * @param session
	 */
	public static void clearConfirmationMessages(final HttpSession session) {
		if (session != null) {
			session.removeAttribute("ajahConfirmationMessages");
		}
	}

	/**
	 * This method will look for a session on the request, and if one is found
	 * call {@link #clearSessionAttributes(HttpSession)}.
	 * 
	 * @param request
	 *            request on which to find a session to clear, required.
	 */
	public static void clearSessionAttributes(final HttpServletRequest request) {
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
	public static void clearSessionAttributes(final HttpSession session) {
		AjahUtils.requireParam(session, "session");
		final Enumeration<?> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			session.removeAttribute((String) names.nextElement());
		}
	}

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
	public static void eradicate(final HttpServletRequest request, final HttpServletResponse response) {
		AjahUtils.requireParam(request, "request");
		AjahUtils.requireParam(response, "response");
		final HttpSession session = request.getSession(false);
		if (session != null) {
			clearSessionAttributes(session);
			session.invalidate();
		}
		CookieUtils.clearAllCookies(request, response);
	}

	/**
	 * Returns the list of confirmation messages stored on the session.
	 * 
	 * @param session
	 *            The session to look for messages on, may be null.
	 * @return The list of confirmation messages stored on the session. May be
	 *         null or empty.
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getConfirmationMessages(final HttpSession session) {
		if (session != null) {
			return (List<String>) session.getAttribute("ajahConfirmationMessages");
		}
		return null;
	}

}
