/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.text;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.logging.Level;

import lombok.extern.java.Log;

import com.ajah.util.AjahUtils;

/**
 * A class for dealing with {@link Locale}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class LocaleUtils {

	/**
	 * Returns the locale matching the specified search criteria.
	 * 
	 * 
	 * @see Locale#getISO3Country()
	 * @see Locale#getDisplayName()
	 * @param code
	 *            The code or name of the Locale
	 * @return The matching Locale, or null if no matches are found.
	 */
	public static Locale getLocale(String code) {
		AjahUtils.requireParam(code, "code");
		String upperCode = code.toUpperCase();
		for (Locale locale : Locale.getAvailableLocales()) {
			try {
				if (upperCode.equals(locale.getISO3Country())) {
					return locale;
				}
				if (upperCode.equals(locale.getDisplayName())) {
					return locale;
				}
			} catch (MissingResourceException e) {
				log.log(Level.WARNING, e.getMessage(), e);
			}
		}
		return null;
	}
	
}
