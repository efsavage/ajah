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

import org.junit.Test;

import com.ajah.cache.HashCache;

/**
 * Tests {@link HashCache}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class HashCacheTest {
	
	/**
	 * Test operations in HashCacheEntry
	 */
	@Test
	public void testHashCache() {
		HashCache<String, String> hashCache = new HashCache<String, String>();
		hashCache.store("myKey", "aString");
		Assert.assertNotNull(hashCache);
		Assert.assertNotNull(hashCache.get("myKey", 0).toString());
		Assert.assertEquals("aString", hashCache.get("myKey", 1000000).toString());
		Assert.assertNull(hashCache.get("myKey", -10000));
	}
}
