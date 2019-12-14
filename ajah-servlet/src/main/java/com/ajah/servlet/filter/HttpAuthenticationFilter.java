/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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
package com.ajah.servlet.filter;

import com.ajah.util.StringUtils;
import com.ajah.util.config.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.java.Log;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * A filter for easily adding HTTP authentication to a webapp (in addition to
 * any in-app authentication).
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
@Data
@EqualsAndHashCode(callSuper = true)
public class HttpAuthenticationFilter extends BaseFilter {

	@SuppressWarnings("restriction")
	protected static boolean allowUser(final String auth, final String username, final String password) {

		if (auth == null) {
			return false; // no auth
		}
		if (!auth.toUpperCase().startsWith("BASIC ")) {
			return false; // we only do BASIC
		}
		// Get encoded user and password, comes after "BASIC "
		final String userpassEncoded = auth.substring(6);
		// Decode it, using any base 64 decoder
		final Base64.Decoder dec = Base64.getDecoder();
		final String userpassDecoded = new String(dec.decode(userpassEncoded));
		return (username + ":" + password).equals(userpassDecoded);
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
		if (((HttpServletRequest) req).getRequestURI().equals("/favicon.ico")) {
			super.doFilter(req, res, chain);
		}
		if (!Config.i.getBoolean("ajah.http-auth.enable", true)) {
			// If this filter is being invoked, we assume it's supposed to be
			// enabled unless explicitly configured otherwise.
			log.finest("HTTP Authentication disabled");
			super.doFilter(req, res, chain);
		}
		final String username = Config.i.get("ajah.http-auth.username", null);
		if (StringUtils.isBlank(username)) {
			log.warning("Auth filter is enabled but no username is set");
			throw new ServletException("Authentication setup not completed");
		}
		final String password = Config.i.get("ajah.http-auth.password", null);
		if (StringUtils.isBlank(password)) {
			log.warning("Auth filter is enabled but no password is set");
			throw new ServletException("Authentication setup not completed");
		}
		final String realm = Config.i.get("ajah.http-auth.realm", "Please Log In");

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final String auth = request.getHeader("Authorization");
		// Enable params to allow things like status checkers and scripts to
		// access the app
		if (StringUtils.isBlank(auth) && Config.i.getBoolean("ajah.http-auth.enable-params", false) && username.equals(request.getParameter("user"))
				&& password.equals(request.getParameter("password"))) {
			super.doFilter(request, response, chain);
		} else if (allowUser(auth, username, password)) {
			super.doFilter(request, response, chain);
		} else {
			log.finest("Supplied auth header: " + auth);
			log.finest("Params enabled: " + Config.i.getBoolean("ajah.http-auth.enable-params", false));
			log.finest("Supplied username: " + request.getParameter("user"));
			log.finest("Supplied password: " + request.getParameter("password"));
			response.setHeader("WWW-Authenticate", "BASIC realm=\"" + realm + "\"");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

}
