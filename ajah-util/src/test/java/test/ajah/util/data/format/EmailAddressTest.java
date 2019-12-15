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

package test.ajah.util.data.format;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.data.format.EmailAddress;

/**
 * Tests {@link EmailAddress}.
 *
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 * href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class EmailAddressTest {

	/**
	 * Test person@badurl
	 */
	@Test(expected = IllegalArgumentException.class)
	public void badEmail() {
		final String emailString = "person@badurl";
		EmailAddress emailAddress;
		emailAddress = new EmailAddress(emailString);
		Assert.assertNull(emailAddress);
	}

	/**
	 * Test person@yahoo.com
	 */
	@Test
	public void goodEmail() {
		final String emailString = "person@yahoo.com";
		final EmailAddress emailAddress = new EmailAddress(emailString);
		Assert.assertNotNull(emailAddress);
		Assert.assertEquals(emailString, emailAddress.toString());
	}

	/**
	 * Test null email
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullEmail() {
		final String emailString = null;
		EmailAddress emailAddress = new EmailAddress(null);
		Assert.assertNull(emailAddress);
	}
}
