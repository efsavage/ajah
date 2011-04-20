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
import java.util.logging.Logger;

import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class AutoFormUtils {

	private static final Logger log = Logger.getLogger(AutoFormUtils.class.getName());

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
	public static String getLabel(Field field) {
		AjahUtils.requireParam(field, "field");
		String label = StringUtils.capitalize(StringUtils.splitCamelCase(field.getName()));
		if (field.isAnnotationPresent(Label.class)) {
			log.fine("@Label is present");
			label = field.getAnnotation(Label.class).value();
		}
		return label;
	}

}