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
package test.ajah.text;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.Validate;
import com.ajah.util.text.LocaleUtils;

/**
 * Tests {@link LocaleUtils}.
 * 
 * @see Validate#isEmail(String)
 * @author Eric F. Savage <code@efsavage.com>
 */
public class LocaleTest {

	/**
	 * Test Denmark
	 */
	@Test
	public void denmark() {
		final Locale locale = LocaleUtils.getLocale("DNK");
		Assert.assertNotNull(locale);
		Assert.assertEquals("Danish (Denmark)", locale.getDisplayName());
	}
}
