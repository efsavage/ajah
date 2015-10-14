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

import com.ajah.util.ToStringable;

import lombok.Getter;

public class SimplePost {

	private CloseableHttpClient client;

	@Getter
	private String host;

	@Getter
	private String path;

	@Getter
	private ArrayList<NameValuePair> params;

	public SimplePost(CloseableHttpClient client) {
		this.client = client;
	}

	public SimplePost host(String _host) {
		this.host = _host;
		return this;
	}

	public SimplePost path(String _path) {
		this.path = _path;
		return this;
	}

	public void addParam(String name, String value) {
		if (this.params == null) {
			this.params = new ArrayList<>();
		}
		this.params.add(new BasicNameValuePair(name, value));
	}

	public String getResponseAsString() throws IOException {
		HttpPost post = new HttpPost(this.host + this.path);
		if (this.params != null) {
			post.setEntity(new UrlEncodedFormEntity(this.params));
		}
		try (final CloseableHttpResponse response = this.client.execute(post)) {
			final String string = EntityUtils.toString(response.getEntity());
			EntityUtils.consumeQuietly(response.getEntity());
			return string;
		}
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

}
