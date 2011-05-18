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
package com.ajah.util;

/**
 * 
 * This marker interface means that the object's has a getId() method, useful
 * for persitence mechanisms.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            ID type.
 * 
 */
public interface Identifiable<K> {

	/**
	 * Returns the unique Identifier for the entity.
	 * 
	 * @return The unique Identifier for the entity.
	 */
	public K getId();

	/**
	 * This is a placeholder for now, until I figure out how to use java
	 * reflection without needing it, as Java seems to hide this method if the
	 * class implements this interface, even when it has a setId method.
	 * 
	 * @param id
	 *            The ID to set.
	 */
	public void setId(K id);

}