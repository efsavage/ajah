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
package com.ajah.util.config;

/**
 * This is a property key that has been overloaded with a default value, so it
 * can be referenced more succinctly.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface PropertyKey {

	/**
	 * The name of the property.
	 * 
	 * @return The name of the property.
	 */
	String getName();

	/**
	 * The default value of the property.
	 * 
	 * @return The default value of the property.
	 */
	String getDefaultValue();

}