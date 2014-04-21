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
package test.ajah.util.data;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.data.LargeDataSizeUnit;

/**
 * Tests {@link LargeDataSizeUnit}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class LargeDataSizeUnitTest {

	/**
	 * Tests calling for long value of a large unit.
	 */
	@Test
	public void testYobiBytes() {
		Assert.assertEquals(BigInteger.valueOf(2).pow(80), LargeDataSizeUnit.YOBIBYTE.getBytes());
	}

	/**
	 * Tests calling for long value of a large unit.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testYobiBytesLong() {
		Assert.assertEquals(2 ^ 80, LargeDataSizeUnit.YOBIBYTE.getBytesLong());
	}

}
