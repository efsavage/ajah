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
package test.ajah.geo.us;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.geo.us.IllegalZipCodeFormatException;
import com.ajah.geo.us.ZipCode;

/**
 * Unit tests for ZipCode class.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class ZipCodeTest {

	/**
	 * Test format that should work.
	 */
	@Test
	public void goodFormat1() {
		final ZipCode test = new ZipCode();
		test.setZip("00000");
		test.setZip4("0000");
		Assert.assertEquals("00000-0000", test.toString());
	}

	/**
	 * Test another format that should work.
	 */
	@Test
	public void goodFormat2() {
		final ZipCode test = new ZipCode();
		test.setZip("11111");
		test.setZip4("1111");
		Assert.assertEquals("11111-1111", test.toString());
	}

	/**
	 * Test default
	 */
	@Test
	public void defaultFormat() {
		final ZipCode test = new ZipCode();
		Assert.assertNull(test.toString());
	}

	/**
	 * Test null
	 */
	@Test
	public void nullFormat() {
		final ZipCode test = new ZipCode();
		test.setZip(null);
		Assert.assertNull(test.toString());
	}

	/**
	 * Test null with zip4
	 */
	@Test
	public void nullFormatZip4() {
		final ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("1111");
		Assert.assertNull(test.toString());
	}

	/**
	 * Test too short
	 */
	@Test(expected = IllegalZipCodeFormatException.class)
	public void tooShort() {
		final ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("3");
		Assert.assertNull(test.toString());
	}

	/**
	 * Test too long
	 */
	@Test(expected = IllegalZipCodeFormatException.class)
	public void tooLong() {
		final ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("1234567");
		Assert.assertNull(test.toString());
	}

	/**
	 * Test alpha chars
	 */
	@Test(expected = IllegalZipCodeFormatException.class)
	public void alphaChars() {
		final ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("abs");
		Assert.assertNull(test.toString());
	}

	/**
	 * Test alphanumeric chars
	 */
	@Test(expected = IllegalZipCodeFormatException.class)
	public void alphaNumericChars() {
		final ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("12345abs");
		Assert.assertNull(test.toString());
	}

}