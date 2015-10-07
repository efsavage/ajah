/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;
import com.ajah.util.ToStringable;

/**
 * Wrapper for more easily creating and submitting a POST and getting a simple
 * response back.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class SimplePost {

	private CloseableHttpClient client;
	private String host = null;
	private ArrayList<NameValuePair> params = null;
	private String path = null;

	/**
	 * Creates an http client with defaults.
	 */
	public SimplePost() {
		this.client = HttpClientBuilder.create().build();
	}

	/**
	 * Uses the supplied client for any requests.
	 * 
	 * @param client
	 *            HTTP client, required.
	 */
	public SimplePost(final CloseableHttpClient client) {
		AjahUtils.requireParam(client, "client");
		this.client = client;
	}

	/**
	 * Adds a parameter to be posted.
	 * 
	 * @param name
	 *            The name of the parameter, required.
	 * @param value
	 *            The value of the parameter, not required.
	 */
	public void addParam(String name, String value) {
		AjahUtils.requireParam(name, "name");
		if (this.params == null) {
			this.params = new ArrayList<>();
		}
		this.params.add(new BasicNameValuePair(name, value.toString()));
	}

	/**
	 * Adds a parameter to be posted.
	 * 
	 * @param name
	 *            The name of the parameter, required.
	 * @param value
	 *            The value of the parameter, not required.
	 */
	public void addParam(String name, ToStringable value) {
		if (value != null) {
			addParam(name, value.toString());
		} else {
			addParam(name, (String) null);
		}
	}

	/**
	 * POSTs the query and returns the server response as a string.
	 * 
	 * @return The server response as a string.
	 * @throws IOException
	 *             If the query could not be executed.
	 */
	public String getResponseAsString() throws IOException {
		AjahUtils.requireParam(host, "host");

		final HttpPost post = new HttpPost(this.host + (StringUtils.isBlank(this.path) ? "" : this.path));

		if (params != null) {
			post.setEntity(new UrlEncodedFormEntity(params));
		}

		try (final CloseableHttpResponse response = this.client.execute(post)) {
			final String string = EntityUtils.toString(response.getEntity());
			EntityUtils.consumeQuietly(response.getEntity());
			return string;
		}
	}

	/**
	 * Sets the host.
	 * 
	 * @param host
	 *            The host, required.
	 * @return This instance.
	 */
	public SimplePost host(String host) {
		AjahUtils.requireParam(host, "host");
		this.host = host;
		return this;
	}

	/**
	 * Sets the path.
	 * 
	 * @param path
	 *            The URL path, not required.
	 * @return This instance.
	 */
	public SimplePost path(String path) {
		this.path = path;
		return this;
	}

}
