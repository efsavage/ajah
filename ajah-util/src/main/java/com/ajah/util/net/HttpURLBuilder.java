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

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import com.ajah.lang.ListMap;
import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;

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
@Log
public class HttpURLBuilder {

	private String host;
	private String path;
	private int port = 0;
	private boolean secure;
	private ListMap<String, String> parameters;

	/**
	 * Constructs an empty URLBuilder.
	 */
	public HttpURLBuilder() {
		// Empty
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
	public HttpURLBuilder setParam(final String name, final String value) {
		AjahUtils.requireParam(name, "name");
		if (StringUtils.isBlank(value)) {
			return this;
		}
		if (this.parameters == null) {
			this.parameters = new ListMap<String, String>();
		}
		this.parameters.putValue(name, value);
		return this;
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
		final StringBuilder string = new StringBuilder();
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
		if (this.parameters != null && this.parameters.size() > 0) {
			for (final String name : this.parameters.keySet()) {
				for (final String value : this.parameters.get(name)) {
					if (this.parameters.get(name) == null) {
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
					try {
						string.append(URLEncoder.encode(value, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						log.log(Level.SEVERE, e.getMessage(), e);
					}
				}
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
		} catch (final MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Parses a URL into the component parts.
	 * 
	 * @param url
	 *            The URL to parse.
	 */
	public void setUrl(String url) {
		if (StringUtils.isBlank(url)) {
			return;
		}
		int startPos = 0;
		if (url.startsWith("http://")) {
			startPos = 7;
			setSecure(false);
		} else if (url.startsWith("https://")) {
			startPos = 8;
			setSecure(true);
		}
		int slashPos = url.substring(startPos).indexOf('/');
		int queryPos = url.indexOf('?');
		if (slashPos > 0) {
			host = url.substring(startPos, startPos + slashPos);
			if (queryPos > 0) {
				path = url.substring(startPos + slashPos, queryPos);
			} else {
				path = url.substring(startPos + slashPos);
			}
		}
		if (queryPos > 0) {
			String[] params = url.substring(queryPos + 1).split("&");
			for (String param : params) {
				String[] paramSplit = param.split("=");
				setParam(paramSplit[0], paramSplit[1]);
			}
		}
	}

	public void setParam(String name, int value) {
		setParam(name, String.valueOf(value));
	}

	public void setParam(String name, BigDecimal value) {
		setParam(name, value.toString());
	}
}
