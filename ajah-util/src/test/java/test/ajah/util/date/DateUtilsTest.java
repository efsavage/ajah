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
package test.ajah.util.date;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.date.DateUtils;

/**
 * Tests for the {@link DateUtils} class.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class DateUtilsTest {

	/**
	 * Adds a full day to a partial day.
	 */
	@Test
	public void testAddDays() {
		final Date date = new Date(1357016500000L);
		final Date newDate = DateUtils.addDays(date, 1);
		Assert.assertEquals(86300000L, newDate.getTime() - date.getTime());
	}

	/**
	 * Adds a full day to an exact day boundary.
	 */
	@Test
	public void testAddFullDay() {
		final Date date = new Date(1357016400000L);
		final Date newDate = DateUtils.addDays(date, 1);
		Assert.assertEquals(86400000L, newDate.getTime() - date.getTime());
	}

}
