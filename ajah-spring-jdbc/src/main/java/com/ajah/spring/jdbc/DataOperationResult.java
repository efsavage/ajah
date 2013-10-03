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
package com.ajah.spring.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A generic container for the result of a data operation.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <T>
 *            The entity class.
 */
@Data
@AllArgsConstructor
public class DataOperationResult<T> {

	/**
	 * The entity that was saved. If an entity was passed into the operation,
	 * this may or may not be the original entity, see implementation for
	 * details.
	 */
	T entity;

	/**
	 * The number of rows/entries affected, as determined by the storage
	 * mechanism.
	 */
	int rowsAffected;

}
