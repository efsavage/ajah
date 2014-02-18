/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.ajah.util.AjahUtils;

/**
 * Utilities for dealing with URLs and URIs.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class URLUtils {

	/**
	 * Returns the hostname portion of a url.
	 * 
	 * @param url
	 *            The URL to parse, required.
	 * @return The hostname of the url, from {@link URI#getHost()}.
	 * @throws URISyntaxException
	 *             If the URI is malformed.
	 */
	public static String getHost(final String url) throws URISyntaxException {
		AjahUtils.requireParam(url, "url");
		final URI uri = new URI(url);
		final String domain = uri.getHost();
		return domain;
	}

	/**
	 * Since URIs are often hard-coded, needing to catch a syntax exception is
	 * mostly unecessary. Do not use this method on dynamically-constructed
	 * URIs.
	 * 
	 * @param uri
	 *            The URI to construct.
	 * @return The URI.
	 * @throws IllegalArgumentException
	 *             If {@link URISyntaxException} is encountered.
	 */
	public static URI getURI(final String uri) {
		try {
			return new URI(uri);
		} catch (final URISyntaxException e) {
			throw new IllegalArgumentException("Invalid Syntax: " + uri);
		}
	}

	/**
	 * Since URLs are often hard-coded, needing to catch a syntax exception is
	 * mostly unecessary. Do not use this method on dynamically-constructed
	 * URLs.
	 * 
	 * @param url
	 *            The URL to construct.
	 * @return THe URL.
	 * @throws IllegalArgumentException
	 *             If a {@link MalformedURLException} occured.
	 */
	public static URL getURL(final String url) {
		try {
			return new URL(url);
		} catch (final MalformedURLException e) {
			throw new IllegalArgumentException("Malformed URL: " + url);
		}
	}

}
