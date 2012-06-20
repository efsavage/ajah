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
package test.ajah.lang;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.ajah.lang.ListMap;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class ListMapTest {

	/**
	 * Put some objects in and get them back out.
	 */
	@Test
	public void basicTest() {
		final ListMap<String, Date> listMap = new ListMap<>();
		final Date now = new Date();
		final Date yesterday = new Date(System.currentTimeMillis() - 86400000L);
		final Date tomorrow = new Date(System.currentTimeMillis() + 86400000L);
		List<Date> dates = listMap.getList("all");
		Assert.assertNotNull(dates);
		Assert.assertEquals(0, dates.size());
		listMap.putValue("all", now);
		listMap.putValue("all", yesterday);
		listMap.putValue("all", tomorrow);
		dates = listMap.getList("all");
		Assert.assertEquals(3, dates.size());
		Assert.assertTrue(dates.contains(now));
		Assert.assertTrue(dates.contains(yesterday));
		Assert.assertTrue(dates.contains(tomorrow));
	}

}
