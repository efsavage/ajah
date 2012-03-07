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
package com.ajah.event.sql;

import lombok.Data;

import com.ajah.event.Event;

/**
 * A SqlEvent happens when the application talks to a database server, most
 * commonly when a query is executed.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class SqlEvent implements Event<SqlEventId> {

	private SqlEventId id;

	@Override
	public void complete() {
		// Empty
	}

	/**
	 * This can be used as fluent shorthand to end an event. It doesn't actually
	 * wrap the method or affect it in any way, it just appears to in syntax.
	 * Example:
	 * 
	 * <code>return new SqlEvent().wrap(super.query(sql, rse));</code>
	 * 
	 * @param rv
	 * @return The value of the "wrapped" method.
	 */
	public <RV> RV wrap(final RV rv) {
		complete();
		return rv;
	}

	/**
	 * This can be used as fluent shorthand to end an event. It doesn't actually
	 * wrap the method or affect it in any way, it just appears to in syntax.
	 * Example:
	 * 
	 * <code>return new SqlEvent().wrapInt(super.query(sql, rse));</code>
	 * 
	 * @param rv
	 * @return The value of the "wrapped" method.
	 */
	public int wrapInt(final int rv) {
		complete();
		return rv;
	}

	/**
	 * This can be used as fluent shorthand to end an event. It doesn't actually
	 * wrap the method or affect it in any way, it just appears to in syntax.
	 * Example:
	 * 
	 * <code>return new SqlEvent().wrapLong(super.query(sql, rse));</code>
	 * 
	 * @param rv
	 * @return The value of the "wrapped" method.
	 */
	public long wrapLong(final long rv) {
		complete();
		return rv;
	}

}
