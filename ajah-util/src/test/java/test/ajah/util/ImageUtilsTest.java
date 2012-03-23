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

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.ajah.util.image.ImageInfo;
import com.ajah.util.image.ImageUtils;
import com.ajah.util.lang.StreamUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class ImageUtilsTest {

	@Test
	public void testGif() throws IOException {
		final byte[] data = StreamUtils.toByteArray(getClass().getResourceAsStream("/test.gif"));
		final ImageInfo info = ImageUtils.getInfo(data);
		Assert.assertEquals(50, info.getHeight());
		Assert.assertEquals(50, info.getWidth());
		Assert.assertEquals("gif", info.getFormat().getSuffix());
	}

	@Test
	public void testJpg() throws IOException {
		final byte[] data = StreamUtils.toByteArray(getClass().getResourceAsStream("/test.jpg"));
		final ImageInfo info = ImageUtils.getInfo(data);
		Assert.assertEquals(50, info.getHeight());
		Assert.assertEquals(50, info.getWidth());
		Assert.assertEquals("jpg", info.getFormat().getSuffix());
	}

	@Test
	public void testPng() throws IOException {
		final byte[] data = StreamUtils.toByteArray(getClass().getResourceAsStream("/test.png"));
		final ImageInfo info = ImageUtils.getInfo(data);
		Assert.assertEquals(50, info.getHeight());
		Assert.assertEquals(50, info.getWidth());
		Assert.assertEquals("png", info.getFormat().getSuffix());
	}
}
