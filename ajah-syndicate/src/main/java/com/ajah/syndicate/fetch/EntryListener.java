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
package com.ajah.syndicate.fetch;

import com.ajah.syndicate.Entry;

/**
 * A listener that is fired when a new or updated entry is found.
 * 
 * Note that while "new or updated" is the goal, it is likely that entries may
 * be submitted multiple times without any important changes. It is the
 * responsibility of the listener to deduplicate if this is important.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public interface EntryListener {

	/**
	 * The method invoked. Note that this method is not necessarily
	 * asynchronous, any queuing should be handled by the implementing clas.
	 * 
	 * @param entry
	 *            The new or update entry.
	 */
	void handle(final Entry entry);

}
