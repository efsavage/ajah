/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.user;

/**
 * Interface that can be used when storing UserSetting names in an enum.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface UserSettingKey {

	/**
	 * Return the name of this key, the value by which it is stored.
	 * 
	 * @return The name of this key, the value by which it is stored.
	 */
	String getName();

}
