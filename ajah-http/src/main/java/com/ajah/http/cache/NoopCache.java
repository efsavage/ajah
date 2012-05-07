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
package com.ajah.http.cache;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.ajah.http.Http;
import com.ajah.http.err.NotFoundException;
import com.ajah.http.err.UnexpectedResponseCode;

/**
 * Non-caching noop passthrough to {@link Http}. This exists so you can program
 * against the {@link HttpCache} interface and may be useful for the occasional
 * times when you don't need a cache.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @author <a href="http://whirlycott.com">Philip Jacob</a>, <a
 *         href="mailto:phil@whirlycott.com">phil@whirlycott.com</a>.
 */
public class NoopCache implements HttpCache {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(final URI uri) throws IOException, UnexpectedResponseCode, NotFoundException {
		return new String(getBytes(uri));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getBytes(final String uri) throws IOException, NotFoundException, UnexpectedResponseCode, URISyntaxException {
		return getBytes(new URI(uri));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getBytes(final URI uri) throws IOException, NotFoundException, UnexpectedResponseCode {
		return Http.getBytes(uri);
	}

}
