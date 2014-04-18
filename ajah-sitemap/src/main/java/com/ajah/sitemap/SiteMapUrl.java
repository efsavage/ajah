/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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

package com.ajah.sitemap;

import java.util.Date;

import lombok.Data;

/**
 * A link within a {@link SiteMap}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class SiteMapUrl {

	/**
	 * The URL.
	 */
	private String loc;
	/**
	 * The date the page was last changed.
	 */
	private Date lastMod;
	/**
	 * The freuquency with which this page usually changes.
	 */
	private Frequency changeFrequency;
	/**
	 * The importance within the context of the site of this page.
	 */
	private Double priority;

}
