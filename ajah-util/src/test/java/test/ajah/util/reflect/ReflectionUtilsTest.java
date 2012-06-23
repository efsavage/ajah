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

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.ajah.util.io.Compact;
import com.ajah.util.reflect.IntrospectionUtils;
import com.ajah.util.reflect.ReflectionUtils;

/**
 * Tests {@link IntrospectionUtils}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */

public class ReflectionUtilsTest {
	
	public String str;
	public boolean bool;
	public int aint;
	public Compact compact;
	public Date adate;
	public long along;
	
	/**
	 * Test operations in ReflectionUtils
	 */
	@Test
	public void testReflection() {
		try {
		Class<?> clazz = Class.forName(ReflectionUtilsTest.class.getName());
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < props.length; i++) {
			PropertyDescriptor prop = props[i];
			Assert.assertNotNull(ReflectionUtils.propGetSafe(new ReflectionUtilsTest(), prop));
		}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
