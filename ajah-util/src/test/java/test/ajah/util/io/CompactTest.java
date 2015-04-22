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
package test.ajah.util.io;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.io.Compact;

/**
 * Tests {@link Compact}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class CompactTest {
	Compact compactH = Compact.HIGH;
	Compact compactL = Compact.LOW;

	/**
	 * Test Equals operations in Compact class
	 */
	public void testEquals() {
		Assert.assertTrue(this.compactH.equals(this.compactH));
	}

	/**
	 * Test Get operations in Compact class
	 */
	public void testGetOperation() {
		Assert.assertEquals(Compact.HIGH, Compact.get(Compact.HIGH.toString(), Compact.NONE));
		Assert.assertEquals(Compact.NONE, Compact.get("INVALID", Compact.NONE));
	}

	/**
	 * Test Greater than or Equal operations in Compact class
	 */
	public void testGreaterOrEqual() {
		Assert.assertTrue(this.compactH.ge(this.compactL));
		Assert.assertTrue(!this.compactL.ge(this.compactH));
	}

	/**
	 * Test Less than or Equal operation in Compact class
	 */
	@Test
	public void testLesserOrEqual() {
		Assert.assertTrue(this.compactL.le(this.compactH));
		Assert.assertTrue(!this.compactH.le(this.compactL));
	}

	/**
	 * Test operations in Compact class
	 */
	public void testLessThan() {
		Assert.assertTrue(this.compactL.lt(this.compactH));
	}

}
