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
package com.ajah.servlet;

import javax.servlet.http.HttpServletRequest;

import com.ajah.util.FromStringable;
import com.ajah.util.ToStringable;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class UserAgent implements FromStringable, ToStringable {

	private String raw;

	private UserAgent(String raw) {
		this.raw = raw;
	}

	public static UserAgent from(HttpServletRequest request) {
		return new UserAgent(request.getHeader("User-Agent"));
	}

	@Override
	public String toString() {
		return this.raw;
	}
}
