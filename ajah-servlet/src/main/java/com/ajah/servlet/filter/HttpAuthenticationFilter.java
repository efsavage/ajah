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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.java.Log;

import com.ajah.util.StringUtils;
import com.ajah.util.config.Config;

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

	protected boolean allowUser(final String auth, final String username, final String password) throws IOException {

		if (auth == null) {
			return false; // no auth
		}
		if (!auth.toUpperCase().startsWith("BASIC ")) {
			return false; // we only do BASIC
		}
		// Get encoded user and password, comes after "BASIC "
		final String userpassEncoded = auth.substring(6);
		// Decode it, using any base 64 decoder
		final sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		final String userpassDecoded = new String(dec.decodeBuffer(userpassEncoded));
		return (username + ":" + password).equals(userpassDecoded);
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
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
		if (!allowUser(auth, username, password)) {
			response.setHeader("WWW-Authenticate", "BASIC realm=\"" + realm + "\"");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			super.doFilter(request, response, chain);
		}
	}

}
