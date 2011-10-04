/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Simple HTTP client that depends only on JDK libraries. For production-level
 * code, use a more robust solution.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class HttpClient {

	/**
	 * Get an {@link InputStream} of the target URL.
	 * 
	 * @param cssFile
	 * @return The results of the URL, if possible, or null.
	 * @throws IOException
	 */
	public static InputStream stream(String cssFile) throws IOException {
		try {
			URL ur = new URL(cssFile);
			URLConnection conn;
			conn = ur.openConnection();
			return conn.getInputStream();
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
