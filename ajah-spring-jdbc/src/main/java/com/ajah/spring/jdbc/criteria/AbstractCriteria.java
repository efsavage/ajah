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

package com.ajah.spring.jdbc.criteria;

import java.util.ArrayList;
import java.util.List;

import com.ajah.util.AjahUtils;
import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;
import com.ajah.util.ToStringable;
import com.ajah.util.lang.NameValuePair;

/**
 * Base class for {@link Criteria}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <C>
 *            The implemented type, for method chaining.
 * 
 */
public abstract class AbstractCriteria<C> {

	protected List<NameValuePair<String>> eqs = null;

	/**
	 * A field match.
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public C eq(final String field, final boolean value) {
		// TODO This should probably be 0 or !0.
		AjahUtils.requireParam(field, "field");
		return eq(field, value ? 1 : 0);
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
	public C eq(final String field, final Identifiable<?> value) {
		AjahUtils.requireParam(field, "field");
		if (value == null) {
			return eq(field, (String) null);
		}
		return eq(field, value.getId().toString());
	}

	/**
	 * A field match.
	 * 
	 * @param field
	 *            The field to match
	 * @param value
	 *            The value the field must be.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public C eq(final String field, final long value) {
		AjahUtils.requireParam(field, "field");
		return eq(field, String.valueOf(value));
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
	public C eq(final String field, final String value) {
		AjahUtils.requireParam(field, "field");
		if (this.eqs == null) {
			this.eqs = new ArrayList<>();
		}
		this.eqs.add(new NameValuePair<>(field, value));
		return getThis();
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
	public C eq(final String field, final ToStringable value) {
		AjahUtils.requireParam(field, "field");
		if (value == null) {
			return eq(field, (String) null);
		}
		return eq(field, value.toString());
	}

	/**
	 * A field match with an inferred name. Primarily used for Id classes so
	 * passing a UserId of value '123' would call {@link #eq(String, String)}
	 * with a field of 'user_id' and a value of '123'.
	 * 
	 * @param value
	 *            The value the field must be.
	 * @return Criteria instance the method was invoked on (for chaining).
	 */
	public C eq(final ToStringable value) {
		AjahUtils.requireParam(value, "value");
		return eq(StringUtils.splitCamelCase(value.getClass().getSimpleName()).replaceAll("\\W+", "_").toLowerCase(), value.toString());
	}

	protected abstract C getThis();

}
