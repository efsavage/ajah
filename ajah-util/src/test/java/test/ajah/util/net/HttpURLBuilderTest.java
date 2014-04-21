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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ajah.util.net.HttpURLBuilder;

/**
 * Tests {@link HttpURLBuilder}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class HttpURLBuilderTest {

	HttpURLBuilder httpURLBuilder;
	Map<String, String> map;

	/**
	 * Initialization required for running tests
	 */
	@Before
	public void setUp() {
		this.httpURLBuilder = new HttpURLBuilder();
		this.httpURLBuilder.setHost("localhost");
		this.httpURLBuilder.setParam("name", "value");
		this.httpURLBuilder.setPath("/xampp");
		this.httpURLBuilder.setPort(80);
		this.httpURLBuilder.setSecure(false);
		this.map = new HashMap<>();
		this.map.put("key", "value");
	}

	/**
	 * Test various operation of HttpURLBuilder
	 */
	@Test
	public void testHttpURLBuilderSecure() {
		this.httpURLBuilder.setSecure(true);
		this.httpURLBuilder.setPort(0);
		Assert.assertEquals(443, this.httpURLBuilder.getPort());
		Assert.assertNotNull(this.httpURLBuilder.toString());
		Assert.assertNotNull(this.httpURLBuilder.toURL());
		Assert.assertTrue(this.httpURLBuilder.canEqual(this.httpURLBuilder));
		Assert.assertTrue(this.httpURLBuilder.equals(this.httpURLBuilder));
		Assert.assertNotNull(Integer.valueOf(this.httpURLBuilder.hashCode()));
	}

	/**
	 * Test various operation of HttpURLBuilder
	 */
	@Test
	public void testHttpURLBuilderUnsecure() {
		this.httpURLBuilder.setSingleValueParameters(this.map);
		Assert.assertEquals("localhost", this.httpURLBuilder.getHost());
		Assert.assertEquals("/xampp", this.httpURLBuilder.getPath());
		Assert.assertEquals(80, this.httpURLBuilder.getPort());
		Assert.assertEquals(this.map, this.httpURLBuilder.getSingleValueParameters());
		Assert.assertFalse(this.httpURLBuilder.isSecure());
		Assert.assertNotNull(this.httpURLBuilder.toString());
		Assert.assertNotNull(this.httpURLBuilder.toURL());
	}
}
