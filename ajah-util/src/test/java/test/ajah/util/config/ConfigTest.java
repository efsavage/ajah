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
	 * Test operations in Config
	 */
	@Test
	public void testConfig() {
		Config config = Config.i;
		config.set("key", "value");
		Assert.assertEquals("value", config.get("key"));
		//config.set("emailKey", "test@email.com");
		//Assert.assertEquals("test@email.com", config.get("emailKey"));
		//config.set("badEmailKey", "testemailcom");
		@Accessors
		class PropertyKeyImpl implements PropertyKey{
			private String key;
			private String value;

			public PropertyKeyImpl(String key, String value)
			{
				this.key = key;
				this.value = value;
			}
			@Override
			public String getName() {
				return key;
			}

			@Override
			public String getDefaultValue() {
				return value;
			}

			@Override
			public PropertyKey getFallback() {
				return new PropertyKeyImpl("default", "default");
			}
		}
		//Test for a bad email syntax
		config.set("badEmailKey", "bademail");
		PropertyKeyImpl pki = new PropertyKeyImpl("badEmailKey", "bademail");
		EmailAddress emailAddress = null;
		try {
			emailAddress = config.getEmailAddress(pki);
		} catch (Exception e) {
			//do nothing
		}
		Assert.assertNull( emailAddress);
		
		//Test for a good email syntax
		config.set("goodEmailKey", "goodemail@email.com");
		pki = new PropertyKeyImpl("goodEmailKey", "goodemail@email.com");
		emailAddress = config.getEmailAddress(pki);
		Assert.assertEquals("goodemail@email.com", emailAddress.toString());
	}
}
