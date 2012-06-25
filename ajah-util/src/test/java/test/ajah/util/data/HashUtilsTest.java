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
package test.ajah.util.data;

import junit.framework.Assert;

import org.junit.Test;

import com.ajah.util.data.HashUtils;

/**
 * Tests {@link HashUtils}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class HashUtilsTest {

	/**
	 * Tests {@link HashUtils#sha256Hex(String)}.
	 */
	@Test
	public void testSha256() {
		Assert.assertEquals("84d89877f0d4041efb6bf91a16f0248f2fd573e6af05c19f96bedb9f882f7882", HashUtils.sha256Hex("0123456789"));
	}
}
