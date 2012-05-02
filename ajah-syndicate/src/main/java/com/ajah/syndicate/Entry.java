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
package com.ajah.syndicate;

import java.util.Date;

import lombok.Data;

import com.ajah.util.Identifiable;
import com.ajah.util.data.Audited;
import com.ajah.util.data.HashUtils;
import com.ajah.util.net.AjahMimeType;

/**
 * An Entry is an "item" in a {@link Feed}. The most common example would be a
 * blog post.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@Audited
public class Entry implements Identifiable<EntryId> {

	private EntryId id;
	private String title;
	private String author;
	private String htmlUrl;
	private String htmlUrlSha1;
	private Date published;
	private Date created;
	private Date updated;
	private String content;
	private AjahMimeType contentType;
	private String description;
	private FeedId feedId;
	private FeedSourceId feedSourceId;

	/**
	 * Returns the SHA-1 of the htmlUrl field, if it is available.
	 * 
	 * @return The SHA-1 of the htmlUrl field, or null if htmlUrl is null.
	 */
	public String getHtmlUrlSha1() {
		if (this.htmlUrlSha1 == null && this.htmlUrl != null) {
			this.htmlUrlSha1 = HashUtils.sha1Hex(this.htmlUrl);
		}
		return this.htmlUrlSha1;
	}

}
