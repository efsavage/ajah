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
package com.ajah.spring.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Transient;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ajah.spring.jdbc.util.JDBCMapperUtils;
import com.ajah.util.AjahUtils;

/**
 * This is a basic DAO object.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The primary key class. Note that {@link Object#toString()} will be
 *            invoked on this object.
 * @param <T>
 *            The type of entity this DAO exists for.
 * 
 */
public abstract class AbstractAjahDao<K, T> implements AjahDao<T> {

	private static final Logger log = Logger.getLogger(AbstractAjahDao.class.getName());

	/**
	 * This method will return a Long, functioning like getLong, but with the
	 * ability to recognize null values, instead of converting them to zero.
	 * 
	 * @see ResultSet#getLong(String)
	 * @see ResultSet#getObject(String)
	 * @param rs
	 *            The ResultSet to look in.
	 * @param field
	 *            The field name to look for.
	 * @return The Long value of the field, may be null.
	 * @throws SQLException
	 *             If thrown by ResultSet.
	 */
	protected static Long getLong(ResultSet rs, String field) throws SQLException {
		if (rs.getObject(field) == null) {
			return null;
		}
		return Long.valueOf(rs.getLong(field));
	}

	/**
	 * Converts a Java Date into a unix-compatible timestamp (seconds
	 * precision).
	 * 
	 * @param date
	 *            Date to convert, may be null.
	 * @return Value of Date in seconds, will return null if null is passed in.
	 */
	protected static Long toUnix(Date date) {
		if (date == null) {
			return null;
		}
		return Long.valueOf(date.getTime() / 1000);
	}

	private Map<String, Field> colMap = new HashMap<String, Field>();

	private List<String> columns;

	protected JdbcTemplate jdbcTemplate;

	private String selectFields;

	private String tableName;

	/**
	 * Will automatically fill in properties from the result set. Currently
	 * supports:
	 * 
	 * <ul>
	 * <li>{@link String}</li>
	 * <ul>
	 * 
	 * @throws SQLException
	 *             If the {@link ResultSet} throws it.
	 * 
	 * @see com.ajah.spring.jdbc.AjahDao#autoPopulate(java.lang.Object,
	 *      java.sql.ResultSet)
	 */
	@Override
	public void autoPopulate(T entity, ResultSet rs) throws SQLException {
		for (String column : getColumns()) {
			try {
				Field field = this.colMap.get(column);
				if (field.getType().isAssignableFrom(String.class)) {
					field.set(entity, rs.getString(column));
				}
			} catch (IllegalArgumentException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			} catch (IllegalAccessException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	/**
	 * Find an entity by unique ID.
	 * 
	 * @param field
	 *            Column to match against, required.
	 * @param value
	 *            Value to match against the entity.field column, required.
	 * @return Entity if found, otherwise null.
	 */
	public T findByField(String field, Object value) {
		AjahUtils.requireParam(field, "field");
		AjahUtils.requireParam(value, "value");
		try {
			return getJdbcTemplate().queryForObject("SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + field + " = ?",
					new Object[] { value }, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Find an entity by unique ID.
	 * 
	 * @param fields
	 *            Columns to match against, required.
	 * @param values
	 *            Values to match against the entity.field column, required.
	 * @return Entity if found, otherwise null.
	 */
	public T findByFields(String[] fields, Object[] values) {
		AjahUtils.requireParam(fields, "fields");
		AjahUtils.requireParam(values, "values");
		try {
			// TODO Optimize for single values
			return getJdbcTemplate().queryForObject("SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + getFieldsClause(fields),
					values, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	/**
	 * Find an entity by unique ID.
	 * 
	 * @param id
	 *            Value to match against the entity.entity_id column, required.
	 * @return Entity if found, otherwise null.
	 */
	public T findById(K id) {
		AjahUtils.requireParam(id, "id");
		try {
			return getJdbcTemplate().queryForObject(
					"SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + getTableName() + "_id = ?",
					new Object[] { id.toString() }, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	/**
	 * Returns the list of columns for this class.
	 * 
	 * @return The list of columns for this class, may be empty but will not be
	 *         null.
	 */
	public List<String> getColumns() {
		if (this.columns == null) {
			loadColumns();
		}
		return this.columns;
	}

	/**
	 * @param fields
	 * @return
	 */
	private String getFieldsClause(String[] fields) {
		StringBuffer stringBuffer = new StringBuffer();
		boolean first = true;
		for (String field : fields) {
			if (first) {
				first = false;
			} else {
				stringBuffer.append("AND ");
			}
			stringBuffer.append(field);
			stringBuffer.append("=? ");
		}
		return stringBuffer.toString();
	}

	/**
	 * Returns the Spring JDBC template.
	 * 
	 * @return The Spring JDBC template, may be null.
	 */
	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	protected RowMapper<T> getRowMapper() {
		return new SimpleAjahRowMapper<T>(this);
	}

	/**
	 * @return The columns for use in SELECT statements for this class, may be
	 *         empty but will not be null.
	 */
	public String getSelectFields() {
		if (this.selectFields == null) {
			loadColumns();
		}
		return this.selectFields;
	}

	/**
	 * Returns the table name for this class.
	 * 
	 * @return The table name for this class, may be null.
	 */
	public String getTableName() {
		if (this.tableName == null) {
			loadColumns();
		}
		return this.tableName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Class<T> getTargetClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	/**
	 * Find a list of entities by non-unique match.
	 * 
	 * @param field
	 *            Column to match against, required.
	 * @param value
	 *            Value to match against the entity.field column, required.
	 * @param orderBy
	 * @param page
	 *            Page of results (offset).
	 * @param count
	 *            Number of results per page to return.
	 * @return Entity if found, otherwise null.
	 */
	public List<T> listByField(String field, Object value, String orderBy, int page, int count) {
		log.fine("listByField");
		AjahUtils.requireParam(field, "field");
		AjahUtils.requireParam(value, "value");
		try {
			return getJdbcTemplate().query(
					"SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + field + " = ? ORDER BY " + orderBy + " LIMIT "
							+ (page * count) + "," + count, new Object[] { value }, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	private void loadColumns() {
		if (this.tableName == null) {
			this.tableName = JDBCMapperUtils.getTableName(getTargetClass());
		}
		List<String> columnList = new ArrayList<String>();
		StringBuffer select = new StringBuffer();
		for (Field field : getTargetClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			String colName = JDBCMapperUtils.getColumnName(this.tableName, field);
			columnList.add(colName);
			this.colMap.put(colName, field);
			if (select.length() > 0) {
				select.append(", ");
			}
			select.append(colName);
		}
		if (this.selectFields == null) {
			this.selectFields = select.toString();
		}
		if (this.columns == null) {
			this.columns = columnList;
		}
	}

	/**
	 * Sets up a new JDBC template with the supplied data source.
	 * 
	 * @param dataSource
	 *            DataSource to use for a new JDBC template.
	 */
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Sets the Spring JDBC Template.
	 * 
	 * @param jdbcTemplate
	 *            The Spring JDBC Template.
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Sets the table name. This will override any auto-discovered settings.
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}