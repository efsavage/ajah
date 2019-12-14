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

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ajah.util.Identifiable;

/**
 * @param <K>
 * 		The type of the unique key of the entity this DAO manages.
 * @param <T>
 * 		The type of entity this DAO manages.
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 * href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public abstract class AbstractAjahRowMapper<K extends Comparable<K>, T extends Identifiable<K>> implements RowMapper<T> {

	private AjahDao<K, T> dao;

	protected AbstractAjahRowMapper(final AjahDao<K, T> dao) {
		this.dao = dao;
	}

	/**
	 * The DAO that will be calling this mapper.
	 *
	 * @return The DAO that will be calling this mapper.
	 */
	public AjahDao<K, T> getDao() {
		return this.dao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		T entity;
		try {
			entity = this.dao.getTargetClass().getConstructor().newInstance();
			this.dao.autoPopulate(entity, rs);
			return entity;
		} catch (final InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * The DAO that will be calling this mapper.
	 *
	 * @param dao
	 * 		The DAO that will be calling this mapper.
	 */
	public void setDao(final AjahDao<K, T> dao) {
		this.dao = dao;
	}

}
