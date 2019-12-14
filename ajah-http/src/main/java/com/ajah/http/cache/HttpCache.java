/*
 *  Copyright 2012-2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.http.cache;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.ajah.http.Http;
import com.ajah.http.err.HttpException;
import com.ajah.http.err.NotFoundException;
import com.ajah.http.err.UnexpectedResponseCode;

/**
 * Interface for a caching wrapper of {@link Http} fetches.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public interface HttpCache {

	/**
	 * Attempts to find a cached response of a URI. If not found, fetches the
	 * URI and caches the response.
	 * 
	 * @param uri
	 *            The URI to fetch
	 * @return The response of the URI, either from cache or from a fresh
	 *         request.
	 * @throws IOException
	 *             If the URI could not be fetched.
	 * @throws NotFoundException
	 *             If the URI is 404
	 * @throws UnexpectedResponseCode
	 *             If the URI returns a response code that {@link Http} cannot
	 *             not handle.
	 * 
	 * @see com.ajah.http.cache.HttpCache#getBytes(java.net.URI)
	 */
	byte[] getBytes(final URI uri) throws IOException, HttpException;

	/**
	 * Attempts to find a cached response of a URI. If not found, fetches the
	 * URI and caches the response.
	 * 
	 * @param uri
	 *            The URI to fetch
	 * @return The response of the URI, either from cache or from a fresh
	 *         request.
	 * @throws IOException
	 *             If the URI could not be fetched.
	 * @throws NotFoundException
	 * @throws UnexpectedResponseCode
	 * 
	 * @see com.ajah.http.cache.HttpCache#getBytes(java.net.URI)
	 */
	String get(final URI uri) throws IOException, HttpException;

	/**
	 * Attempts to find a cached response of a URI. If not found, fetches the
	 * URI and caches the response.
	 * 
	 * @param uri
	 *            The URI to fetch
	 * @return The response of the URI, either from cache or from a fresh
	 *         request.
	 * @throws IOException
	 *             If the URI could not be fetched.
	 * @throws NotFoundException
	 * @throws UnexpectedResponseCode
	 * @throws URISyntaxException
	 * 
	 * @see com.ajah.http.cache.HttpCache#getBytes(java.net.URI)
	 */
	byte[] getBytes(final String uri) throws IOException, HttpException, URISyntaxException;

}
