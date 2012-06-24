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
package test.ajah.util.net;

import junit.framework.Assert;

import org.junit.Test;

import com.ajah.util.net.AjahMimeType;
import com.ajah.util.net.HttpClient;

/**
 * Tests {@link HttpClient}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class AjahMimeTypeTest {

	/**
	 * Test various attributes and methods in AjahMimeType
	 */
	@Test
	public void testMimeType() {
		final AjahMimeType mimeType = AjahMimeType.APPLICATION_JAVASCRIPT;
		Assert.assertEquals("application/javascript", mimeType.getId());
		Assert.assertEquals("application", mimeType.getPrimaryType());
		Assert.assertEquals("javascript", mimeType.getSubType());
		Assert.assertEquals(mimeType, AjahMimeType.get("application/javascript"));
	}

	/**
	 * Test get operation in AjahMimeType
	 */
	@Test
	public void testMimeTypeForUnknown() {
		Assert.assertEquals(AjahMimeType.UNKNOWN, AjahMimeType.get(""));
	}

}
