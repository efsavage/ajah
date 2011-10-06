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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ajah.spring.mvc.form.AutoForm;

/**
 * Serves as a wrapper to support the custom validation annotations in this
 * library. Invokes validate() method from standardValidator first, then
 * validates against custom annotations.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class AutoFormValidator implements Validator {

	@Autowired
	private Validator standardValidator;

	private final Validator[] customValidators = new Validator[] { new MatchValidator(), new StringSizeValidator() };

	/**
	 * Returns the "standard" validator, i.e. the one that will handle standard
	 * validation like java.validation annotations.
	 * 
	 * @return The standard validator, if set, otherwise null.
	 */
	public Validator getStandardValidator() {
		return this.standardValidator;
	}

	/**
	 * Sets the "standard" validator, i.e. the one that will handle standard
	 * validation like java.validation annotations.
	 * 
	 * @param standardValidator
	 *            The "standard" validator, may be null.
	 */
	public void setStandardValidator(Validator standardValidator) {
		this.standardValidator = standardValidator;
	}

	/**
	 * This validator realistically only supports classes with the
	 * {@link AutoForm} attribute, but there's no real reason to enforce this
	 * restriction.
	 * 
	 * @return Always returns true
	 */
	public boolean supports(Class<?> clazz) {
		return true;
	}

	/**
	 * Invokes validate() method from superclass first, then validates against
	 * custom annotations.
	 * 
	 * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter#
	 *      validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object object, Errors errors) {
		if (this.standardValidator != null) {
			ValidationUtils.invokeValidator(this.standardValidator, object, errors);
		}
		for (Validator validator : this.customValidators) {
			ValidationUtils.invokeValidator(validator, object, errors);
		}

	}

}
