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

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.cache.CacheEntry;

/**
 * Tests {@link CacheEntry}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class CacheEntryTest {

	final long timestamp = new Date().getTime();
	final String str = "aString";
	final CacheEntry<String> cacheEntry = new CacheEntry<>(this.str, this.timestamp);

	/**
	 * Test getObject method in CacheEntry
	 */
	@Test
	public void testCache() {
		Assert.assertEquals(this.str, this.cacheEntry.getObject());
	}

	/**
	 * Test canEqual method in CacheEntry
	 */
	@Test
	public void testCacheCanEquals() {
//		Assert.assertTrue(this.cacheEntry.canEqual(this.cacheEntry));
	}

	/**
	 * Test equals method in CacheEntry
	 */
	@Test
	public void testCacheEquality() {
		Assert.assertTrue(!this.cacheEntry.equals(this.str));
	}

	/**
	 * Test getCreated method in CacheEntry
	 */
	@Test
	public void testCacheGetCreated() {
		Assert.assertEquals(this.timestamp, this.cacheEntry.getCreated());
	}

	/**
	 * Test hashCode method in CacheEntry
	 */
	@Test
	public void testCacheHashCode() {
		Assert.assertTrue(this.cacheEntry.hashCode() != 0);
	}

	/**
	 * Test for Null Check in CacheEntry
	 */
	@Test
	public void testCacheNullCheck() {
		Assert.assertNotNull(this.cacheEntry);
	}

	/**
	 * Test toString method in CacheEntry
	 */
	@Test
	public void testCacheToString() {
		Assert.assertNotNull(this.cacheEntry.toString());
	}
}
