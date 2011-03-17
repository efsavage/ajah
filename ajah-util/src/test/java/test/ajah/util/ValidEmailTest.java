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

import com.ajah.util.Valid;

/**
 * Tests email validator
 * 
 * @see Valid#email(String)
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class ValidEmailTest {

	/**
	 * Test basic email.
	 */
	@Test
	public void simpleEmail() {
		Assert.assertTrue(Valid.email("user@domain.com"));
	}

	/**
	 * Test valid but non-standard email.
	 */
	@Test
	public void strageButTrue() {
		Assert.assertTrue(Valid.email("++%_@a.bc"));
	}

	/**
	 * Test subdomain email.
	 */
	@Test
	public void subdomain() {
		Assert.assertTrue(Valid.email("user@sub.domain.com"));
	}

	/**
	 * Short TLD.
	 */
	@Test
	public void shortTLD() {
		Assert.assertFalse(Valid.email("user@domain.c"));
	}
	/**
	 * Long TLD.
	 */
	@Test
	public void longTLD() {
		Assert.assertFalse(Valid.email("user@domain.combo"));
	}

	/**
	 * Alphanumeric TLD.
	 */
	@Test
	public void alphanumericTLD() {
		Assert.assertFalse(Valid.email("user@domain.c3"));
	}

	/**
	 * Numeric TLD.
	 */
	@Test
	public void numericTLD() {
		Assert.assertFalse(Valid.email("user@domain.334"));
	}

	/**
	 * Bad domain.
	 */
	@Test
	public void badDomain() {
		Assert.assertFalse(Valid.email("user@domain"));
	}

	/**
	 * No domain.
	 */
	@Test
	public void noDomain() {
		Assert.assertFalse(Valid.email("user@"));
	}

	/**
	 * No user.
	 */
	@Test
	public void noUser() {
		Assert.assertFalse(Valid.email("@domain.com"));
	}

	/**
	 * No user or domain.
	 */
	@Test
	public void noUserOrDomain() {
		Assert.assertFalse(Valid.email("@"));
	}

	/**
	 * Null
	 */
	@Test
	public void nullEmail() {
		Assert.assertFalse(Valid.email(null));
	}

	/**
	 * Empty
	 */
	@Test
	public void emptyEmail() {
		Assert.assertFalse(Valid.email(""));
	}

}
