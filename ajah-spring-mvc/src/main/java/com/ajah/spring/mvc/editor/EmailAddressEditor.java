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
package com.ajah.spring.mvc.editor;

import java.beans.PropertyEditorSupport;

import com.ajah.util.StringUtils;
import com.ajah.util.data.format.EmailAddress;

/**
 * Property editor for {@link EmailAddress} type.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class EmailAddressEditor extends PropertyEditorSupport {

	/**
	 * Returns {@link EmailAddress#toString()} if not null. Returns null if
	 * null.
	 * 
	 * @see java.beans.PropertyEditorSupport#getAsText()
	 */
	@Override
	public String getAsText() {
		return getValue() == null ? null : getValue().toString();
	}

	/**
	 * Sets value via {@link EmailAddress#EmailAddress(String)} if not null,
	 * sets to null if parameter is null.
	 * 
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
	 */
	@Override
	public void setAsText(final String text) {
		setValue(StringUtils.isBlank(text) ? null : new EmailAddress(text));
	}

}
