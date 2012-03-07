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
package com.ajah.user.account;

/**
 * An Account corresponds to a billing or non-person entity.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface Account {

	/**
	 * Unique ID of the account.
	 * 
	 * @return Unique ID of the account, may be null.
	 */
	AccountId getId();

	/**
	 * The status of the account.
	 * 
	 * @return The status of the account. May be null if not saved/complete.
	 */
	AccountStatus getStatus();

	/**
	 * The type of the account.
	 * 
	 * @return The type of the account. May be null if not saved/complete.
	 */
	AccountType getType();

}
