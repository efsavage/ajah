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
package test.ajah.util;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.net.HttpClient;

/**
 * Tests {@link HttpClient}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class HttpClientTest {

	/**
	 * 
	 * Tests {@link HttpClient#getBytes(String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testBytes() throws IOException {
		Assert.assertTrue(HttpClient.getBytes("http://efsavage.com/blog/wp-content/uploads/2012/01/barcode-150x150.png").length > 0);
	}

	/**
	 * Tests {@link HttpClient#getString(String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testString() throws IOException {
		Assert.assertTrue(HttpClient.getString("http://efsavage.com").length() > 0);
	}

}
