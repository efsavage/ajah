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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows for customization of the submit button on an AutoForm.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Submit {

	/**
	 * The icon to use in the submit button, to the left of the text, optional.
	 */
	Icon iconLeft() default Icon.NONE;

	/**
	 * The icon to use in the submit button, to the right of the text, optional.
	 */
	Icon iconRight() default Icon.NONE;

	/**
	 * Value attribute of the submit button.
	 * 
	 * @return Value attribute of the submit button, no default value.
	 */
	String value();

}
