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
package test.ajah.syndicate;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;

import com.ajah.syndicate.Entry;
import com.ajah.syndicate.Feed;
import com.ajah.syndicate.FeedSource;
import com.ajah.syndicate.rome.RomeUtils;
import com.ajah.util.net.HttpClient;
import com.sun.syndication.io.FeedException;

/**
 * Test basic feed operations.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class SimpleFeedTest {

	private static final Logger log = Logger.getLogger(SimpleFeedTest.class.getName());

	private static String TEST_FEED = "http://efsavage.com/blog/feed/";

	/**
	 * Test getting a feed.
	 * 
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 * @throws IOException
	 */
	@Test
	public void getFeed() throws IllegalArgumentException, FeedException, IOException {
		FeedSource feedSource = new FeedSource();
		Feed feed = RomeUtils.createFeed(HttpClient.getXml(TEST_FEED), feedSource);
		log.info(feed.getEntries().size() + " entries");
		for (Entry entry : feed.getEntries()) {
			log.info("Title: " + entry.getTitle());
			log.info("Author: " + entry.getAuthor());
			log.info("Content: " + entry.getContent() + "\n");
		}
	}

}
