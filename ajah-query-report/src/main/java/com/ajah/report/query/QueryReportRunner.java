/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.report.query;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.sql.DataSource;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import com.ajah.flatfile.FlatFileFormat;
import com.ajah.flatfile.FlatFileRow;
import com.ajah.flatfile.FlatFileWriter;
import com.ajah.report.query.data.QueryReportManager;
import com.ajah.report.query.data.QueryReportNotFoundException;
import com.ajah.report.query.data.QueryReportRunManager;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.util.io.file.FileUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 *
 */
@Service
@Log
public class QueryReportRunner {

	protected static final Logger sqlLog = Logger.getLogger("ajah.sql");

	@Autowired
	QueryReportManager reportManager;

	@Autowired
	QueryReportRunManager runManager;

	private JdbcTemplate jdbcTemplate;

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");

	@Autowired
	public void setDataSource(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Loads a report and creates a new run.
	 * 
	 * @param queryReportId
	 *            The ID of the report to run.
	 * @return The run entity.
	 * @throws IOException
	 *             If the report couldn't be written to a temp file.
	 * @throws DataOperationException
	 *             If the query couldn't be executed.
	 * @throws QueryReportNotFoundException
	 *             If the report is not found in the database.
	 */
	public QueryReportRun run(QueryReportId queryReportId) throws QueryReportNotFoundException, DataOperationException, IOException {
		QueryReport report = this.reportManager.load(queryReportId);
		Date now = new Date();
		QueryReportRun run = null;
		try {
			run = this.runManager.create(report.getId(), report.getName() + "-" + DATE_FORMAT.format(now), QueryReportRunType.STANDARD, QueryReportRunStatus.RUNNING).getEntity();
			// File file = File.createTempFile("query-report-" + queryReportId +
			// "-", ".csv");
			File file = new File("/tmp/query-report-" + queryReportId + "-" + System.currentTimeMillis(), ".csv");
			log.fine("Using temp file " + file.getAbsolutePath());
			int[] results = writeResultSet(report.getSql(), file);
			run.setColumns(results[0]);
			run.setRows(results[1]);
			log.fine("Wrote " + run.getColumns() + " columns, " + run.getRows() + " rows");
			run.setData(FileUtils.readFile(file));
			run.setStatus(QueryReportRunStatus.COMPLETE);
			this.runManager.save(run);
		} catch (DataOperationException e) {
			if (run != null) {
				run.setData(e.getMessage());
				run.setStatus(QueryReportRunStatus.ERROR);
				this.runManager.save(run);
			}
			throw e;
		} catch (IOException e) {
			if (run != null) {
				run.setData(e.getMessage());
				run.setStatus(QueryReportRunStatus.ERROR);
				this.runManager.save(run);
			}
			throw e;
		}
		return run;
	}

	private int[] writeResultSet(final String sql, final File file) throws IOException {
		final int[] results = new int[2];
		file.getParentFile().mkdirs();
		try (FlatFileWriter writer = new FlatFileWriter(FlatFileFormat.CSV, file)) {
			sqlLog.finest(sql);
			this.jdbcTemplate.query(sql, new RowCallbackHandler() {

				@Override
				public void processRow(final ResultSet rs) throws SQLException {
					final int columnCount = rs.getMetaData().getColumnCount();
					results[0] = columnCount;
					log.fine(columnCount + " columns");
					if (!writer.isColumnsLocked()) {
						for (int i = 1; i <= columnCount; i++) {
							log.finest("Column " + i);
							final String label = rs.getMetaData().getColumnLabel(i);
							writer.addColumn(label);
						}
						writer.setColumnsLocked(true);
					}
					try {
						FlatFileRow row;
						row = writer.newRow();
						results[1]++;
						for (int i = 1; i <= columnCount; i++) {
							final String label = rs.getMetaData().getColumnLabel(i);
							row.set(label, rs.getString(label));
						}
					} catch (final IOException e) {
						throw new SQLException(e);
					}
				}

			});
		}
		return results;
	}

}
