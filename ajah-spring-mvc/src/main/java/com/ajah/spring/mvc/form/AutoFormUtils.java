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
package com.ajah.spring.mvc.form;

import java.lang.reflect.Field;

import lombok.extern.java.Log;

import com.ajah.html.dtd.InputType;
import com.ajah.util.AjahUtils;
import com.ajah.util.Named;
import com.ajah.util.StringUtils;
import com.ajah.util.reflect.IntrospectionUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class AutoFormUtils {

	/**
	 * Looks for {@link Hidden} and {@link Password} annotations to determine
	 * the input type.
	 * 
	 * @param field
	 *            The field to inspect.
	 * @return The input type, default value is {@link InputType#TEXT}
	 */
	public static InputType getInputType(final Field field) {
		if (field.isAnnotationPresent(Hidden.class)) {
			log.finest("Field " + field.getName() + " is hidden");
			return InputType.HIDDEN;
		}
		if (field.isAnnotationPresent(com.ajah.spring.mvc.form.Password.class)) {
			log.finest("Field " + field.getName() + " is a password");
			return InputType.PASSWORD;
		}
		log.finest("Field " + field.getName() + " is text");
		return InputType.TEXT;
	}

	/**
	 * Returns a human-readable version of an {@link AutoForm}'s field name. If
	 * there is a {@link Label} annotation, that will be used, otherwise the
	 * field name will be de-camelcased.
	 * 
	 * @see StringUtils#splitCamelCase(String)
	 * @param field
	 *            The field to derive the label of, required.
	 * @return The label of the field, should not be null.
	 */
	public static String getLabel(final Field field) {
		AjahUtils.requireParam(field, "field");
		if (field.isAnnotationPresent(Hidden.class)) {
			return null;
		}
		String label = StringUtils.capitalize(StringUtils.splitCamelCase(field.getName()));
		if (field.isAnnotationPresent(Label.class)) {
			log.fine("@Label is present");
			label = field.getAnnotation(Label.class).value();
		}
		return label;
	}

	/**
	 * Returns a human-readable version of an {@link AutoForm}'s field name. If
	 * there is a {@link Label} annotation, that will be used, otherwise the
	 * field name will be de-camelcased.
	 * 
	 * @see StringUtils#splitCamelCase(String)
	 * @param field
	 *            The field to derive the label of, required.
	 * @param object
	 *            The instance to get the label of.
	 * @return The label of the field, should not be null.
	 */
	public static String getLabel(final Field field, final Object object) {
		if (IntrospectionUtils.isEnum(field) && IntrospectionUtils.isNamed(field)) {
			final String name = ((Named) object).getName();
			if (!StringUtils.isBlank(name)) {
				return name;
			}
		}
		log.finest("isEnum: " + IntrospectionUtils.isEnum(field));
		log.finest("isNamed: " + IntrospectionUtils.isNamed(field));
		return getLabel(field);
	}
}
