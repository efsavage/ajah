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
package com.ajah.syndicate.data;

import lombok.EqualsAndHashCode;

import com.ajah.syndicate.EntryId;

/**
 * Thrown when a entry is requested that does not exist.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@EqualsAndHashCode(callSuper = false)
public class EntryNotFoundException extends Exception {

	private final EntryId entryId;

	/**
	 * Thrown when a entry is requested by an ID that does not exist.
	 * 
	 * @param entryId
	 *            Entry ID that was sought.
	 */
	public EntryNotFoundException(final EntryId entryId) {
		super("id: " + entryId + " not found");
		this.entryId = entryId;
	}

}
