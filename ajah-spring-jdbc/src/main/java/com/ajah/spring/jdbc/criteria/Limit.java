/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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

import lombok.Data;

/**
 * A simple bean that can be passed to help construct the LIMIT part of prepared
 * sql statement.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class Limit {

	private final long offset;
	private final long count;

	/**
	 * Creates a LIMIT clause from this instances variables.
	 * 
	 * @return The LIMIT clause to use in a SQL query.
	 */
	public String getSql() {
		if (this.offset == 0 && this.count == 0) {
			return "";
		}
		if (this.offset > 0 && this.count == 0) {
			return " LIMIT " + this.offset + ", " + Integer.MAX_VALUE;
		}
		if (this.offset == 0 && this.count > 0) {
			return " LIMIT " + this.count;
		}
		return " LIMIT " + this.offset + ", " + this.count;
	}

}
