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
package com.ajah.http;

import com.ajah.util.FromStringable;
import com.ajah.util.ToStringable;

/**
 * Class that represents a UserAgent, exposing capabilities that are known about
 * it. Currently incomplete and only capable of returning a {@link Browser}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class UserAgent implements FromStringable, ToStringable {

	private String raw;

	private UserAgent(String raw) {
		this.raw = raw;
	}

	@Override
	public String toString() {
		return this.raw;
	}

	/**
	 * Returns the browser that matches this user agent.
	 * 
	 * @return The matching browser as determined by {@link Browser#get(String)}
	 *         .
	 */
	public Browser getBrowser() {
		return Browser.get(this.raw);
	}

	/**
	 * Retrieves a UserAgent from a User-Agent http header.
	 * 
	 * @param header
	 *            The User-Agent header.
	 * @return The matching UserAgent
	 */
	public static UserAgent from(String header) {
		return new UserAgent(header);
	}

}
