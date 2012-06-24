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

import junit.framework.Assert;

import org.junit.Test;

import com.ajah.util.io.AjahStringBuilder;
import com.ajah.util.io.Compact;

/**
 * Tests {@link AjahStringBuilder}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class AjahStringBuilderTest {

	/**
	 * Test operations in AjahStringBuilder for valid scenarios
	 */
	@Test
	public void testStringBuilder() {
		final AjahStringBuilder ajahStringBuilder = new AjahStringBuilder(Compact.HIGH);
		ajahStringBuilder.append("1");
		ajahStringBuilder.append('2', Compact.HIGH);
		ajahStringBuilder.append(new char[] { '3', '4' }, Compact.HIGH);
		Assert.assertNotNull(ajahStringBuilder.toString());
		Assert.assertEquals("1234", ajahStringBuilder.toString());
	}

	/**
	 * Test the String Builder operation where elements are dropped.
	 */
	@Test
	public void testStringBuilderDrop() {
		final AjahStringBuilder ajahStringBuilder = new AjahStringBuilder(Compact.HIGH);
		ajahStringBuilder.append("1");
		ajahStringBuilder.append('2', Compact.LOW);
		ajahStringBuilder.append(new char[] { '3', '4' }, Compact.LOW);
		Assert.assertNotNull(ajahStringBuilder.toString());
		Assert.assertEquals("1", ajahStringBuilder.toString());
	}
}
