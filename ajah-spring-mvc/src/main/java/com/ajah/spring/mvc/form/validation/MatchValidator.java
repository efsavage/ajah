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

import lombok.extern.java.Log;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ajah.spring.mvc.form.AutoForm;
import com.ajah.util.AjahUtils;

/**
 * Validates {@link Match} annotations.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class MatchValidator implements Validator {

	/**
	 * This validator realistically only supports classes with the
	 * {@link AutoForm} attribute, but there's no real reason to enforce this
	 * restriction.
	 * 
	 * @return Always returns true
	 */
	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	/**
	 * Look for {@link Match} annotated fields, find the field they are supposed
	 * to match, and compare the values.
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 * @throws IllegalArgumentException
	 *             If the value of the annotation is empty or does not
	 *             correspond to another field.
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void validate(final Object target, final Errors errors) {
		log.finest("Validating " + target.getClass().getName());
		for (final Field field : target.getClass().getFields()) {
			if (field.isAnnotationPresent(Match.class)) {
				log.fine("Validating " + Match.class.getSimpleName() + " on " + field.getName());
				final Match match = field.getAnnotation(Match.class);
				final String matchTarget = match.value();
				AjahUtils.requireParam(matchTarget, "@Match value");
				try {
					final Field matchTargetField = target.getClass().getField(matchTarget);
					if (!field.getType().equals(matchTargetField.getType())) {
						throw new IllegalArgumentException("Target field is different type");
					}
					boolean valid = false;
					if (field.getType().isAssignableFrom(Comparable.class)) {
						valid = ((Comparable) field.get(target)).compareTo(matchTargetField.get(target)) == 0;
					} else if (field.get(target) != null) {
						valid = field.get(target).equals(matchTargetField.get(target));
					}
					log.fine("Valid: " + valid);
				} catch (final SecurityException | IllegalAccessException | NoSuchFieldException e) {
					throw new IllegalArgumentException(e);
				}
			}
		}
	}
}
