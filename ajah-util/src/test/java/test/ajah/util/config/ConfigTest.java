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
package test.ajah.util.config;

import junit.framework.Assert;
import lombok.experimental.Accessors;

import org.junit.Test;

import com.ajah.util.config.Config;
import com.ajah.util.config.PropertyKey;
import com.ajah.util.data.format.EmailAddress;

/**
 * Tests {@link Config}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class ConfigTest {
	/**
	 * Inner class implementation for PropertyKey
	 */
	@Accessors
	class PropertyKeyImpl implements PropertyKey {
		private final String key;
		private final String value;

		public PropertyKeyImpl(final String key, final String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String getDefaultValue() {
			return this.value;
		}

		@Override
		public PropertyKey getFallback() {
			return new PropertyKeyImpl("default", "default");
		}

		@Override
		public String getName() {
			return this.key;
		}
	}

	Config config = Config.i;

	/**
	 * Test get operations in Config
	 */
	@Test
	public void testConfig() {

		this.config.set("key", "value");
		Assert.assertEquals("value", this.config.get("key"));
	}

	/**
	 * Test getEmailAddress operations in Config class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConfigBadEmail() {
		// Test for a bad email syntax
		this.config.set("badEmailKey", "bademail");
		final PropertyKeyImpl pki = new PropertyKeyImpl("badEmailKey", "bademail");
		EmailAddress emailAddress = null;
		emailAddress = this.config.getEmailAddress(pki);
		Assert.assertNull(emailAddress);
	}

	/**
	 * Test getEmailAddress operations in Config class
	 */
	@Test
	public void testConfigGoodEmail() {
		// Test for a good email syntax
		this.config.set("goodEmailKey", "goodemail@email.com");
		final PropertyKeyImpl pki = new PropertyKeyImpl("goodEmailKey", "goodemail@email.com");
		final EmailAddress emailAddress = this.config.getEmailAddress(pki);
		Assert.assertEquals("goodemail@email.com", emailAddress.toString());
	}
}
