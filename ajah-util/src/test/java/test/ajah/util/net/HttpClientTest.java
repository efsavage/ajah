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
package test.ajah.util.net;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.ajah.util.data.XmlString;
import com.ajah.util.net.HttpClient;

/**
 * Tests {@link HttpClient}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class HttpClientTest {
	HttpClient client;
	byte[] bytes;
	String string;
	XmlString xmlString;
	String url = "http://efsavage.com/";

	/**
	 * Initialization required for running tests
	 */
	@Before
	public void setUp() {
		this.client = new HttpClient();
	}

	/**
	 * Null Check
	 * 
	 * @throws IOException
	 */
	@Test
	public void testHttpClient() throws IOException {
		Assert.assertNotNull(this.client);
	}

	/**
	 * Test the get Bytes operation
	 * 
	 * @throws IOException
	 */
	@Test
	public void testHttpClientGetBytes() throws IOException {
		this.bytes = HttpClient.getBytes(this.url);
		Assert.assertNotNull(this.bytes);
	}

	/**
	 * Test the get Stream operation
	 * 
	 * @throws IOException
	 */
	@Test
	public void testHttpClientGetStream() throws IOException {
		Assert.assertNotNull(HttpClient.stream(this.url));
	}

	/**
	 * Test the get String operation
	 * 
	 * @throws IOException
	 */
	@Test
	public void testHttpClientGetString() throws IOException {
		this.string = HttpClient.getString(this.url);
		Assert.assertNotNull(this.string);
	}

	/**
	 * Test the get XmlString operation
	 * 
	 * @throws IOException
	 */
	@Test
	public void testHttpClientGetXmlString() throws IOException {
		this.xmlString = HttpClient.getXml(this.url);
		Assert.assertNotNull(this.xmlString);
	}

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
