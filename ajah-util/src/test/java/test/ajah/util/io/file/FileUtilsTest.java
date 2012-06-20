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
package test.ajah.util.io.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ajah.util.Validate;
import com.ajah.util.io.file.FileUtils;
import com.ajah.util.text.LoremIpsum;

/**
 * Tests email validator
 * 
 * @see Validate#isEmail(String)
 * @author Eric F. Savage <code@efsavage.com>
 */
public class FileUtilsTest {

	File file = null;

	/**
	 * Create a random file to use for testing.
	 */
	@Before
	public void setup() {
		this.file = new File("/tmp/.ajah/" + UUID.randomUUID().toString());
		this.file.getParentFile().mkdirs();
	}

	/**
	 * Test string write/read.
	 * 
	 * @throws IOException
	 */
	@Test
	public void writeFile() throws IOException {
		FileUtils.write(this.file, getClass().getName());
		Assert.assertEquals(getClass().getName(), FileUtils.readFile(this.file));
		String lipsum = LoremIpsum.getParagraph() + "\n\n" + LoremIpsum.getParagraph();
		FileUtils.write(this.file, lipsum);
		Assert.assertEquals(lipsum, FileUtils.readFile(this.file));
	}

	/**
	 * Remove the random file.
	 */
	@After
	public void teardown() {
		this.file.delete();
	}

}
