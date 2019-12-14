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
package test.ajah.util;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.Validate;
import com.ajah.util.compare.CompareUtils;

/**
 * Tests {@link CompareUtils}
 * 
 * @see Validate#isEmail(String)
 * @author Eric F. Savage <code@efsavage.com>
 */
public class CompareUtilsTest {

	private enum TestEnum {
		FOO, BAR, BYE
	}

	/**
	 * Test enum comparison
	 */
	@Test
	public void testEnum() {
		// TODO Investigate further, it appears that comparing enums is based on
		// the position of the enum
		Assert.assertEquals(-1, CompareUtils.safeCompare(TestEnum.FOO, TestEnum.BAR, false));
		Assert.assertEquals(0, CompareUtils.safeCompare(TestEnum.BAR, TestEnum.BAR, false));
		Assert.assertEquals(-2, CompareUtils.safeCompare(TestEnum.FOO, TestEnum.BYE, false));
		Assert.assertEquals(-1, CompareUtils.safeCompare(TestEnum.BAR, TestEnum.BYE, false));
		Assert.assertEquals(1, CompareUtils.safeCompare(TestEnum.BYE, TestEnum.BAR, false));
	}

}
