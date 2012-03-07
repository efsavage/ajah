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
package test.ajah.util;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.ArrayUtils;
import com.ajah.util.Validate;

/**
 * Tests email validator
 * 
 * @see Validate#isEmail(String)
 * @author Eric F. Savage <code@efsavage.com>
 */
public class ArrayUtilsTest {

	/**
	 * Test bad get.
	 */
	@Test
	public void badGet() {
		final String[] array = new String[] { "foo", "bar" };
		Assert.assertNull(ArrayUtils.get(array, 10));
	}

	/**
	 * Test another bad get.
	 */
	@Test
	public void badGet2() {
		final String[] array = new String[] { "foo", "bar" };
		Assert.assertNull(ArrayUtils.get(array, -1));
	}

	/**
	 * Test good get.
	 */
	@Test
	public void goodGet() {
		final String[] array = new String[] { "foo", "bar" };
		Assert.assertTrue(ArrayUtils.get(array, 1).equals("bar"));
	}

	/**
	 * Test null get.
	 */
	@Test
	public void nullGet() {
		Assert.assertNull(ArrayUtils.get(null, 10));
	}

	/**
	 * Test another null get.
	 */
	@Test
	public void nullGet2() {
		final String[] array = new String[] { "foo", null };
		Assert.assertNull(ArrayUtils.get(array, 1));
	}

	/**
	 * Test another bad get.
	 */
	@Test
	public void nullGet3() {
		final String[] array = new String[] {};
		Assert.assertNull(ArrayUtils.get(array, 1));
	}

}
