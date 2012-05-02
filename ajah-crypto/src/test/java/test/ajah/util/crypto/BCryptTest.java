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

import com.ajah.crypto.BCrypt;

/**
 * Tests for {@link BCrypt}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class BCryptTest {

	/**
	 * Hashes a value and then checks that hash.
	 */
	@Test
	public void hashpwTest() {
		String hashed = BCrypt.hashpw("foobar", BCrypt.gensalt());
		Assert.assertTrue(BCrypt.checkpw("foobar", hashed));
	}
}
