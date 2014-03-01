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

import lombok.extern.java.Log;

import com.ajah.util.MathUtils;
import com.ajah.util.data.DataSizeUnit;
import com.ajah.util.log.Report;

/**
 * A utility for scanning a directory and generating a report about it's
 * structure.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class FileScan {

	private long files;
	private long bytes;
	private long directories;
	private long emptyDirectories;
	private final File root;
	private final Report report;
	private static long[] ranges = new long[] { 0, 1, DataSizeUnit.KIBIBYTE.getBytes() - 1, DataSizeUnit.KIBIBYTE.getAsBytes(10), DataSizeUnit.KIBIBYTE.getAsBytes(100),
			DataSizeUnit.MEBIBYTE.getAsBytes(1), DataSizeUnit.MEBIBYTE.getAsBytes(10), DataSizeUnit.MEBIBYTE.getAsBytes(100), Long.MAX_VALUE };
	private static long[][] rangeCountSizes = new long[ranges.length][2];

	/**
	 * Scans one or more directories.
	 * 
	 * @param args
	 *            The directories to scan
	 * @throws IOException
	 *             If a file or directory could not be scanned.
	 */
	public static void main(final String[] args) throws IOException {
		for (final String arg : args) {
			// Send logger output to our FileHandler.
			log.addHandler(new ConsoleHandler());
			// Request that every detail gets logged.
			log.setLevel(Level.ALL);
			final File file = new File(arg);
			final Report report = new Report();
			report.add(System.out);
			report.set(log);
			report.add(new File("/tmp/report-" + System.currentTimeMillis() + ".txt"));
			report.rule();
			report.println("Report for: " + file.getAbsolutePath());
			final FileScan scan = new FileScan(file, report);
			scan.scan();
			report.rule();
			report.println("Scan Complete");
			report.rule();
			scan.report(0);
			report.close();
		}
	}

	/**
	 * Public constructor.
	 * 
	 * @param root
	 *            The root directory to scan.
	 * @param report
	 *            The report to write results to.
	 */
	public FileScan(final File root, final Report report) {
		this.root = root;
		this.report = report;
	}

	/**
	 * Returns the number of bytes scanned.
	 * 
	 * @return The number of bytes scanned.
	 */
	public long getBytes() {
		return this.bytes;
	}

	/**
	 * Returns the number of directories scanned.
	 * 
	 * @return The number of directories scanned.
	 */
	public long getDirectories() {
		return this.directories;
	}

	/**
	 * Returns the number of empty directories scanned.
	 * 
	 * @return The number of empty directories scanned.
	 */
	public long getEmptyDirectories() {
		return this.emptyDirectories;
	}

	/**
	 * Returns the number of files scanned.
	 * 
	 * @return The number of files scanned.
	 */
	public long getFiles() {
		return this.files;
	}

	/**
	 * Returns the root file of the scan.
	 * 
	 * @return The root file of the scan.
	 */
	public File getRoot() {
		return this.root;
	}

	private void report(final int depth) {
		this.report.println(depth, "Files: " + NumberFormat.getInstance().format(getFiles()));
		this.report.println(depth, "Directories: " + NumberFormat.getInstance().format(getDirectories()));
		this.report.println(depth, "Empty Directories: " + NumberFormat.getInstance().format(getEmptyDirectories()));
		this.report.println(depth, "Bytes: " + NumberFormat.getInstance().format(getBytes()));
		this.report.println(depth, "Size: " + DataSizeUnit.format(getBytes()));
		this.report.rule();
		for (int range = 0; range < ranges.length - 1; range++) {
			this.report.println(depth, DataSizeUnit.format(ranges[range]) + " to " + DataSizeUnit.format(ranges[range + 1]));
			this.report.println(depth + 1, "Files: " + NumberFormat.getInstance().format(rangeCountSizes[range][0]));
			this.report.println(depth + 1, "Size: " + DataSizeUnit.format(rangeCountSizes[range][1]));
		}
	}

	/**
	 * Executes a scan starting at the root directory.
	 * 
	 * @throws IOException
	 *             If a file or directory could not be scanned.
	 */
	public void scan() throws IOException {
		final int depth = 0;
		scan(this.root, depth);
	}

	private long[] scan(final File file, final int depth) throws IOException {
		// log.info("Scanning: " + file.getAbsolutePath());
		final long[] results = new long[4];
		if (file.isDirectory()) {
			results[2]++;
			final File[] subFiles = file.listFiles();
			if (subFiles == null || subFiles.length < 1) {
				results[3]++;
			} else {
				for (final File subFile : subFiles) {
					if (subFile.isDirectory()) {
						MathUtils.addTo(results, scan(subFile, depth + 1));
					} else {
						results[0]++;
						// long oldSize = results[1];
						final long fileSize = subFile.length();
						results[1] += fileSize;
						// long newSize = results[1];

						// Update the buckets
						for (int range = 0; range < ranges.length - 1; range++) {
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
}
