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
package com.ajah.user.login;

/**
 * LoginType is the way a user logged in. Common examples would be MANUAL,
 * COOKIE, or TOKEN.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface LoginType {

	/**
	 * The internal ID of the type.
	 * 
	 * @return The internal ID of the type. Cannot be null.
	 */
	String getId();

	/**
	 * The short, display-friendly code of the type. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the type. Cannot be null.
	 */
	String getCode();

	/**
	 * The display-friendly name of the type. If no name is applicable, it
	 * should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the type. Cannot be null.
	 */
	String getName();

	/**
	 * The display-friendly description of the type.
	 * 
	 * @return The display-friendly description of the type. May be null.
	 */
	String getDescription();

	/**
	 * Did the user manually log in? This can be useful to determined if
	 * authentication confirmation is needed for sensitive operations such as
	 * changing a password or finalizing a checkout.
	 * 
	 * Note: Saved-password mechanisms are not typically detectable, this method
	 * is more targeted as separating cookie/token-based logins from those
	 * prompting for a password.
	 * 
	 * @return True if this login was manual.
	 */
	boolean isManual();

}