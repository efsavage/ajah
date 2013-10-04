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
package com.ajah.util;

/**
 * This marker interface means that the object's has a getId() method, useful
 * for persitence mechanisms.
 * 
 * This version adds some enum fields to the standard {@link Identifiable}
 * interface.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            ID type.
 */
public interface IdentifiableEnum<K extends Comparable<K>> extends Identifiable<K> {

	/**
	 * @see Enum#name()
	 * @return The enum's name as declared in code.
	 */
	String name();

	/**
	 * Returns the name property of the enum. Value is not necessarily the same
	 * as {@link #name()}
	 * 
	 * @return The name property of the enum.
	 */
	String getName();

	/**
	 * A code/shorthand for the enum.
	 * 
	 * @return The code property of the enum.
	 */
	String getCode();

}
