/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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

package com.ajah.spring.jdbc.status;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import lombok.extern.java.Log;

/**
 * Checks a {@link DataSource} and builds a status report on it.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class DataSourceChecker {

	/**
	 * Checks a {@link DataSource} and builds a status report on it.
	 * 
	 * @param dataSource
	 *            The data source to check.
	 * @return The status report. Should return even in the event of an error.
	 */
	public static DataSourceStatus check(DataSource dataSource) {
		DataSourceStatus status = new DataSourceStatus();
		try (Connection connection = dataSource.getConnection()) {
			try (ResultSet rs = connection.createStatement().executeQuery("SELECT NOW()")) {
				if (rs.next()) {
					status.serverTimestamp = rs.getDate(1);
				}
			}
			DatabaseMetaData metaData = connection.getMetaData();
			if (metaData != null) {
				status.serverProduct.name = metaData.getDatabaseProductName();
				status.serverProduct.majorVersion = metaData.getDatabaseMajorVersion();
				status.serverProduct.minorVersion = metaData.getDatabaseMinorVersion();
				status.serverProduct.productVersion = metaData.getDatabaseProductVersion();
				status.driver.name = metaData.getDriverMajorVersion();
				status.driver.majorVersion = metaData.getDriverMajorVersion();
				status.driver.minorVersion = metaData.getDriverMinorVersion();
				status.driver.productVersion = metaData.getDriverVersion();
			} else {
				log.warning("DB metadata is null");
			}
		} catch (SQLException e) {
			status.error = e;
		}
		return status;
	}

}
