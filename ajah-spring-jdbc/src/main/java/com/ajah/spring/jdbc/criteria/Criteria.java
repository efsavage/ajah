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
import java.util.logging.Level;

import lombok.extern.java.Log;

import com.ajah.util.AjahUtils;
import com.ajah.util.CollectionUtils;
import com.ajah.util.StringUtils;
import com.ajah.util.lang.NameValuePair;

/**
 * Object-oriented way to construct a WHERE statement.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
// TODO add gt/lt/ge/le/ne
// TODO handle Date params
public class Criteria extends AbstractCriteria<Criteria> {

	private List<NameValuePair<String>> gts = null;
	private List<NameValuePair<String>> gtes = null;
	private List<NameValuePair<String>> ltes = null;
	private List<NameValuePair<String>> likes = null;
	private List<NameValuePair<String>> reverseLikes = null;
	private List<NameValuePair<String>> joins = null;
	private List<NameValuePair<Order>> orderBys = null;
	private List<SubCriteria> ands = null;
	private List<SubCriteria> ors = null;
	private long offset = 0;
	private long rowCount = 0;

	/**
	 * A subclause, included as an AND, but may contain ORs.
	 * 
	 * @param subCriteria
	 *            The subCriteria to include
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria and(final SubCriteria subCriteria) {
		AjahUtils.requireParam(subCriteria, "field");
		if (this.ands == null) {
			this.ands = new ArrayList<>();
		}
		this.ands.add(subCriteria);
		return this;
	}

	/**
	 * Add an "ORDER BY" clause for the field with an ascending order.
	 * 
	 * @param field
	 *            The field to sort by.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria asc(final String field) {
		AjahUtils.requireParam(field, "field");
		return orderBy(field, Order.ASC);
	}

	/**
	 * Add an "ORDER BY" clause for the field with an ascending order.
	 * 
	 * @param field
	 *            The field to sort by.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria desc(final String field) {
		AjahUtils.requireParam(field, "field");
		return orderBy(field, Order.DESC);
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
	 * Returns the SQL for the ORDER BY portion of this query, or an empty
	 * string.
	 * 
	 * @return The SQL for the ORDER BY portion of this query, or an empty
	 *         string.
	 */
	public String getOrderBySql() {
		if (CollectionUtils.isEmpty(this.orderBys)) {
			return " ";
		}
		final StringBuilder sql = new StringBuilder();
		boolean first = true;
		for (final NameValuePair<Order> orderBy : this.orderBys) {
			if (first) {
				sql.append(" ORDER BY ");
				first = false;
			} else {
				sql.append(",");
			}
			sql.append(orderBy.getName());
			if (orderBy.getValue() == null) {
				// Do nothing
			} else if (orderBy.getValue() != Order.ASC) {
				sql.append(" ");
				sql.append(orderBy.getValue().name());
			}
		}
		return sql.toString();
	}

	/**
	 * @see com.ajah.spring.jdbc.criteria.AbstractCriteria#getThis()
	 */
	@Override
	protected Criteria getThis() {
		return this;
	}

	public Where getWhere() {
		return getWhere(null);
	}

	/**
	 * Constructs a {@link Where} object from this instance, suitable for
	 * creating a prepared SQL statement.
	 * 
	 * @return A where clause that is equivalent to this criteria.
	 */
	public Where getWhere(String tableName) {
		final List<String> values = new ArrayList<>();
		final StringBuilder where = new StringBuilder();
		boolean first = true;
		String tablePrefix = StringUtils.isBlank(tableName) ? "" : "`" + tableName + "`.";

		if (!CollectionUtils.isEmpty(this.eqs)) {
			for (final NameValuePair<String> eq : this.eqs) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}

				where.append(tablePrefix);
				where.append("`");
				where.append(eq.getName());
				where.append("`");
				if (eq.getValue() == null) {
					where.append(" IS NULL");
				} else {
					where.append("=?");
					values.add(eq.getValue());
				}
			}
		}
		if (!CollectionUtils.isEmpty(this.gtes)) {
			for (final NameValuePair<String> gte : this.gtes) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(tablePrefix);
				where.append("`");
				where.append(gte.getName());
				where.append("`");
				where.append(">=?");
				values.add(gte.getValue());
			}
		}
		if (!CollectionUtils.isEmpty(this.gts)) {
			for (final NameValuePair<String> gt : this.gts) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(tablePrefix);
				where.append("`");
				where.append(gt.getName());
				where.append("`");
				where.append(">?");
				values.add(gt.getValue());
			}
		}
		if (!CollectionUtils.isEmpty(this.ltes)) {
			for (final NameValuePair<String> lte : this.ltes) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(tablePrefix);
				where.append("`");
				where.append(lte.getName());
				where.append("`");
				where.append("<=?");
				values.add(lte.getValue());
			}
		}
		if (!CollectionUtils.isEmpty(this.joins)) {
			for (final NameValuePair<String> join : this.joins) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(join.getName());
				where.append("=");
				where.append(join.getValue());
			}
		}
		if (!CollectionUtils.isEmpty(this.likes)) {
			for (final NameValuePair<String> like : this.likes) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(tablePrefix);
				where.append("`");
				where.append(like.getName());
				where.append("`");
				where.append(" LIKE '");
				where.append(like.getValue().replaceAll("'", "\\'"));
				where.append("'");
			}
		}
		if (!CollectionUtils.isEmpty(this.reverseLikes)) {
			for (final NameValuePair<String> reverseLike : this.reverseLikes) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(tablePrefix);
				where.append("'");
				where.append(reverseLike.getValue().replaceAll("'", "\\'"));
				where.append("'");
				where.append(" LIKE ");
				where.append(reverseLike.getName());
			}
		}
		if (!CollectionUtils.isEmpty(this.ands)) {
			for (final SubCriteria and : this.ands) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(and.getWhere().getSql(false));
				values.addAll(and.getWhere().getValues());
			}
		}
		if (!CollectionUtils.isEmpty(this.ors)) {
			for (final SubCriteria or : this.ors) {
				if (first) {
					first = false;
				} else {
					where.append(" OR ");
				}
				where.append(or.getWhere().getSql(false));
				values.addAll(or.getWhere().getValues());
			}
		}
		if (log.isLoggable(Level.FINEST)) {
			log.finest(where.toString());
		}
		return new Where(where.toString(), values);
	}

	/**
	 * A greater-than match.
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be greater than.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria gt(final String field, final long value) {
		AjahUtils.requireParam(field, "field");
		return gt(field, String.valueOf(value));
	}

	/**
	 * A greater-than field match.
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be greater than .
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria gt(final String field, final String value) {
		AjahUtils.requireParam(field, "field");
		if (this.gts == null) {
			this.gts = new ArrayList<>();
		}
		this.gts.add(new NameValuePair<>(field, value));
		return this;
	}

	/**
	 * A greater-than or equal to match.
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be greater than or equal to.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria gte(final String field, final long value) {
		AjahUtils.requireParam(field, "field");
		return gte(field, String.valueOf(value));
	}

	/**
	 * A greater-than or equal-to field match.
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be greater than or equal to.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria gte(final String field, final String value) {
		AjahUtils.requireParam(field, "field");
		if (this.gtes == null) {
			this.gtes = new ArrayList<>();
		}
		this.gtes.add(new NameValuePair<>(field, value));
		return this;
	}

	/**
	 * A field that must be NULL.
	 * 
	 * @param field
	 *            The field to match
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria isNull(final String field) {
		AjahUtils.requireParam(field, "field");
		return eq(field, (String) null);
	}

	/**
	 * A join match.
	 * 
	 * @param table1
	 *            The first table to join.
	 * @param table2
	 *            The second table to join.
	 * @param field
	 *            The field to join on (same field name in both tables).
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria join(final String table1, final String table2, final String field) {
		return join(table1, table2, field, field);
	}

	/**
	 * A join match.
	 * 
	 * @param table1
	 *            The first table to join.
	 * @param table2
	 *            The second table to join.
	 * @param field1
	 *            The field of the first table to join on.
	 * @param field2
	 *            The field of the second table to join on.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria join(final String table1, final String table2, final String field1, final String field2) {
		if (this.joins == null) {
			this.joins = new ArrayList<>();
		}
		this.joins.add(new NameValuePair<>("`" + table1 + "`.`" + field1 + "`", "`" + table2 + "`.`" + field2 + "`"));
		return this;
	}

	/**
	 * A LIKE field match. Uses the query as-is (i.e. add your own wildcards).
	 * 
	 * @param field
	 *            The field to match
	 * @param pattern
	 *            The pattern the field must match.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria like(final String field, final String pattern) {
		AjahUtils.requireParam(field, "field");
		AjahUtils.requireParam(pattern, "pattern");
		if (this.likes == null) {
			this.likes = new ArrayList<>();
		}
		this.likes.add(new NameValuePair<>(field, pattern));
		return this;
	}

	/**
	 * A reverse LIKE field match. Intended for when the wildcard is in the
	 * database, e.g. 'value' like field. Uses the query as-is (i.e. add your
	 * own wildcards).
	 * 
	 * @param field
	 *            The field to match
	 * @param pattern
	 *            The pattern the field must match.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria reverseLike(final String field, final String pattern) {
		AjahUtils.requireParam(field, "field");
		AjahUtils.requireParam(pattern, "pattern");
		if (this.reverseLikes == null) {
			this.reverseLikes = new ArrayList<>();
		}
		this.reverseLikes.add(new NameValuePair<>(field, pattern));
		return this;
	}

	/**
	 * A greater-than or equal to match.
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be greater than or equal to.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria lte(final String field, final long value) {
		AjahUtils.requireParam(field, "field");
		return lte(field, String.valueOf(value));
	}

	/**
	 * A less-than or equal-to field match.
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be less than or equal to.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria lte(final String field, final String value) {
		AjahUtils.requireParam(field, "field");
		if (this.ltes == null) {
			this.ltes = new ArrayList<>();
		}
		this.ltes.add(new NameValuePair<>(field, value));
		return this;
	}

	/**
	 * Sets the offset, i.e. the position of the first result.
	 * 
	 * @param offsetIndex
	 *            The offset, i.e. the position of the first result.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria offset(final long offsetIndex) {
		this.offset = offsetIndex;
		return this;
	}

	/**
	 * A subclause, included as an AND, but may contain ORs.
	 * 
	 * @param subCriteria
	 *            The subCriteria to include
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria or(final SubCriteria subCriteria) {
		AjahUtils.requireParam(subCriteria, "subCriteria");
		if (this.ors == null) {
			this.ors = new ArrayList<>();
		}
		this.ors.add(subCriteria);
		return this;
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
	public Criteria orderBy(final String field, final Order order) {
		AjahUtils.requireParam(field, "field");
		AjahUtils.requireParam(order, "order");
		if (this.orderBys == null) {
			this.orderBys = new ArrayList<>();
		}
		this.orderBys.add(new NameValuePair<>(field, order));
		return this;
	}

	/**
	 * Adds an ORDER BY RAND() sort.
	 * 
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria randomOrder() {
		if (this.orderBys == null) {
			this.orderBys = new ArrayList<>();
		}
		this.orderBys.add(new NameValuePair<>("RAND()", (Order) null));
		return this;
	}

	/**
	 * Sets the maximum number of rows to fetch.
	 * 
	 * @param maximumRowsFetched
	 *            The maximum number of rows to fetch.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria rows(final long maximumRowsFetched) {
		this.rowCount = maximumRowsFetched;
		return this;
	}

	/**
	 * Sets the number of rows to fetch to 1.
	 * 
	 * @see Criteria#rows(long)
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public Criteria unique() {
		this.rowCount = 1;
		return this;
	}

}
