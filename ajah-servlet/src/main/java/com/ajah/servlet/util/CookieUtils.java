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

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ajah.util.AjahUtils;

/**
 * Class for utilities that deal with {@link Cookie}s.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class CookieUtils {

	/**
	 * Clears all cookies by setting {@link Cookie#setMaxAge(int)} to 0;
	 * 
	 * @param request
	 *            Request to get cookies from, required.
	 * @param response
	 *            Response to set updated cookies on, required.
	 */
	public static void clearAllCookies(final HttpServletRequest request, final HttpServletResponse response) {
		AjahUtils.requireParam(request, "request");
		AjahUtils.requireParam(response, "response");
		if (request.getCookies() == null) {
			return;
		}
		for (final Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	/**
	 * Finds a cookie by name and returns it if found.
	 * 
	 * @param request
	 *            Request to look for cookies on, required.
	 * @param name
	 *            Name of cookies to look for, required.
	 * @return Cookie if found, otherwise null.
	 */
	public static Cookie getCookie(final HttpServletRequest request, final String name) {
		AjahUtils.requireParam(request, "request");
		AjahUtils.requireParam(name, "name");
		final Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * Alternate signature for
	 * {@link CookieUtils#getCookie(HttpServletRequest, String)}.
	 * 
	 * @param request
	 *            Request to look for cookies on, required.
	 * @param name
	 *            Name of cookies to look for, required.
	 * @return Cookie if found, otherwise null.
	 */
	public static Cookie getCookie(final ServletRequest request, final String name) {
		return getCookie((HttpServletRequest) request, name);
	}

}
