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
package com.ajah.spring.mvc.form.validation;

import java.lang.reflect.Field;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ajah.crypto.Password;
import com.ajah.spring.mvc.form.AutoFormUtils;
import com.ajah.util.ToStringable;

/**
 * Validates {@link StringSize} annotations.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class StringSizeValidator implements Validator {

	private static Object[] getArgs(final Field field, final StringSize stringSize) {
		return new Object[] { AutoFormUtils.getLabel(field), Integer.valueOf(stringSize.min()), Integer.valueOf(stringSize.max()) };
	}

	/**
	 * This validator realistically only supports properties with the
	 * {@link ToStringable} attribute, but there's no real reason to enforce
	 * this restriction.
	 * 
	 * @return Always returns true
	 */
	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	/**
	 * Look for {@link StringSize} annotated fields, and checks the min/max
	 * values against the {@link Object#toString()} methods.
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		log.finest("Validating " + target.getClass().getName());
		for (final Field field : target.getClass().getFields()) {
			if (field.isAnnotationPresent(StringSize.class)) {
				final String fieldName = field.getName();
				log.fine("Validating " + StringSize.class.getSimpleName() + " on " + fieldName);
				final StringSize stringSize = field.getAnnotation(StringSize.class);
				Object value;
				try {
					value = field.get(target);
				} catch (final IllegalAccessException e) {
					log.log(Level.SEVERE, e.getMessage(), e);
					errors.rejectValue(fieldName, StringSize.class.getSimpleName(), getArgs(field, stringSize), stringSize.message());
					return;
				}
				final String stringValue = value != null ? value.toString() : "";
				int len = stringValue.length();
				if (value instanceof Password) {
					len = ((Password) value).getOriginalLength();
				}
				log.fine("Value is " + len + " chars [min=" + stringSize.min() + ", max=" + stringSize.max() + "]");
				if (len < stringSize.min()) {
					errors.rejectValue(fieldName, StringSize.class.getSimpleName(), getArgs(field, stringSize), stringSize.message());
				}
				if (len > stringSize.min()) {
					errors.rejectValue(fieldName, StringSize.class.getSimpleName(), getArgs(field, stringSize), stringSize.message());
				}
			}
		}
	}

}
