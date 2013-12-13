/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.flatfile;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import com.ajah.util.StringUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class FlatFileWriter implements Closeable {

	private final FileWriter writer;
	private final List<FlatFileColumn> columns = new ArrayList<>();
	private final Map<String, FlatFileColumn> map = new HashMap<>();
	private FlatFileRow row = null;
	@Getter
	private final FlatFileFormat format;
	@Getter
	private final File file;
	@Getter
	@Setter
	private boolean flushEveryLine;

	public FlatFileWriter(FlatFileFormat format, File file) throws IOException {
		this.format = format;
		this.file = file;
		this.writer = new FileWriter(file);
	}

	public void addColumn(String name, boolean required) {
		if (this.map.get(name) != null) {
			throw new IllegalArgumentException("Duplicate column name " + name);
		}
		FlatFileColumn column = new FlatFileColumn(name, null, required);
		this.columns.add(column);
		this.map.put(column.getName(), column);
	}

	public void addColumn(String name, String defaultValue) {
		if (this.map.get(name) != null) {
			throw new IllegalArgumentException("Duplicate column name " + name);
		}
		FlatFileColumn column = new FlatFileColumn(name, defaultValue, false);
		this.columns.add(column);
		this.map.put(column.getName(), column);
	}

	@Override
	public void close() throws IOException {
		if (this.row == null) {
			writeHeader();
		} else {
			writeRow();
		}
		this.writer.close();
	}

	public FlatFileRow newRow() throws IOException {
		if (this.row == null) {
			writeHeader();
		} else {
			writeRow();
		}
		this.row = new FlatFileRow(this.map, this);
		return this.row;
	}

	private void writeRow() throws IOException {
		boolean first = true;
		for (FlatFileColumn column : this.columns) {
			String value = this.row.get(column.getName());
			if (column.isRequired() && StringUtils.isBlank(value)) {
				throw new IllegalArgumentException("Column is required: " + column.getName());
			}
			write(value, first);
			if (first) {
				first = false;
			}
		}
		this.writer.write("\r\n");
		if (this.flushEveryLine) {
			this.writer.flush();
		}
		this.row = null;
	}

	public void writeHeader() throws IOException {
		boolean first = true;
		for (FlatFileColumn column : this.columns) {
			write(column.getName(), first);
			if (first) {
				first = false;
			}
		}
		this.writer.write("\r\n");
		if (this.flushEveryLine) {
			this.writer.flush();
		}
	}

	private void write(String value, boolean first) throws IOException {
		switch (this.format) {
		case CSV:
			if (!first) {
				this.writer.write(",");
			}
			if (value.contains(",") || value.contains("\"") || value.contains("\r") || value.contains("\n")) {
				this.writer.write("\"" + value.replaceAll("\"", "\"\"") + "\"");
			} else {
				this.writer.write(value);
			}
			break;
		case TAB:
			if (value != null && value.contains("\t")) {
				log.log(Level.WARNING, "Writing data containing tabs to a tab-delimited file");
			}
			if (!first) {
				this.writer.write("\t");
			}
			this.writer.write(value);
			break;

		}
	}

	public void addColumn(String name) {
		addColumn(name, false);
	}

	/**
	 * This is for injecting non-standard content such as an extra header or
	 * footer row.
	 * 
	 * @param string
	 *            The string to write as-is. Should include any line terminators
	 *            if they are necessary.
	 * @throws IOException
	 *             If the string could not be written.
	 */
	public void writeString(String string) throws IOException {
		this.writer.write(string);
	}

}
