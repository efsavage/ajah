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

import lombok.extern.java.Log;

import com.ajah.util.AjahUtils;
import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;
import com.ajah.util.date.DateUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class ReflectionUtils {

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
	public static Object findEnumById(final Field field, final Object value) {
		if (!IntrospectionUtils.isIdentifiableEnum(field)) {
			throw new IllegalArgumentException("Field " + field.getName() + " is not an Identifiable enum");
		}
		if (value == null) {
			return null;
		}
		final Object[] elements = field.getType().getEnumConstants();
		for (final Object element : elements) {
			if (((Identifiable<?>) element).getId().equals(value)) {
				if (log.isLoggable(Level.FINEST)) {
					log.finest("Matched: " + element.toString());
				}
				return element;
			}
		}
		if (log.isLoggable(Level.FINEST)) {
			log.finest("No Match for " + value);
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
	public static Date propGetDateSafe(final Object instance, final PropertyDescriptor propertyDescriptor) {
		final Object value = ReflectionUtils.propGetSafe(instance, propertyDescriptor);
		if (value == null || !(value instanceof Date)) {
			return null;
		}
		return (Date) value;
	}

	/**
	 * Executes the read method for a property descriptor. If any errors occur
	 * they are logged, and will result in null return value.
	 * 
	 * @param instance
	 * @param propertyDescriptor
	 * @return The value of the objects read method for the property specified
	 *         (which may be null), or null if an error occurs.
	 */
	public static Object propGetSafe(final Object instance, final PropertyDescriptor propertyDescriptor) {
		AjahUtils.requireParam(instance, "instance");
		AjahUtils.requireParam(propertyDescriptor, "propertyDescriptor");
		try {
			final Method getter = propertyDescriptor.getReadMethod();
			if (getter != null) {
				return getter.invoke(instance);
			}
			log.log(Level.SEVERE, "No read method found for " + propertyDescriptor.getName() + " on class " + instance.getClass().getName());
		} catch (final RuntimeException e) {
			log.log(Level.SEVERE, propertyDescriptor.getName() + ": " + e.getMessage(), e);
		} catch (final IllegalAccessException e) {
			log.log(Level.SEVERE, propertyDescriptor.getName() + ": " + e.getMessage(), e);
		} catch (final InvocationTargetException e) {
			log.log(Level.SEVERE, propertyDescriptor.getName() + ": " + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Returns the value of a property descriptor, or null if an error occurs.
	 * 
	 * @param object
	 *            The object instance to invoke the property descriptor on
	 * @param field
	 *            The field of the class of the object that corresponds with the
	 *            property descriptor.
	 * @param propertyDescriptor
	 *            The property descriptor to invoke on the object.
	 * @return The value of a property descriptor, or null if an error occurs.
	 */
	public static Object propGetSafeAuto(final Object object, final Field field, final PropertyDescriptor propertyDescriptor) {
		if (IntrospectionUtils.isString(field)) {
			// It's a string
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		} else if (IntrospectionUtils.isDate(field)) {
			// It's a date
			return DateUtils.safeToLong(ReflectionUtils.propGetDateSafe(object, propertyDescriptor));
		} else if (IntrospectionUtils.isToStringable(field)) {
			// It's toStringable
			return StringUtils.safeToString(ReflectionUtils.propGetSafe(object, propertyDescriptor));
		} else if (IntrospectionUtils.isIdentifiable(field)) {
			// It's identifiable
			final Identifiable<?> identifiable = ((Identifiable<?>) ReflectionUtils.propGetSafe(object, propertyDescriptor));
			if (identifiable == null) {
				return null;
			}
			return StringUtils.safeToString(identifiable.getId());
		} else if (IntrospectionUtils.isInt(field)) {
			// It's an int
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		} else if (IntrospectionUtils.isLong(field)) {
			// It's a long
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		} else if (IntrospectionUtils.isBoolean(field)) {
			// It's a boolean
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		} else {
			log.warning("Can't handle property getting of type " + field.getType() + " for field " + field.getName());
			return ReflectionUtils.propGetSafe(object, propertyDescriptor);
		}
	}

}
