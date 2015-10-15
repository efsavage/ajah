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

import com.ajah.util.Validate;

/**
 * Tests email validator
 * 
 * @see Validate#isEmail(String)
 * @author Eric F. Savage <code@efsavage.com>
 */
public class ValidEmailTest {

	/**
	 * Alphanumeric TLD.
	 */
	@Test
	public void alphanumericTLD() {
		Assert.assertFalse(Validate.isEmail("user@domain.c3"));
	}

	/**
	 * Bad domain.
	 */
	@Test
	public void badDomain() {
		Assert.assertFalse(Validate.isEmail("user@domain"));
	}

	/**
	 * Empty
	 */
	@Test
	public void emptyEmail() {
		Assert.assertFalse(Validate.isEmail(""));
	}

	/**
	 * Long TLD.
	 */
	@Test
	public void longTLD() {
		Assert.assertFalse(Validate.isEmail("user@domain.combo"));
	}

	/**
	 * No domain.
	 */
	@Test
	public void noDomain() {
		Assert.assertFalse(Validate.isEmail("user@"));
	}

	/**
	 * No user.
	 */
	@Test
	public void noUser() {
		Assert.assertFalse(Validate.isEmail("@domain.com"));
	}

	/**
	 * No user or domain.
	 */
	@Test
	public void noUserOrDomain() {
		Assert.assertFalse(Validate.isEmail("@"));
	}

	/**
	 * Null
	 */
	@Test
	public void nullEmail() {
		Assert.assertFalse(Validate.isEmail(null));
	}

	/**
	 * Numeric TLD.
	 */
	@Test
	public void numericTLD() {
		Assert.assertFalse(Validate.isEmail("user@domain.334"));
	}

	/**
	 * Short TLD.
	 */
	@Test
	public void shortTLD() {
		Assert.assertFalse(Validate.isEmail("user@domain.c"));
	}

	/**
	 * Test basic email.
	 */
	@Test
	public void simpleEmail() {
		Assert.assertTrue(Validate.isEmail("user@domain.com"));
	}

	/**
	 * Test valid but non-standard email.
	 */
	@Test
	public void strageButTrue() {
		Assert.assertTrue(Validate.isEmail("++%_@a.bc"));
	}

	/**
	 * Test subdomain email.
	 */
	@Test
	public void subdomain() {
		Assert.assertTrue(Validate.isEmail("user@sub.domain.com"));
	}

	/**
	 * Test subdomain email with a dash in the domain.
	 */
	@Test
	public void subdomainWithDash() {
		Assert.assertTrue(Validate.isEmail("user@sub.domain-word.com"));
	}

}
