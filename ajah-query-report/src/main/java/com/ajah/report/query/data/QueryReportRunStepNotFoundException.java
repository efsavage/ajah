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
package com.ajah.report.query.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.report.query.QueryReportRunStep;
import com.ajah.report.query.QueryReportRunStepId;

/**
 * Thrown when an {@link QueryReportRunStep} was expected to be found, but was not.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryReportRunStepNotFoundException extends Exception {

	private final QueryReportRunStepId id;

	/**
	 * Thrown when an {@link QueryReportRunStep} could not be found by it's internal ID.
	 * 
	 * @param id
	 *            The internal ID that was sought.
	 */
	public QueryReportRunStepNotFoundException(final QueryReportRunStepId id) {
		super("ID: " + id);
		this.id = id;
	}

}
