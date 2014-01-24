/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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

import com.ajah.util.AjahUtils;
import com.ajah.util.CollectionUtils;
import com.ajah.util.lang.NameValuePair;

/**
 * A subclause of a {@link Criteria}. Supports ORs. Only supports WHERE
 * expressions.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class SubCriteria {

	private List<NameValuePair<String>> orLikes = null;
	private List<NameValuePair<Number>> gts = null;
	private List<SubCriteria> ands = null;

	/**
	 * A subclause, included as an AND, but may contain ORs.
	 * 
	 * @param subCriteria
	 *            The subCriteria to include
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public SubCriteria and(SubCriteria subCriteria) {
		AjahUtils.requireParam(subCriteria, "field");
		if (this.ands == null) {
			this.ands = new ArrayList<>();
		}
		this.ands.add(subCriteria);
		return this;
	}

	/**
	 * A LIKE field match included as an OR. Uses the query as-is (i.e. add your
	 * own wildcards).
	 * 
	 * @param field
	 *            The field to match
	 * @param pattern
	 *            The pattern the field must match.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public SubCriteria orLike(final String field, final String pattern) {
		AjahUtils.requireParam(field, "field");
		AjahUtils.requireParam(pattern, "pattern");
		if (this.orLikes == null) {
			this.orLikes = new ArrayList<>();
		}
		this.orLikes.add(new NameValuePair<>(field, pattern));
		return this;
	}

	/**
	 * Constructs a {@link Where} object from this instance, suitable for
	 * creating a prepared SQL statement. Note that this does not include the
	 * WHERE token.
	 * 
	 * @return A where clause that is equivalent to this criteria.
	 */
	public Where getWhere() {
		final List<String> values = new ArrayList<>();
		final StringBuilder where = new StringBuilder();
		boolean first = true;
		if (!CollectionUtils.isEmpty(this.orLikes)) {
			where.append("(");
			for (final NameValuePair<String> orLike : this.orLikes) {
				if (first) {
					first = false;
				} else {
					where.append(" OR ");
				}
				where.append("`");
				where.append(orLike.getName());
				where.append("`");
				where.append(" LIKE '");
				where.append(orLike.getValue());
				where.append("'");
			}
			where.append(")");
		}
		if (!CollectionUtils.isEmpty(this.ands)) {
			for (final SubCriteria and : this.ands) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append(and.getWhere().toString());
			}
		}
		if (!CollectionUtils.isEmpty(this.gts)) {
			for (final NameValuePair<Number> gt : this.gts) {
				if (first) {
					first = false;
				} else {
					where.append(" AND ");
				}
				where.append("`");
				where.append(gt.getName());
				where.append("`");
				where.append(" > ");
				where.append(gt.getValue());
			}
		}
		return new Where(where.toString(), values);

	}

	public SubCriteria gt(String field, Number value) {
		AjahUtils.requireParam(field, "field");
		AjahUtils.requireParam(value, "value");
		if (this.gts == null) {
			this.gts = new ArrayList<>();
		}
		this.gts.add(new NameValuePair<>(field, value));
		return this;
	}
}