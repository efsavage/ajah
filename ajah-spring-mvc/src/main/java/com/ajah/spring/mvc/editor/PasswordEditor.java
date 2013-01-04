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
import java.util.logging.Level;

import lombok.extern.java.Log;

import com.ajah.crypto.CryptoException;
import com.ajah.crypto.HmacSha1Password;
import com.ajah.crypto.Password;
import com.ajah.util.StringUtils;

/**
 * Property editor for {@link Password} type. Uses {@link HmacSha1Password}
 * implementation.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Log
public class PasswordEditor extends PropertyEditorSupport {

	/**
	 * Returns {@link Password#toString()} if not null. Returns null if null.
	 * 
	 * @see java.beans.PropertyEditorSupport#getAsText()
	 */
	@Override
	public String getAsText() {
		return getValue() == null ? null : getValue().toString();
	}

	/**
	 * Sets value via {@link HmacSha1Password#HmacSha1Password(String, boolean)
	 * )} if not null, sets to null if parameter is null.
	 * 
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
	 */
	@Override
	public void setAsText(final String text) {
		try {
			setValue(StringUtils.isBlank(text) ? null : new HmacSha1Password(text, false));
		} catch (CryptoException e) {
			log.log(Level.WARNING, e.getMessage(), e);
			setValue(null);
		}
	}

}
