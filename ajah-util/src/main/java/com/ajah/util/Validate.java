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
 * Contains validator methods for simple cases.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class Valid {

	/**
	 * Validates email against a simple pattern. Written against common usage,
	 * NOT against the RFC, which is too permissive to be practical.
	 * 
	 * @param email
	 * @return true if email is valid
	 */
	public static boolean email(String email) {
		return StringUtils.safeLength(email)>5 && email.matches("[A-Za-z0-9._%+-]+@([a-zA-Z0-9]+\\.)+[a-zA-Z]{2,4}");
	}

}