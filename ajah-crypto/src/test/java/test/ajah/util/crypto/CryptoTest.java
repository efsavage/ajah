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
package test.ajah.util.crypto;

import junit.framework.Assert;

import org.junit.Test;

import com.ajah.crypto.HmacSha1Password;
import com.ajah.util.config.Config;

/**
 * Tests {@link HmacSha1Password}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class CryptoTest {

	/**
	 * Tests that a value is being hashed properly.
	 */
	@Test
	public void hmacSha1PasswordTest() {
		Config.i.set("crypto.key.hmacsha1", "1234567890");
		HmacSha1Password hmacSha1Password = new HmacSha1Password("foobar", false);
		Assert.assertEquals("-305dc59cb5a82c23fcef84a3c60ea0aca890f03e", hmacSha1Password.toString());
	}
}
