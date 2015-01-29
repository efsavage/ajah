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
import java.util.List;

import com.ajah.rest.api.model.relay.IdentifiableEnumRelay;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ajah.report.query.QueryReportId;
import com.ajah.report.query.QueryReportStatus;
import com.ajah.report.query.QueryReportType;

/**
 * Simplified version of a QueryReport for easier serialization/deserialization.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@JsonInclude(Include.NON_NULL)
public class QueryReportRelay {

	public String id;
	public String name;
	public IdentifiableEnumRelay<String> status;
	public IdentifiableEnumRelay<String> type;
	public Date created;

}
