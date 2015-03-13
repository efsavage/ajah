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

import javax.servlet.http.HttpServletRequest;

import com.ajah.http.UserAgent;
import com.ajah.util.AjahUtils;

/**
 * Utilities related to HTTP requests.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class RequestUtils {

	/**
	 * Returns the IP of the requester.
	 * 
	 * @param request
	 * @return IP of the requester, may be null.
	 */
	public static String getIp(final HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * Returns the 'Referer' header from a request.
	 * 
	 * @param request
	 *            The request to analyze.
	 * @return The referer, may be null.
	 */
	public static String getReferer(final HttpServletRequest request) {
		AjahUtils.requireParam(request, "request");
		return request.getHeader("Referer");
	}

	/**
	 * Returns the {@link UserAgent} from a request.
	 * 
	 * @param request
	 *            The request to analyze.
	 * @return The user agent.
	 * @see UserAgent#from(String)
	 */
	public static UserAgent getUserAgent(final HttpServletRequest request) {
		AjahUtils.requireParam(request, "request");
		return UserAgent.from(request.getHeader("User-Agent"));
	}

	/**
	 * Returns the raw User-Agent header from a request.
	 * 
	 * @param request
	 *            The request to analyze.
	 * @return The user agent.
	 */
	public static String getUserAgentString(final HttpServletRequest request) {
		AjahUtils.requireParam(request, "request");
		return request.getHeader("User-Agent");
	}

}
