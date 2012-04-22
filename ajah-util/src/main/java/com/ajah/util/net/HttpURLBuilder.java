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
package com.ajah.util.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * This class builds a URL to avoid error-prone string concatenation, especially
 * regarding adding/removing/editing parameters, which the built-in {@link URL}
 * class does not handle.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@Accessors(chain = true)
public class HttpURLBuilder {

	private String host;
	private String path;
	private int port = 0;
	private boolean secure;
	private Map<String, String> singleValueParameters;

	/**
	 * Constructs an empty URLBuilder.
	 */
	public HttpURLBuilder() {
		// Empty
	}

	/**
	 * Creates a complete "external" URL.
	 * 
	 * @see java.lang.Object#toString()
	 * @throws IllegalArgumentException
	 *             If the URL could not be constructed.
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		if (isSecure()) {
			string.append("https://");
		} else {
			string.append("http://");
		}
		string.append(this.host);

		if ((!isSecure() && getPort() != 80) || (isSecure() && getPort() != 443)) {
			string.append(":" + this.port);
		}

		if (!this.path.startsWith("/")) {
			string.append("/");
		}
		string.append(this.path);

		boolean params = false;
		if (this.singleValueParameters != null && this.singleValueParameters.size() > 0) {
			for (String name : this.singleValueParameters.keySet()) {
				if (this.singleValueParameters.get(name) == null) {
					// Skip null values
					continue;
				}
				if (params) {
					string.append("&");
				} else {
					string.append("?");
					params = true;
				}
				string.append(name);
				string.append("=");
				string.append(this.singleValueParameters.get(name));
			}
		}

		return string.toString();
	}

	/**
	 * Returns a URL object. If the source URL is malformed (
	 * {@link MalformedURLException}) will wrap in an
	 * {@link IllegalArgumentException}.
	 * 
	 * @return A URL.
	 * @throws IllegalArgumentException
	 *             If the URL could not be constructed.
	 */
	public URL toURL() {
		try {
			return new URL(toString());
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Sets/replaces a single-value parameter. A null value will not be included
	 * in the URL.
	 * 
	 * @param name
	 *            The parameter name.
	 * @param value
	 *            The parameter value.
	 * @return The current instance, for chaining.
	 */
	public HttpURLBuilder setParam(String name, String value) {
		if (this.singleValueParameters == null) {
			this.singleValueParameters = new HashMap<>();
		}
		this.singleValueParameters.put(name, value);
		return this;
	}

	/**
	 * Returns the port. If the port is not set, will return default port based
	 * on {@link #isSecure()}.
	 * 
	 * @return The port number.
	 */
	public int getPort() {
		if (this.port == 0) {
			if (isSecure()) {
				return 443;
			}
			return 80;
		}
		return this.port;
	}

}
