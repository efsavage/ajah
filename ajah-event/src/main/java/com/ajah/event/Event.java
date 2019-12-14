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
package com.ajah.event;

import com.ajah.util.Identifiable;

/**
 * An Event is a generic idea of something that happened that will be useful to
 * track. The intent is that specific event types will be built for specific
 * needs, such as an HTTP Request or a SQL query. This interface defines the
 * limited set of functionality that is common to all events.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The unique ID type of the event.
 * 
 */
public interface Event<K extends Comparable<K>> extends Identifiable<K> {

	/**
	 * Consider this event complete. If the event has children, they may remain
	 * active.
	 */
	void complete();

}
