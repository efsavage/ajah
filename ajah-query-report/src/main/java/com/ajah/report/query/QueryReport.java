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

import java.util.Date;

import javax.persistence.GeneratedValue;

import lombok.Data;

import com.ajah.report.group.ReportGroupId;
import com.ajah.util.Identifiable;

@Data
public class QueryReport implements Identifiable<QueryReportId> {

	@GeneratedValue
	private QueryReportId id;
	private ReportGroupId reportGroupId;
	private String name;
	private String sql;
	private QueryReportStatus status;
	private QueryReportType type;
	private Date created;
	private InformationLevel personalInfoLevel;
	private InformationLevel financialInfoLevel;

}
