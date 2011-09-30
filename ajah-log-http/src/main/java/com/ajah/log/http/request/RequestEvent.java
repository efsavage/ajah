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
package com.ajah.log.http.request;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

import com.ajah.event.Event;
import com.ajah.servlet.HttpMethod;
import com.ajah.servlet.UserAgent;

/**
 * Represents an HTTP request.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class RequestEvent implements Event<RequestEventId> {

	private long start;
	private long end;
	private RequestEventId id;
	private HttpMethod method;
	private String uri;
	private String queryString;
	private String ip;
	private UserAgent userAgent;

	public RequestEvent(HttpServletRequest request) {
		this.id = new RequestEventId(UUID.randomUUID().toString());
		this.start = System.currentTimeMillis();
		this.method = HttpMethod.get(request.getMethod());
		this.uri = request.getRequestURI();
		this.queryString = request.getQueryString();
		this.ip = request.getRemoteAddr();
		this.userAgent = UserAgent.from(request);
	}

	public void complete() {
		this.end = System.currentTimeMillis();
	}

	public long getDuration() {
		return this.end - start;
	}

}
