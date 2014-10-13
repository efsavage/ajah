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
import java.util.List;

import lombok.Data;

import com.ajah.util.Identifiable;

/**
 * A feed is an instance/delivery of a feed source. For example, each time a
 * blog's RSS feed is pulled, it is a new Feed, from the same {@link FeedSource}
 * .
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class Feed implements Identifiable<FeedId> {

	private FeedId id;
	private String title;
	private String author;
	private String link;
	private String url;
	private List<FeedEntry> entries;
	private Date created;
	private Date published;
	private String sha1;
	private FeedSourceId feedSourceId;

}
