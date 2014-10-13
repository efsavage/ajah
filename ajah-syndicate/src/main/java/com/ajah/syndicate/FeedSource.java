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
import com.ajah.util.data.HashUtils;

/**
 * A feed source is something that create feeds. This could be a website, blog,
 * etc.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class FeedSource implements Identifiable<FeedSourceId> {

	private FeedSourceId id;
	private String title;
	private String author;
	private String htmlUrl;
	private String htmlUrlSha1;
	private String feedUrl;
	private String feedUrlSha1;
	private String description;
	private PollStatus pollStatus;
	private Date pollStatusSince;
	private FeedSourceType type;
	private FeedSourceStatus status;
	private Date nextPoll;
	private Date created;
	private Date modified;
	// Frequency in minutes
	private int fetchFrequency;

	/**
	 * Returns the SHA-1 of the feedUrl field, if it is available.
	 * 
	 * @return The SHA-1 of the feedUrl field, or null if feedUrl is null.
	 */
	public String getFeedUrlSha1() {
		if (this.feedUrlSha1 == null && this.feedUrl != null) {
			this.feedUrlSha1 = HashUtils.sha1Hex(this.feedUrl);
		}
		return this.feedUrlSha1;
	}

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
