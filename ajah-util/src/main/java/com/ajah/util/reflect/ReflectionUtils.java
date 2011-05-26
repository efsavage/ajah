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
package com.ajah.util.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ajah.util.AjahUtils;
import com.ajah.util.Identifiable;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class ReflectionUtils {

	private static final Logger log = Logger.getLogger(ReflectionUtils.class.getName());

	/**
	 * Executes the read method for a property descriptor. If any errors occur
	 * they are logged, and will result in null return value.
	 * 
	 * @param instance
	 * @param propertyDescriptor
	 * @return The value of the objects read method for the property specified
	 *         (which may be null), or null if an error occurs.
	 */
	public static Object propGetSafe(Object instance, PropertyDescriptor propertyDescriptor) {
		AjahUtils.requireParam(instance, "instance");
		AjahUtils.requireParam(propertyDescriptor, "propertyDescriptor");
		try {
			Method getter = propertyDescriptor.getReadMethod();
			if (getter != null) {
				return getter.invoke(instance);
			}
			log.log(Level.SEVERE, "No read method found for " + propertyDescriptor.getName() + " on class " + instance.getClass().getName());
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, propertyDescriptor.getName() + ": " + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.log(Level.SEVERE, propertyDescriptor.getName() + ": " + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.log(Level.SEVERE, propertyDescriptor.getName() + ": " + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Executes the read method for a property descriptor, and returns as a
	 * {@link Date}. If any errors occur they are logged, and will result in
	 * null return value.
	 * 
	 * @param instance
	 *            The instance on which the Property Descriptor is.
	 * @param propertyDescriptor
	 *            The property descriptor for the property sought.
	 * @return The value of the objects read method for the property specified
	 *         (which may be null), or null if an error occurs.
	 */
	public static Date propGetDateSafe(Object instance, PropertyDescriptor propertyDescriptor) {
		Object value = ReflectionUtils.propGetSafe(instance, propertyDescriptor);
		if (value == null || !(value instanceof Date)) {
			return null;
		}
		return (Date) value;
	}

	/**
	 * Finds the enum where the getId() value matches the value of the field for
	 * this particular object.
	 * 
	 * @param field
	 *            The field to get the value of.
	 * @param value
	 *            The value of the field.
	 * @return The value, null if the field value is null, also null on error.
	 */
	public static Object findEnumById(Field field, Object value) {
		if (!IntrospectionUtils.isIdentifiableEnum(field)) {
			throw new IllegalArgumentException("Field " + field.getName() + " is not an Identifiable enum");
		}
		if (value == null) {
			return null;
		}
		Object[] elements = field.getType().getEnumConstants();
		for (Object element : elements) {
			if (((Identifiable<?>) element).getId().equals(value)) {
				return element;
			}
		}
		return null;
	}

	public static Object propGetSafeAuto(Object object, Field field, PropertyDescriptor propertyDescriptor) {
		if (IntrospectionUtils.isString(field)) {
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		} else if (IntrospectionUtils.isDate(field)) {
			return AjahUtils.toUnix(ReflectionUtils.propGetDateSafe(object, propertyDescriptor));
		} else if (IntrospectionUtils.isToStringable(field)) {
			return ReflectionUtils.propGetSafe(object, propertyDescriptor).toString();
		} else if (IntrospectionUtils.isIdentifiable(field)) {
			return ((Identifiable<?>) ReflectionUtils.propGetSafe(object, propertyDescriptor)).getId().toString();
		} else if (IntrospectionUtils.isInt(field)) {
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		} else if (IntrospectionUtils.isLong(field)) {
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		} else {
			log.warning("Can't handle property getting of type " + field.getType());
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		}
	}

}