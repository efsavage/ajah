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
package test.ajah.cache;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.ajah.cache.HashCache;

/**
 * Tests {@link HashCache}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class HashCacheTest {
	HashCache<String, String> hashCache = new HashCache<String, String>();

	/**
	 * Store operation to initialize
	 */
	@Before
	public void setUp() {
		this.hashCache.store("myKey", "aString");
	}

	/**
	 * Test get operating when max age is expired
	 */
	@Test
	public void testGetExpiredMaxAge() {
		Assert.assertNull(this.hashCache.get("myKey", -10000));
	}

	/**
	 * Test get operations with expire period of max age
	 */
	@Test
	public void testGetValidMaxAge() {
		Assert.assertNotNull(this.hashCache);
		Assert.assertNotNull(this.hashCache.get("myKey", 0).toString());
		Assert.assertEquals("aString", this.hashCache.get("myKey", 1000000).toString());
	}
}
