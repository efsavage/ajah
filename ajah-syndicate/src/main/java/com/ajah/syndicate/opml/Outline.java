/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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
package com.ajah.syndicate.opml;

import java.util.List;

import lombok.Data;

/**
 * An OPML outline element.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class Outline {

	List<Outline> outlines;
	String text;
	String title;
	String xmlUrl;
	String htmlUrl;
	String type;
	Outline parent;

	/**
	 * The depth of this outline. Root-level outlines will return 0.
	 * 
	 * @return The depth of this outline. Root-level outlines will return 0.
	 */
	public int getDepth() {
		if (this.parent == null) {
			return 0;
		}
		return this.parent.getDepth() + 1;
	}

}
