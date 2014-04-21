/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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
package test.ajah.util.reflect;

import java.lang.reflect.Field;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.net.AjahMimeType;
import com.ajah.util.reflect.IntrospectionUtils;

/**
 * Tests {@link IntrospectionUtils}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class IntrospectionUtilsTest {

	public String str;
	public boolean bool;
	public int aint;
	public AjahMimeType identifiable;
	public Date adate;
	public long along;

	/**
	 * Test operations in IntrospectionUtils
	 */
	@Test
	public void testIntrospection() {
		Field field;
		try {
			field = IntrospectionUtilsTest.class.getField("str");
			Assert.assertTrue(IntrospectionUtils.isString(field));
			Assert.assertTrue(!IntrospectionUtils.isToStringable(field));

			field = IntrospectionUtilsTest.class.getField("bool");
			Assert.assertTrue(IntrospectionUtils.isBoolean(field));

			field = IntrospectionUtilsTest.class.getField("aint");
			Assert.assertTrue(IntrospectionUtils.isInt(field));

			field = IntrospectionUtilsTest.class.getField("adate");
			Assert.assertTrue(IntrospectionUtils.isDate(field));

			field = IntrospectionUtilsTest.class.getField("identifiable");
			Assert.assertTrue(IntrospectionUtils.isEnum(field));
			Assert.assertTrue(IntrospectionUtils.isIdentifiableEnum(field));
			Assert.assertTrue(IntrospectionUtils.isIdentifiable(field));

			field = IntrospectionUtilsTest.class.getField("along");
			Assert.assertTrue(IntrospectionUtils.isLong(field));

			field = IntrospectionUtilsTest.class.getField("along");
			Assert.assertTrue(IntrospectionUtils.isPrimitive(field));
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}
}
