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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.java.Log;

import com.ajah.crypto.SHA;
import com.ajah.http.Http;
import com.ajah.http.err.NotFoundException;
import com.ajah.http.err.UnexpectedResponseCode;
import com.ajah.util.config.Config;
import com.ajah.util.io.file.FileHashUtils;
import com.ajah.util.io.file.FileUtils;

/**
 * Disk-based implementation of HttpCache.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @author <a href="http://whirlycott.com">Philip Jacob</a>, <a
 *         href="mailto:phil@whirlycott.com">phil@whirlycott.com</a>.
 */
@Log
public class DiskCache implements HttpCache {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(final URI uri) throws IOException, UnexpectedResponseCode, NotFoundException {
		return new String(getBytes(uri, Long.MAX_VALUE));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getBytes(final String uri) throws IOException, NotFoundException, UnexpectedResponseCode, URISyntaxException {
		return getBytes(new URI(uri), Long.MAX_VALUE);
	}

	/**
	 * Returns the content from cache if possible, and if not, will fetch and
	 * cache it.
	 * 
	 * @param uri
	 *            The URI to fetch.
	 * @param maxAge
	 *            The maximum age of the cached copy to return. Use -1 to force
	 *            a fresh fetch.
	 * @return The content from cache or as fetched.
	 * @throws IOException
	 * @throws NotFoundException
	 * @throws UnexpectedResponseCode
	 */
	public byte[] getBytes(final URI uri, long maxAge) throws IOException, NotFoundException, UnexpectedResponseCode {
		// TODO Add max-age
		// IDEA Store expires header, check last-modified
		final String path = FileHashUtils.getHashedFileName(SHA.sha1Hex(uri.toString()), 3, 2);
		final File cacheDir = new File(Config.i.get("ajah.http.cache.dir", "/tmp/ajah-http-cache"));
		final File f = new File(cacheDir, path);

		byte[] data = null;
		if (f.exists()) {
			if (maxAge > 0) {
				if (maxAge == Long.MAX_VALUE || f.lastModified() + maxAge < System.currentTimeMillis()) {
					return FileUtils.readFileAsBytes(f);
				}
				log.fine("Cache expired; getting " + uri);
			}
		} else {
			log.fine("Cache miss; getting " + uri);
		}

		data = Http.getBytes(uri);
		FileUtils.write(f, data);
		return data;
	}

	/**
	 * @param url
	 * @param millis
	 * @return
	 * @throws IOException 
	 * @throws UnexpectedResponseCode 
	 * @throws NotFoundException 
	 */
	public String get(URI uri, long millis) throws NotFoundException, UnexpectedResponseCode, IOException {
		return new String(getBytes(uri, millis));
	}

	/**
	 * Calls {@link #getBytes(URI, long)} with {@link Long#MAX_VALUE} for a
	 * maxAge.
	 * 
	 * @see com.ajah.http.cache.HttpCache#getBytes(java.net.URI)
	 */
	@Override
	public byte[] getBytes(URI uri) throws IOException, NotFoundException, UnexpectedResponseCode {
		return getBytes(uri, Long.MAX_VALUE);
	}

}
