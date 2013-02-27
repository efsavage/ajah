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
package test.ajah.elasticsearch;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ajah.elasticsearch.ElasticSearchNodeClient;
import com.ajah.logback.LogbackUtils;
import com.ajah.util.text.LoremIpsum;

/**
 * Tests {@link ElasticSearchNodeClient}
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Slf4j
public class ClientTest {

	/**
	 * Initialize logging.
	 */
	@Before
	public void setup() {
		LogbackUtils.bridge();
	}

	/**
	 * Test indexing and searching some docs.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testClient() throws Exception {
		TestElasticSearchNodeClient client = new TestElasticSearchNodeClient();
		int count = 10;
		for (int i = 0; i < count * 5; i++) {
			TestEntity testEntity = new TestEntity();
			testEntity.setId(new TestEntityId(UUID.randomUUID().toString()));
			testEntity.setName(LoremIpsum.getWord());
			testEntity.setBody(LoremIpsum.getParagraph() + " foobar ");
			client.index(testEntity);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<TestEntity> results = client.search("foobar");
		for (TestEntity result : results) {
			log.debug(result.toString());
		}
		client.close();
		Assert.assertEquals(count, results.size());
	}
}
