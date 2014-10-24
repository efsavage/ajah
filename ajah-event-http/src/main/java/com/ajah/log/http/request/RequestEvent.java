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

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

import com.ajah.event.Event;
import com.ajah.http.Browser;
import com.ajah.http.HttpMethod;
import com.ajah.http.UserAgent;

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
	private int minute;
	private int hour;
	private int day;
	private int month;
	private int year;
	private RequestEventId id;
	private HttpMethod method;
	private String uri;
	private String queryString;
	private String ip;
	private UserAgent userAgent;
	private Browser browser;
	private String userId;
	private int statusCode;
	private String contentType;

	/**
	 * Populates a RequestEvent from a servlet request.
	 * 
	 * @param request
	 */
	public RequestEvent(final HttpServletRequest request) {
		this.id = new RequestEventId(UUID.randomUUID().toString());
		this.start = System.currentTimeMillis();
		this.method = HttpMethod.get(request.getMethod());
		this.uri = request.getRequestURI();
		this.queryString = request.getQueryString();
		this.ip = request.getRemoteAddr();
		this.userAgent = UserAgent.from(request.getHeader("User-Agent"));
		this.browser = userAgent.getBrowser();
	}

	/**
	 * Invoked when the request is completed, establishing the end time.
	 * 
	 * @see com.ajah.event.Event#complete()
	 */
	@Override
	public void complete() {
		this.end = System.currentTimeMillis();
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.start);
		this.year = cal.get(Calendar.YEAR);
		this.month = cal.get(Calendar.MONTH) + (this.year * 12);
		this.day = cal.get(Calendar.DAY_OF_YEAR) + (this.month * 31);
		this.hour = cal.get(Calendar.HOUR_OF_DAY) + (this.day * 24);
		this.minute = cal.get(Calendar.MINUTE) + (this.hour * 60);
	}

	/**
	 * Returns the duration of the request in milliseconds. If the request has
	 * not been completed, will base it on the current timestamp.
	 * 
	 * @return The duration of the request in milliseconds.
	 */
	public long getDuration() {
		if (this.end == 0) {
			return System.currentTimeMillis() - this.start;
		}
		return this.end - this.start;
	}

}
