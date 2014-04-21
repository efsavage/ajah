/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
package test.ajah.report;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import lombok.extern.java.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ajah.report.FileScanReport;
import com.ajah.report.ReportWriter;

/**
 * Tests {@link FileScanReport}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class FileScanTest {

	File file = null;

	/**
	 * Scans the /tmp directory.
	 * 
	 * @throws IOException
	 */
	@Test
	public void runScan() throws IOException {
		// TODO What's the best way to unit test files?
		try (final ReportWriter report = new ReportWriter()) {
			report.set(log);
			new FileScanReport(this.file, report).scan();
		}
	}

	/**
	 * Create a random file to use for testing.
	 * 
	 * @throws IOException
	 */
	@Before
	public void setup() throws IOException {
		this.file = new File("/tmp/.ajah/" + UUID.randomUUID().toString());
		this.file.mkdirs();
		new File(this.file, UUID.randomUUID().toString()).createNewFile();
		new File(this.file, UUID.randomUUID().toString()).mkdirs();
	}

	/**
	 * Remove the random file.
	 */
	@After
	public void teardown() {
		// this.file.delete();
	}

}
