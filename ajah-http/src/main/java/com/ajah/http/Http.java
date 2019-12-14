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
package com.ajah.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import com.ajah.http.err.*;
import lombok.extern.java.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Offers a simple interface to HTTP client operations with sensible default
 * behaviors.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class Http {

	private static String get(final String url) throws IOException, HttpException {
		URI uri;
		try {
			uri = URI.create(url);
		} catch (final IllegalArgumentException e) {
			log.warning("Illegal URI [" + url + "] - " + e.getMessage());
			throw new BadRequestException(e);
		}
		return get(uri);
	}

	/**
	 * Fetch a URI and return it's response as a String.
	 * 
	 * @param uri
	 *            The URI to fetch.
	 * @return The response body as a String.
	 * @throws IOException
	 *             If the response could not be completed.
	 * @throws UnexpectedResponseCode
	 *             If an unexpected/illegal response status is issued.
	 * @throws NotFoundException
	 *             If the resource could not be found at the URI (404).
	 * @throws InternalServerError
	 * @throws ParseException
	 */
	public static String get(final URI uri) throws IOException, UnexpectedResponseCode, NotFoundException, ParseException, InternalServerError {
		long start = System.currentTimeMillis();
		String response = EntityUtils.toString(internalGet(uri));
		log.finest((System.currentTimeMillis() - start) + "ms to fetch " + uri.toASCIIString());
		return response;
	}

	/**
	 * Return the body of the response as a byte array (such as an image).
	 * 
	 * @see #getBytes(URI)
	 * @param uri
	 *            The URI to fetch.
	 * @return The response body as a String.
	 * @throws IOException
	 *             If the response could not be completed.
	 * @throws UnexpectedResponseCode
	 *             If an unexpected/illegal response status is issued.
	 * @throws NotFoundException
	 *             If the resource could not be found at the URI (404).
	 * @throws URISyntaxException
	 * @throws InternalServerError
	 */
	public static byte[] getBytes(final String uri) throws IOException, NotFoundException, UnexpectedResponseCode, URISyntaxException, InternalServerError {
		return EntityUtils.toByteArray(internalGet(new URI(uri)));
	}

	/**
	 * Return the body of the response as a byte array (such as an image).
	 * 
	 * @param uri
	 *            The URI to fetch.
	 * @return The response body as a String.
	 * @throws IOException
	 *             If the response could not be completed.
	 * @throws UnexpectedResponseCode
	 *             If an unexpected/illegal response status is issued.
	 * @throws NotFoundException
	 *             If the resource could not be found at the URI (404).
	 * @throws InternalServerError
	 */
	public static byte[] getBytes(final URI uri) throws IOException, HttpException {
		return EntityUtils.toByteArray(internalGet(uri));
	}

	/**
	 * Calls {@link #get(URI)} but returns null instead of throwing exceptions.
	 * 
	 * @param uri
	 *            The URL to fetch.
	 * @return The response as a String, or null.
	 */
	public static String getSafe(final String uri) {
		try {
			return get(uri);
		} catch (final IOException | HttpException e) {
			log.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
	}

	private static HttpEntity internalGet(final URI uri) throws IOException, NotFoundException, UnexpectedResponseCode, InternalServerError {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		final HttpGet httpget = new HttpGet(uri);
		final HttpResponse response = client.execute(httpget);
		if (response.getStatusLine().getStatusCode() == 200) {
			return response.getEntity();
		} else if (response.getStatusLine().getStatusCode() == 404) {
			throw new NotFoundException(response.getStatusLine().getStatusCode() + " - " + response.getStatusLine().getReasonPhrase());
		} else if (response.getStatusLine().getStatusCode() == 500) {
			throw new InternalServerError(response.getStatusLine().getStatusCode() + " - " + response.getStatusLine().getReasonPhrase());
		} else {
			throw new UnexpectedResponseCode(response.getStatusLine().getStatusCode() + " - " + response.getStatusLine().getReasonPhrase());
		}
	}

}
