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

package com.ajah.log.http.servlet.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.ajah.log.http.RequestEventHandler;
import com.ajah.log.http.request.RequestEvent;
import com.ajah.log.http.request.data.RequestEventManager;
import com.ajah.servlet.AjahFilter;
import com.ajah.util.AjahUtils;

/**
 * Logs HTTP requests. Will try to determine on it's own if a request should be
 * logged, but this behavior can be overridden by setting a {@link Boolean}
 * request attribute "logMe".
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Log
public class AccessLoggerFilter extends AjahFilter {

	/**
	 * Determines if a request should be logged.
	 * 
	 * @param request
	 *            The request to examine, required.
	 * @return true if the request should be logged, otherwise false.
	 */
	private static boolean isLoggable(final HttpServletRequest request) {
		if (request.getAttribute("logMe") != null) {
			return Boolean.TRUE.equals(request.getAttribute("logMe"));
		}
		final String uri = request.getRequestURI();
		return !uri.equals("/favicon.ico") && !uri.endsWith(".css") && !uri.endsWith(".js");
	}

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private RequestEventManager requestEventManager;

	/**
	 * Logs requests.
	 */
	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final RequestEvent requestEvent = new RequestEvent(request);
		request.setAttribute("ajahRequestEvent", requestEvent);
		try {
			chain.doFilter(request, response);
		} finally {
			requestEvent.setUserId((String) request.getAttribute("ajahRequestUserId"));
			requestEvent.setStatusCode(response.getStatus());
			requestEvent.setContentType(response.getContentType());
			requestEvent.complete();
			if (isLoggable(request)) {
				log.finest(requestEvent.getUri() + " took " + requestEvent.getDuration() + "ms");
				AjahUtils.requireParam(this.taskExecutor, "taskExecutor");
				this.taskExecutor.execute(new RequestEventHandler(requestEvent, this.requestEventManager));
			}
		}
	}

}
