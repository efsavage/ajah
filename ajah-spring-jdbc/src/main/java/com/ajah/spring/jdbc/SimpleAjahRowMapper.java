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

import com.ajah.util.Identifiable;

/**
 * Simple version of DAO that will use only default behaviors.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The type of the unique key of the entity this DAO manages.
 * @param <T>
 *            The type of entity managed.
 * 
 */
public class SimpleAjahRowMapper<K extends Comparable<K>, T extends Identifiable<K>> extends AbstractAjahRowMapper<K, T> {

	/**
	 * Sets the calling DAO.
	 * 
	 * @param dao
	 *            The calling DAO.
	 */
	protected SimpleAjahRowMapper(final AjahDao<K, T> dao) {
		super(dao);
	}

}
