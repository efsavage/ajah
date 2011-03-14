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

/**
 * An autoform is a bean that can be created, pretty much as-is, as a form. The
 * goal is to let the system manage the details and only write the input (the
 * bean, with fields and rules) and the output (the controller which accepts the
 * bean). The UI can be generated from the form and the hints therein, and the
 * simple validation can be enforced without additional code.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public @interface AutoForm {
	// Empty
}