/*
 *  Copyright 2011-2013 Eric F. Savage, code@efsavage.com
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

import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.util.Identifiable;

/**
 * Essential methods for using Ajah Spring JDBC library.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The type of Key the Entity uses for a unique ID. *
 * @param <T>
 *            The type of Entity managed by the DAO.
 */
public interface AjahDao<K extends Comparable<K>, T extends Identifiable<K>> {

	/**
	 * Returns the class this DAO manages.
	 * 
	 * @return The class of the managed entity.
	 */
	Class<? extends T> getTargetClass();

	/**
	 * Returns the ID class this DAO manages.
	 * 
	 * @return The class of the managed entity's ID.
	 */
	Class<K> getIdClass();

	/**
	 * Automatically fill in properties from the result set.
	 * 
	 * @param entity
	 *            The entity to populate fields on.
	 * @param resultSet
	 *            The result set to pull data from.
	 * @throws SQLException
	 */
	void autoPopulate(final T entity, final ResultSet resultSet) throws SQLException;

	/**
	 * Finds a record by it's unique ID.
	 * 
	 * @param id
	 *            The unique ID of the record.
	 * @return The matching record, or null.
	 * @throws DataOperationException
	 *             if the query could not be executed.
	 */
	T load(final K id) throws DataOperationException;

	/**
	 * Inserts a record.
	 * 
	 * @param entity
	 *            The entity to insert as a record.
	 * @return The number of rows inserted.
	 * @throws DataOperationException
	 *             if the query could not be executed.
	 */
	DataOperationResult<T> insert(final T entity) throws DataOperationException;

	/**
	 * Inserts a record.
	 * 
	 * @param entity
	 *            The entity to update.
	 * @return The number of rows updated.
	 * @throws DataOperationException
	 *             if the query could not be executed.
	 */
	DataOperationResult<T> update(final T entity) throws DataOperationException;

	/**
	 * Deletes a record, or marks it as deleted.
	 * 
	 * @param entity
	 *            The entity to delete.
	 * @return The number of rows deleted or marked as deleted.
	 * @throws DataOperationException
	 *             if the query could not be executed.
	 */
	DataOperationResult<T> delete(final T entity) throws DataOperationException;

}
