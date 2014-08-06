/*
 *  Copyright 2012-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.spring.jdbc.criteria;

import java.util.List;

import lombok.Data;

import com.ajah.util.StringUtils;

/**
 * A simple bean that can be passed to help construct the WHERE part of prepared
 * sql statement.
 * 
 * NOTE: The values must be in the same order as the "?" placeholders in the sql
 * field.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class Where {

	private final String sql;
	private final List<String> values;

	/**
	 * Returns the SQL for this where, prepending the 'WHERE' if the clause is
	 * not blank and includeWhere is true.
	 * 
	 * @param includeWhere
	 *            Include the WHERE clause if the base SQL is not blank?
	 * @return A valid WHERE clause, may be a single space (to avoid
	 *         concatenation errors).
	 */
	public String getSql(boolean includeWhere) {
		if (StringUtils.isBlank(this.sql)) {
			return " ";
		}
		if (includeWhere) {
			return " WHERE " + this.sql;
		}
		return this.sql;
	}

	/**
	 * Returns the SQL for this where, prepending the 'WHERE' if the clause is
	 * not blank.
	 * 
	 * @return A valid WHERE clause, may be single space (to avoid concatenation
	 *         errors)
	 */
	public String getSql() {
		if (StringUtils.isBlank(this.sql)) {
			return " ";
		}
		return " WHERE " + this.sql;
	}

}
