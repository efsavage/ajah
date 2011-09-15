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

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ajah.util.Identifiable;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The type of entity this DAO manages.
 * 
 */
public abstract class AbstractAjahRowMapper<K extends Comparable<K>, T extends Identifiable<K>> implements RowMapper<T> {

	protected AbstractAjahRowMapper(AjahDao<K, T> dao) {
		this.dao = dao;
	}

	private AjahDao<K, T> dao;

	/**
	 * The DAO that will be calling this mapper.
	 * 
	 * @return The DAO that will be calling this mapper.
	 * 
	 */
	public AjahDao<K, T> getDao() {
		return this.dao;
	}

	/**
	 * The DAO that will be calling this mapper.
	 * 
	 * @param dao
	 *            The DAO that will be calling this mapper.
	 * 
	 */
	public void setDao(AjahDao<K, T> dao) {
		this.dao = dao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T entity;
		try {
			entity = this.dao.getTargetClass().newInstance();
			this.dao.autoPopulate(entity, rs);
			return entity;
		} catch (InstantiationException e) {
			throw new SQLException(e);
		} catch (IllegalAccessException e) {
			throw new SQLException(e);
		}
	}

}