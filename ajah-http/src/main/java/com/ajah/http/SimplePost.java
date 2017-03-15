/*
 *  Copyright 2015-2016 Eric F. Savage, code@efsavage.com
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
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.ajah.util.AjahUtils;
import com.ajah.util.ToStringable;

import lombok.Getter;

public class SimplePost {

	private CloseableHttpClient client;

	@Getter
	private int statusCode;

	@Getter
	private String statusReason;

	@Getter
	private String host;

	@Getter
	private String path;

	@Getter
	private ArrayList<NameValuePair> params;

	@Getter
	private ArrayList<NameValuePair> headers;

	private String responseAsString = null;

	public SimplePost(CloseableHttpClient client) {
		this.client = client;
	}

	public void addParam(String name, String value) {
		if (this.params == null) {
			this.params = new ArrayList<>();
		}
		this.params.add(new BasicNameValuePair(name, value));
	}

	public String getResponseAsString() throws IOException {
		if (responseAsString != null) {
			return responseAsString;
		}
		HttpPost post = new HttpPost(this.host + this.path);
		if (this.params != null) {
			post.setEntity(new UrlEncodedFormEntity(this.params));
		}
		if (this.headers != null) {
			for (NameValuePair header : headers) {
				post.setHeader(header.getName(), header.getValue());
			}
		}
		try (final CloseableHttpResponse response = this.client.execute(post)) {
			statusCode = response.getStatusLine().getStatusCode();
			statusReason = response.getStatusLine().getReasonPhrase();
			final String string = EntityUtils.toString(response.getEntity());
			EntityUtils.consumeQuietly(response.getEntity());
			responseAsString = string;
		}
		return responseAsString;
	}

	/**
	 * Sets the host.
	 * 
	 * @param _host
	 *            The host, required.
	 * @return This instance.
	 */
	public SimplePost host(String _host) {
		AjahUtils.requireParam(_host, "host");
		this.host = _host;
		return this;
	}

	/**
	 * Sets the path.
	 * 
	 * @param _path
	 *            The URL path, not required.
	 * @return This instance.
	 */
	public SimplePost path(String _path) {
		this.path = _path;
		return this;
	}

	public void addParam(String name, BigDecimal value) {
		if (value != null) {
			addParam(name, value.toString());
		} else {
			addParam(name, (String) null);
		}
	}

	public void addParam(String name, int value) {
		addParam(name, String.valueOf(value));
	}

	public void addParam(String name, ToStringable value) {
		if (value != null) {
			addParam(name, value.toString());
		} else {
			addParam(name, (String) null);
		}
	}

	public void addHeader(String name, String value) {
		if (this.headers == null) {
			this.headers = new ArrayList<>();
		}
		this.headers.add(new BasicNameValuePair(name, value));
	}

}
