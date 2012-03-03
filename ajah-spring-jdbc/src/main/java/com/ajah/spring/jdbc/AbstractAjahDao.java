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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Limit;
import com.ajah.spring.jdbc.criteria.Where;
import com.ajah.spring.jdbc.util.JDBCMapperUtils;
import com.ajah.util.AjahUtils;
import com.ajah.util.CollectionUtils;
import com.ajah.util.Identifiable;
import com.ajah.util.ToStringable;
import com.ajah.util.data.Audited;
import com.ajah.util.reflect.IntrospectionUtils;
import com.ajah.util.reflect.ReflectionUtils;

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
public abstract class AbstractAjahDao<K extends Comparable<K>, T extends Identifiable<K>> implements AjahDao<K, T> {

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

	private Map<String, Field> colMap = new HashMap<>();

	private List<String> columns;

	protected JdbcTemplate jdbcTemplate;

	private String selectFields;

	private String selectFieldsWithTablePrefix;

	private String insertFields;

	private String updateFields;

	private List<String> updateFieldsList;

	private String insertPlaceholders;

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
	 * @see com.ajah.spring.jdbc.AjahDao#autoPopulate(Identifiable, ResultSet)
	 */
	@Override
	public void autoPopulate(T entity, ResultSet rs) throws SQLException {
		try {
			BeanInfo componentBeanInfo = Introspector.getBeanInfo(entity.getClass());
			PropertyDescriptor[] props = componentBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor prop : props) {
				log.finest("PropertyDescriptor: " + prop.getName() + ", Setter: " + (prop.getWriteMethod() == null ? null : prop.getWriteMethod().getName()) + " Getter: "
						+ (prop.getReadMethod() == null ? null : prop.getReadMethod().getName()));
			}
			for (String column : getColumns()) {
				Field field = this.colMap.get(column);
				if (rs.getObject(column) == null) {
					propSet(entity, getProp(field, props), null);
				} else if (IntrospectionUtils.isString(field)) {
					propSet(entity, getProp(field, props), rs.getString(column));
				} else if (IntrospectionUtils.isDate(field)) {
					propSet(entity, getProp(field, props), new Date(rs.getLong(column)));
				} else if (IntrospectionUtils.isFromStringable(field)) {
					propSet(entity, getProp(field, props), field.getType().getConstructor(String.class).newInstance(rs.getString(column)));
				} else if (IntrospectionUtils.isIdentifiableEnum(field)) {
					propSet(entity, getProp(field, props), ReflectionUtils.findEnumById(field, rs.getString(column)));
				} else if (IntrospectionUtils.isInt(field)) {
					propSet(entity, getProp(field, props), Integer.valueOf(rs.getInt(column)));
				} else if (IntrospectionUtils.isLong(field)) {
					propSet(entity, getProp(field, props), Long.valueOf(rs.getLong(column)));
				} else if (IntrospectionUtils.isBoolean(field)) {
					propSet(entity, getProp(field, props), Boolean.valueOf(rs.getBoolean(column)));
				} else if (IntrospectionUtils.isEnum(field)) {
					log.warning("Can't handle non-Identifiable enum for column " + column + " [" + field.getType() + "]");
				} else {
					log.warning("Can't handle auto-populating of column " + column + " of type " + field.getType());
				}
			}
		} catch (IntrospectionException e) {
			log.log(Level.SEVERE, entity.getClass().getName() + ": " + e.getMessage(), e);
		} catch (SecurityException e) {
			log.log(Level.SEVERE, entity.getClass().getName() + ": " + e.getMessage(), e);
		} catch (InstantiationException e) {
			log.log(Level.SEVERE, entity.getClass().getName() + ": " + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.log(Level.SEVERE, entity.getClass().getName() + ": " + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.log(Level.SEVERE, entity.getClass().getName() + ": " + e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.log(Level.SEVERE, entity.getClass().getName() + ": " + e.getMessage(), e);
		}
	}

	private static PropertyDescriptor getProp(Field field, PropertyDescriptor[] props) {
		for (PropertyDescriptor prop : props) {
			if (prop.getName().equals(field.getName())) {
				return prop;
			}
		}
		return null;
	}

	private void propSet(T entity, PropertyDescriptor prop, Object value) {
		try {
			Method setter = prop.getWriteMethod();
			if (setter == null) {
				throw new IllegalArgumentException("No setter found for " + prop.getName());
			}
			setter.invoke(entity, value);
		} catch (IllegalAccessException e) {
			log.log(Level.SEVERE, prop.getName() + ": " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			// TODO See if we're trying to set a null on a primitive
			log.log(Level.SEVERE, prop.getName() + ": " + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.log(Level.SEVERE, prop.getName() + ": " + e.getMessage(), e);
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
			return getJdbcTemplate().queryForObject("SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + field + " = ?", new Object[] { value }, getRowMapper());
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
			String sql = "SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + getFieldsClause(fields);
			if (log.isLoggable(Level.FINEST)) {
				log.finest(sql);
				for (Object value : values) {
					log.finest(value.toString());
				}
			}
			return getJdbcTemplate().queryForObject(sql, values, getRowMapper());
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
			return getJdbcTemplate().queryForObject("SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + getTableName() + "_id = ?", new Object[] { id.toString() }, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.finest(e.getMessage());
			return null;
		}
	}

	/**
	 * Finds a single object by the Criteria specified.
	 * 
	 * @param criteria
	 *            The criteria to use to find the object.
	 * @return The object, if found.
	 */
	public T find(Criteria criteria) {
		if (criteria.getLimit().getCount() > 1) {
			throw new IllegalArgumentException("Cannot use singular find method when criteria has a limit greater than 1 (" + criteria.getLimit().getCount() + ")");
		}
		criteria.rows(1);
		return find(criteria.getWhere(), criteria.getLimit());
	}

	/**
	 * Finds a single object by the Where and Limit specified.
	 * 
	 * @param where
	 *            The Object to create the WHERE statement.
	 * @param limit
	 *            The Object to create the LIMIT statement.
	 * @return The object, if found, otherwise null.
	 */
	public T find(Where where, Limit limit) {
		AjahUtils.requireParam(where, "where");
		if (limit != null && limit.getCount() > 1) {
			throw new IllegalArgumentException("Cannot use singular find method with a limit greater than 1 (" + limit.getCount() + ")");
		}
		try {
			String sql = "SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + where.getSql() + (limit == null ? " LIMIT 1" : " " + limit.getSql());
			log.finest(sql);
			return getJdbcTemplate().queryForObject(sql, where.getValues().toArray(), getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Find a collections of entities by their unique ID.
	 * 
	 * @param ids
	 *            Values to match against the entity.entity_id column, required.
	 * @return Entity if found, otherwise null.
	 */
	public List<T> findByIds(Collection<K> ids) {
		AjahUtils.requireParam(ids, "ids");
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE ");
			boolean first = true;
			String[] idArray = new String[ids.size()];
			int i = 0;
			for (K id : ids) {
				if (first) {
					first = false;
				} else {
					sql.append(" OR ");
				}
				sql.append(getTableName() + "_id = ?");
				idArray[i++] = id.toString();
			}
			if (log.isLoggable(Level.FINEST)) {
				log.finest(sql.toString());
				for (Object value : ids) {
					log.finest(value.toString());
				}
			}
			return getJdbcTemplate().query(sql.toString(), idArray, getRowMapper());
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

	private static String getFieldsClause(String[] fields) {
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
		return new SimpleAjahRowMapper<>(this);
	}

	/**
	 * @return The columns for use in SELECT statements for this class, may be
	 *         empty but will not be null.
	 */
	public String getSelectFields() {
		return getSelectFields(false);
	}

	/**
	 * @return The columns for use in SELECT statements for this class, may be
	 *         empty but will not be null.
	 */
	public String getSelectFields(boolean tablePrefix) {
		if (this.selectFields == null) {
			loadColumns();
		}
		return tablePrefix ? this.selectFieldsWithTablePrefix : this.selectFields;
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
	 * @return Entity if found, otherwise null.
	 */
	public List<T> listByField(String field, ToStringable value) {
		AjahUtils.requireParam(value, "value");
		return listByField(field, value.toString(), getTableName() + "_id", 0, Integer.MAX_VALUE);
	}

	/**
	 * Find a list of entities by non-unique match.
	 * 
	 * @param field
	 *            Column to match against, required.
	 * @param value
	 *            Value to match against the entity.field column, required.
	 * @return Entity if found, otherwise null.
	 */
	public List<T> listByField(String field, String value) {
		AjahUtils.requireParam(value, "value");
		return listByField(field, value, getTableName() + "_id", 0, Integer.MAX_VALUE);
	}

	/**
	 * Find a list of entities by non-unique match.
	 * 
	 * @param field
	 *            Column to match against, required.
	 * @param value
	 *            Value to match against the entity.field column, required.
	 * @param count
	 *            The maximum number of rows to fetch.
	 * @return Entity if found, otherwise null.
	 */
	public List<T> listByField(String field, String value, int count) {
		AjahUtils.requireParam(value, "value");
		return listByField(field, value, getTableName() + "_id", 0, count);
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
	public List<T> listByField(String field, ToStringable value, String orderBy, int page, int count) {
		return listByField(field, value.toString(), orderBy, page, count);
	}

	/**
	 * Find a list of entities by non-unique match.
	 * 
	 * @param field
	 *            Column to match against, required.
	 * @param value
	 *            Value to match against the entity.field column, required. If
	 *            matching on "IS NULL", set this parameter to "NULL".
	 * @param orderBy
	 * @param page
	 *            Page of results (offset).
	 * @param count
	 *            Number of results per page to return.
	 * @return Entity if found, otherwise null.
	 */
	public List<T> listByField(String field, String value, String orderBy, int page, int count) {
		AjahUtils.requireParam(field, "field");
		AjahUtils.requireParam(value, "value");
		try {
			if (value.equals("NULL")) {
				String sql = "SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + field + " IS NULL ORDER BY " + orderBy + " LIMIT " + (page * count) + "," + count;
				return CollectionUtils.nullIfEmpty(getJdbcTemplate().query(sql, getRowMapper()));
			}
			String sql = "SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + field + " = ? ORDER BY " + orderBy + " LIMIT " + (page * count) + "," + count;
			if (log.isLoggable(Level.FINEST)) {
				log.finest(sql);
				log.finest(value.toString());
			}
			return CollectionUtils.nullIfEmpty(getJdbcTemplate().query(sql, new Object[] { value }, getRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	/**
	 * Find a list of entities by non-unique match.
	 * 
	 * @param criteria
	 *            The criteria object to use to build the query.
	 * @return Entity if found, otherwise null.
	 * @since 1.0.1
	 */
	public List<T> list(Criteria criteria) {
		AjahUtils.requireParam(criteria, "criteria");
		try {
			String sql = "SELECT " + getSelectFields() + " FROM " + getTableName() + criteria.getWhere().getSql() + criteria.getOrderBySql() + criteria.getLimit().getSql();
			if (log.isLoggable(Level.FINEST)) {
				log.finest(sql);
				log.finest(criteria.getWhere().getValues().toString());
			}
			return CollectionUtils.nullIfEmpty(getJdbcTemplate().query(sql, criteria.getWhere().getValues().toArray(), getRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	private void loadColumns() {
		log.finest("Loading columns");
		if (this.tableName == null) {
			this.tableName = JDBCMapperUtils.getTableName(getTargetClass());
		}
		log.finest("Table set to : " + this.tableName);
		List<String> columnList = new ArrayList<>();
		List<String> newUpdateFields = new ArrayList<>();
		StringBuffer select = new StringBuffer();
		StringBuffer selectWithTablePrefix = new StringBuffer();
		log.finest(getTargetClass().getDeclaredFields().length + " declared fields for " + getTargetClass().getName());
		for (Field field : getTargetClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Transient.class)) {
				log.finest("Ignoring Transient field " + field.getName());
				continue;
			} else if (field.isAnnotationPresent(ManyToMany.class)) {
				log.finest("Ignoring ManyToMany field " + field.getName());
				continue;
			} else if (Collection.class.isAssignableFrom(field.getType())) {
				log.finest("Ignoring Collection field " + field.getName());
				continue;
			}
			String colName = JDBCMapperUtils.getColumnName(getTableName(), field);
			columnList.add(colName);
			this.colMap.put(colName, field);
			if (select.length() > 0) {
				select.append(", ");
			}
			if (selectWithTablePrefix.length() > 0) {
				selectWithTablePrefix.append(", ");
			}

			select.append(colName);
			selectWithTablePrefix.append(this.tableName + "." + colName);
			if (!field.getName().equals("id")) {
				newUpdateFields.add(colName);
			}

			if (field.isAnnotationPresent(Audited.class)) {
				log.finest(field.getName() + " is audited");
				// TODO Audit it!
			}
		}
		if (this.selectFields == null) {
			this.selectFields = select.toString();
			this.selectFieldsWithTablePrefix = selectWithTablePrefix.toString();
			this.insertFields = this.selectFields;
		}
		if (this.columns == null) {
			this.columns = columnList;
		}
		log.finest(this.columns.size() + " columns");

		if (this.updateFields == null) {
			StringBuffer uf = new StringBuffer();
			for (String field : newUpdateFields) {
				if (uf.length() > 0) {
					uf.append(",");
				}
				uf.append(field);
				uf.append("=?");
			}
			this.updateFields = uf.toString();
			this.updateFieldsList = newUpdateFields;
		}

		StringBuffer iph = new StringBuffer();
		for (int i = 0; i < this.columns.size(); i++) {
			if (i > 0) {
				iph.append(",");
			}
			iph.append("?");
		}
		this.insertPlaceholders = iph.toString();

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
	 * Sets the table name. This will override any auto-discovered settings.
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	protected int now() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	protected long count(String sql) {
		try {
			return getJdbcTemplate().queryForInt(sql);
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return 0;
		}
	}

	protected long maxLong(String field, Criteria criteria) {
		try {
			String sql = "SELECT MAX(" + field + ") FROM " + getTableName() + criteria.getWhere().getSql();
			return getJdbcTemplate().queryForLong(sql, criteria.getWhere().getValues());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return 0;
		}
	}

	protected int maxInt(String field, Criteria criteria) {
		try {
			String sql = "SELECT MAX(" + field + ") FROM " + getTableName() + criteria.getWhere().getSql();
			return getJdbcTemplate().queryForInt(sql, criteria.getWhere().getValues());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return 0;
		}
	}

	protected long minLong(String field, Criteria criteria) {
		try {
			String sql = "SELECT MIN(" + field + ") FROM " + getTableName() + criteria.getWhere().getSql();
			return getJdbcTemplate().queryForLong(sql, criteria.getWhere().getValues());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return 0;
		}
	}

	protected int minInt(String field, Criteria criteria) {
		try {
			String sql = "SELECT MIN(" + field + ") FROM " + getTableName() + criteria.getWhere().getSql();
			return getJdbcTemplate().queryForInt(sql, criteria.getWhere().getValues());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return 0;
		}
	}

	/**
	 * Find an entity by the supplied WHERE clause.
	 * 
	 * @param where
	 *            The WHERE clause to include in the query.
	 * @return Entity if found, otherwise null.
	 */
	public T findByWhere(String where) {
		AjahUtils.requireParam(where, "where");
		try {
			String sql = "SELECT " + getSelectFields() + " FROM " + getTableName() + " WHERE " + where + " LIMIT 1";
			log.finest(sql);
			return getJdbcTemplate().queryForObject(sql, null, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Inserts the record. May throw an error on duplicate key exceptions.
	 * 
	 * @param entity
	 *            Entity to insert into the table.
	 * @return Number of rows inserted.
	 * @throws DatabaseAccessException
	 *             If an error occurs executing the query.
	 */
	public int insert(T entity) throws DatabaseAccessException {
		return insert(entity, false);
	}

	/**
	 * Inserts the record. May throw an error on duplicate key exceptions.
	 * 
	 * @param entity
	 *            Entity to insert into the table.
	 * @param delayed
	 *            Use a DELAYED insert.
	 * @return Number of rows inserted.
	 * @throws DatabaseAccessException
	 *             If an error occurs executing the query.
	 */
	public int insert(T entity, boolean delayed) throws DatabaseAccessException {
		AjahUtils.requireParam(entity, "entity");
		AjahUtils.requireParam(entity.getId(), "entity.id");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		try {
			String sql = "INSERT " + (delayed ? "DELAYED " : "") + "INTO " + getTableName() + "(" + getInsertFields() + ") VALUES (" + getInsertPlaceholders() + ")";
			if (log.isLoggable(Level.FINEST)) {
				log.finest(sql);
			}
			return this.jdbcTemplate.update(sql, getInsertValues(entity));
		} catch (DataAccessException e) {
			throw new DatabaseAccessException(e);
		}
	}

	/**
	 * Updates the record. Will not do anything if there are no matching
	 * records.
	 * 
	 * @param entity
	 *            Entity to update.
	 * @return Number of rows affected.
	 * @throws DatabaseAccessException
	 *             If an error occurs executing the query.
	 */
	public int update(T entity) throws DatabaseAccessException {
		AjahUtils.requireParam(entity, "entity");
		AjahUtils.requireParam(entity.getId(), "entity.id");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		try {
			String sql = "UPDATE " + getTableName() + " SET " + getUpdateFields() + " WHERE " + getTableName() + "_id = ?";
			if (log.isLoggable(Level.FINEST)) {
				log.finest(sql);
			}
			return this.jdbcTemplate.update(sql, getUpdateValues(entity));
		} catch (DataAccessException e) {
			throw new DatabaseAccessException(e);
		}
	}

	/**
	 * Increments the field of the record by a certain amount.
	 * 
	 * @param entity
	 *            Entity to update.
	 * @param field
	 *            The field to increase.
	 * @param amount
	 *            The amount to increase the field by.
	 * @return Number of rows affected.
	 * @throws DatabaseAccessException
	 *             If an error occurs executing the query.
	 */
	public int increment(T entity, String field, int amount) throws DatabaseAccessException {
		AjahUtils.requireParam(entity, "entity");
		AjahUtils.requireParam(entity.getId(), "entity.id");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		try {
			String sql = "UPDATE " + getTableName() + " SET " + field + "=" + field + " + " + amount + " WHERE " + getTableName() + "_id = ?";
			if (log.isLoggable(Level.FINEST)) {
				log.finest(sql);
			}
			return this.jdbcTemplate.update(sql, entity.getId().toString());
		} catch (DataAccessException e) {
			throw new DatabaseAccessException(e);
		}
	}

	/**
	 * Increments the field of the record by 1.
	 * 
	 * @param entity
	 *            Entity to update.
	 * @param field
	 *            The field to increase.
	 * @return Number of rows affected.
	 * @throws DatabaseAccessException
	 *             If an error occurs executing the query.
	 */
	public int increment(T entity, String field) throws DatabaseAccessException {
		return increment(entity, field, 1);
	}

	/**
	 * Decrements the field of the record by 1.
	 * 
	 * @param entity
	 *            Entity to update.
	 * @param field
	 *            The field to decrease.
	 * @return Number of rows affected.
	 * @throws DatabaseAccessException
	 *             If an error occurs executing the query.
	 */
	public int decrement(T entity, String field) throws DatabaseAccessException {
		return increment(entity, field, -1);
	}

	private Object[] getInsertValues(T entity) {
		Object[] values = new Object[getColumns().size()];
		try {
			BeanInfo componentBeanInfo = Introspector.getBeanInfo(entity.getClass());
			PropertyDescriptor[] props = componentBeanInfo.getPropertyDescriptors();
			for (int i = 0; i < values.length; i++) {
				Field field = this.colMap.get(this.columns.get(i));
				values[i] = ReflectionUtils.propGetSafeAuto(entity, field, getProp(field, props));
				if (log.isLoggable(Level.FINEST)) {
					log.finest(field.getName() + " set to " + values[i]);
				}
			}
		} catch (IntrospectionException e) {
			log.log(Level.SEVERE, entity.getClass().getName() + ": " + e.getMessage(), e);
		}
		return values;
	}

	private Object[] getUpdateValues(T entity) {
		Object[] values = new Object[getUpdateFieldsList().size() + 1];
		try {
			BeanInfo componentBeanInfo = Introspector.getBeanInfo(entity.getClass());
			PropertyDescriptor[] props = componentBeanInfo.getPropertyDescriptors();
			for (int i = 0; i < (values.length - 1); i++) {
				Field field = this.colMap.get(this.updateFieldsList.get(i));
				values[i] = ReflectionUtils.propGetSafeAuto(entity, field, getProp(field, props));
			}
			values[values.length - 1] = entity.getId().toString();
		} catch (IntrospectionException e) {
			log.log(Level.SEVERE, entity.getClass().getName() + ": " + e.getMessage(), e);
		}
		return values;
	}

	private String getInsertPlaceholders() {
		if (this.insertPlaceholders == null) {
			loadColumns();
		}
		return this.insertPlaceholders;
	}

	private String getInsertFields() {
		if (this.insertFields == null) {
			loadColumns();
		}
		return this.insertFields;
	}

	private String getUpdateFields() {
		if (this.updateFields == null) {
			loadColumns();
		}
		return this.updateFields;
	}

	private List<String> getUpdateFieldsList() {
		if (this.updateFieldsList == null) {
			loadColumns();
		}
		return this.updateFieldsList;
	}

	/**
	 * Deletes an entity by unique ID.
	 * 
	 * @param id
	 *            Value to match against the entity.entity_id column, required.
	 * @return Entity if found, otherwise null.
	 * @throws DatabaseAccessException
	 *             If the query could not be executed.
	 */
	public int deleteById(K id) throws DatabaseAccessException {
		AjahUtils.requireParam(id, "id");
		try {
			return getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE " + getTableName() + "_id = ?", new Object[] { id.toString() });
		} catch (DataAccessException e) {
			throw new DatabaseAccessException(e);
		}

	}

}
