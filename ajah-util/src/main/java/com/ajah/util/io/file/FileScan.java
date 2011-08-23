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
package com.ajah.util.io.file;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ajah.util.MathUtils;
import com.ajah.util.data.DataSizeUnit;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class FileScan {

	private static final Logger log = Logger.getLogger(FileScan.class.getName());

	private long files;
	private long bytes;
	private long directories;
	private long emptyDirectories;
	private final File root;
	private Report report;
	private static long[] ranges = new long[] { 0, 1, DataSizeUnit.KIBIBYTE.getBytes() - 1, DataSizeUnit.KIBIBYTE.getAsBytes(10), DataSizeUnit.KIBIBYTE.getAsBytes(100),
			DataSizeUnit.MEBIBYTE.getAsBytes(1), DataSizeUnit.MEBIBYTE.getAsBytes(10), DataSizeUnit.MEBIBYTE.getAsBytes(100), Long.MAX_VALUE };
	private static long[][] rangeCountSizes = new long[ranges.length][2];

	public long getFiles() {
		return this.files;
	}

	public long getBytes() {
		return this.bytes;
	}

	public long getDirectories() {
		return this.directories;
	}

	public long getEmptyDirectories() {
		return this.emptyDirectories;
	}

	public File getRoot() {
		return this.root;
	}

	public FileScan(final File root, Report report) {
		this.root = root;
		this.report = report;
	}

	public void scan() throws IOException {
		int depth = 0;
		scan(this.root, depth);
	}

	private long[] scan(File file, int depth) throws IOException {
		// log.info("Scanning: " + file.getAbsolutePath());
		long[] results = new long[4];
		if (file.isDirectory()) {
			results[2]++;
			File[] subFiles = file.listFiles();
			if (subFiles == null || subFiles.length < 1) {
				results[3]++;
			} else {
				for (File subFile : subFiles) {
					if (subFile.isDirectory()) {
						MathUtils.addTo(results, scan(subFile, depth + 1));
					} else {
						results[0]++;
						// long oldSize = results[1];
						long fileSize = subFile.length();
						results[1] += fileSize;
						// long newSize = results[1];

						// Update the buckets
						for (int range = 0; range < (ranges.length - 1); range++) {
							if (fileSize >= ranges[range] && fileSize < ranges[range + 1]) {
								// File Count
								rangeCountSizes[range][0]++;
								// File Size
								rangeCountSizes[range][1] += fileSize;
							}
						}
					}
				}
			}
		} else {
			results[0]++;
			results[1] += file.length();
		}
		this.files = results[0];
		this.bytes = results[1];
		this.directories = results[2];
		this.emptyDirectories = results[3];
		// report(depth);
		return results;
	}

	public static void main(String[] args) throws IOException {
		for (String arg : args) {
			// Send logger output to our FileHandler.
			log.addHandler(new ConsoleHandler());
			// Request that every detail gets logged.
			log.setLevel(Level.ALL);
			File file = new File(arg);
			Report report = new Report();
			report.add(System.out);
			report.add(log);
			report.add(new File("/tmp/report-" + System.currentTimeMillis() + ".txt"));
			report.rule();
			report.println("Report for: " + file.getAbsolutePath());
			FileScan scan = new FileScan(file, report);
			scan.scan();
			report.rule();
			report.println("Scan Complete");
			report.rule();
			scan.report(0);
			report.close();
		}
	}

	private void report(int depth) {
		this.report.println(depth, "Files: " + NumberFormat.getInstance().format(getFiles()));
		this.report.println(depth, "Directories: " + NumberFormat.getInstance().format(getDirectories()));
		this.report.println(depth, "Empty Directories: " + NumberFormat.getInstance().format(getEmptyDirectories()));
		this.report.println(depth, "Bytes: " + NumberFormat.getInstance().format(getBytes()));
		this.report.println(depth, "Size: " + DataSizeUnit.format(getBytes()));
		this.report.rule();
		for (int range = 0; range < (ranges.length - 1); range++) {
			this.report.println(depth, DataSizeUnit.format(ranges[range]) + " to " + DataSizeUnit.format(ranges[range + 1]));
			this.report.println(depth + 1, "Files: " + NumberFormat.getInstance().format(rangeCountSizes[range][0]));
			this.report.println(depth + 1, "Size: " + DataSizeUnit.format(rangeCountSizes[range][1]));
		}
	}
}
