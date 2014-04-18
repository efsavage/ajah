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

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Represents a sitemap file. May be used standalone as referenced by a
 * {@link SiteMapIndex}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class SiteMap {

	private List<SiteMapUrl> siteMapUrls = new ArrayList<>();

	private String locationRoot;
	private String filename;

	/**
	 * Adds a url to this sitemap.
	 * 
	 * @param siteMapUrl
	 */
	public void add(SiteMapUrl siteMapUrl) {
		this.siteMapUrls.add(siteMapUrl);
	}

}
