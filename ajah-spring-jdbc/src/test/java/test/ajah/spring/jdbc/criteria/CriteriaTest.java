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
package test.ajah.spring.jdbc.criteria;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.spring.jdbc.criteria.Criteria;

/**
 * Tests {@link Criteria}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@SuppressWarnings("static-method")
public class CriteriaTest {

	/**
	 * Tests a simple {@link Criteria#eq(com.ajah.util.ToStringable)} operation.
	 */
	@Test
	public void testCriteria() {
		final Criteria criteria = new Criteria().eq("object_id", "1234");
		Assert.assertEquals(" WHERE `object_id`=?", criteria.getWhere().getSql());
		Assert.assertEquals("1234", criteria.getWhere().getValues().get(0));
	}

	/**
	 * Tests a simple {@link Criteria#eq(com.ajah.util.ToStringable)} operation.
	 */
	@Test
	public void testLike() {
		final Criteria criteria = new Criteria().like("object_id", "%foo?");
		Assert.assertEquals(" WHERE `object_id` LIKE '%foo?'", criteria.getWhere().getSql());
		Assert.assertEquals(0, criteria.getWhere().getValues().size());
	}

}
