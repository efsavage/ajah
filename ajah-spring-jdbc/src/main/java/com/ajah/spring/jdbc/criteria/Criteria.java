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

import java.util.ArrayList;
import java.util.List;

import com.ajah.util.CollectionUtils;
import com.ajah.util.Identifiable;
import com.ajah.util.ToStringable;
import com.ajah.util.lang.NameValuePair;

/**
 * Object-oriented way to construct a WHERE statement.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class Criteria {

	private List<NameValuePair<String>> eqs = null;
	private List<NameValuePair<Order>> orderBys = null;
	private int offset = 0;
	private int rowCount = 0;

	/**
	 * A field match. Supports nulls (as "IS NULL").
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria eq(String field, ToStringable value) {
		if (value == null) {
			return eq(field, (String) null);
		}
		return eq(field, value.toString());
	}

	/**
	 * A field match. Supports nulls (as "IS NULL").
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria eq(String field, String value) {
		if (this.eqs == null) {
			this.eqs = new ArrayList<>();
		}
		this.eqs.add(new NameValuePair<>(field, value));
		return this;
	}

	/**
	 * A field match. Supports nulls (as "IS NULL").
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be. {@ink Identifable#getId()} will
	 *            be invoked on this object if it is not null.
	 *            {@link Object#toString()} will then be invoked on the ID
	 *            object, which must not be null.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria eq(String field, Identifiable<?> value) {
		if (value == null) {
			return eq(field, (String) null);
		}
		return eq(field, value.getId().toString());
	}

	/**
	 * Add an "ORDER BY" clause.
	 * 
	 * @param field
	 *            The field to sort by.
	 * @param order
	 *            The order by which to sort.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria orderBy(String field, Order order) {
		if (this.orderBys == null) {
			this.orderBys = new ArrayList<>();
		}
		this.orderBys.add(new NameValuePair<>(field, order));
		return this;
	}

	/**
	 * Constructs a {@link Where} object from this instance, suitable for
	 * creating a prepared SQL statement.
	 * 
	 * @return A where clause that is equivalent to this criteria.
	 */
	public Where getWhere() {
		List<String> values = new ArrayList<>();
		StringBuilder where = new StringBuilder();
		boolean first = true;
		if (!CollectionUtils.isEmpty(this.eqs)) {
			for (NameValuePair<String> eq : this.eqs) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(eq.getName());
				where.append("=?");
				values.add(eq.getValue());
			}
		}
		return new Where(where.toString(), values);
	}

	/**
	 * Returns the LIMIT number of this Criteria. The default value is 0, which
	 * will yield a query without a LIMIT clause.
	 * 
	 * @return The number of records this criteria should yield, or 0 which
	 *         means unlimited.
	 */
	public Limit getLimit() {
		return new Limit(this.offset, this.rowCount);
	}

	/**
	 * Set the LIMIT number of this Criteria. Setting this to 0 will yield a
	 * query without a LIMIT clause.
	 * 
	 * @param newRowCount
	 *            The number of records this criteria should yield, or 0 which
	 *            means unlimited.
	 */
	public void rowCount(int newRowCount) {
		this.rowCount = newRowCount;
	}

}
