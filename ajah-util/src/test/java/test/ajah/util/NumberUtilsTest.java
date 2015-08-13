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
package test.ajah.util;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.NumberUtils;
import com.ajah.util.Validate;

/**
 * Tests email validator
 * 
 * @see Validate#isEmail(String)
 * @author Eric F. Savage <code@efsavage.com>
 */
public class NumberUtilsTest {

	@Test
	public void round() {
		Assert.assertEquals(1000000, NumberUtils.round(1000000, 1000000));
		Assert.assertEquals(1000000, NumberUtils.round(1000000, 100000));
		Assert.assertEquals(1000000, NumberUtils.round(1000000, 10000));
		Assert.assertEquals(1000000, NumberUtils.round(1000500, 10000));
		Assert.assertEquals(1200000, NumberUtils.round(1232342, 100000));
	}

	@Test
	public void safeInt() {
		Assert.assertEquals(1, NumberUtils.safeInt("200000.00", 1));
		Assert.assertEquals(200000, NumberUtils.safeInt("200000", 200000));
	}
}
