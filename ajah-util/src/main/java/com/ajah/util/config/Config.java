/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.extern.java.Log;

import com.ajah.util.StringUtils;
import com.ajah.util.data.format.EmailAddress;

/**
 * Config object. Works kind of like a stripped-down version of commons config,
 * but without all the bells and whistles (or dependencies). Uses enum style
 * singleton pattern.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
@Log
public enum Config {

	/**
	 * Singleton instance of Config.
	 */
	i;

	/**
	 * Returns the logger.
	 * 
	 * @return The logger.
	 */
	private static Logger getLog() {
		return log;
	}

	private final Properties properties = new Properties();

	Config() {
		final String config = StringUtils.isBlank(System.getProperty("config")) ? "/ajah.properties" : System.getProperty("config");
		try (final InputStream stream = getClass().getResourceAsStream(config)) {
			if (stream != null) {
				try {
					this.properties.load(stream);
					Logger.getLogger(Config.class.getName()).log(Level.CONFIG, "Loaded properties from " + config);
				} catch (final IOException e) {
					Logger.getLogger(Config.class.getName()).log(Level.CONFIG, e.getMessage(), e);
				}
			} else {
				Logger.getLogger(Config.class.getName()).log(Level.CONFIG, "No resource " + config + " found.");
			}
		} catch (final IOException e) {
			getLog().log(Level.WARNING, e.getMessage(), e);
		}
		for (final Object propKey : this.properties.keySet()) {
			final String sysProp = System.getProperty((String) propKey);
			if (!StringUtils.isBlank(sysProp)) {
				Logger.getLogger(Config.class.getName()).log(Level.CONFIG, "Overriding property " + propKey + " with System property value of " + sysProp);
				this.properties.setProperty((String) propKey, sysProp);
			}
		}
	}

	/**
	 * Returns the value for the specified key, or the default value specified.
	 * Invokes {@link #get(String, String)} with the values from
	 * {@link PropertyKey}.
	 * 
	 * @param key
	 *            The key of the property sought.
	 * @return Returns the value for the specified key, or defaultValue
	 */
	public String get(final PropertyKey key) {
		if (key.getFallback() != null) {
			return get(key.getName(), get(key.getFallback()));
		}
		return get(key.getName(), key.getDefaultValue());
	}

	/**
	 * Calls {@link #get(String, String)} with a default value of null.
	 * 
	 * @param key
	 *            The key to fetch.
	 * @return The value of the key, or null.
	 */
	public String get(final String key) {
		return get(key, null);
	}

	/**
	 * Returns the value for the specified key, or the default value specified.
	 * 
	 * @param key
	 *            The key of the property sought.
	 * @param defaultValue
	 *            The value to return if no value is found.
	 * @return Returns the value for the specified key, or defaultValue
	 */
	public String get(final String key, final String defaultValue) {
		final String retVal = (String) this.properties.get(key);
		if (StringUtils.isBlank(retVal)) {
			return defaultValue;
		}
		return retVal;
	}

	/**
	 * Returns The value as a boolean.
	 * 
	 * @param key
	 *            The property to return
	 * @return Returns true if the value is "true", case-insensitive.
	 */
	public boolean getBoolean(final PropertyKey key) {
		return "true".equalsIgnoreCase(get(key));
	}

	/**
	 * Returns The value as a boolean.
	 * 
	 * @param key
	 *            The property to return
	 * @param defaultValue
	 *            The value to return if the property is not found.
	 * @return Returns true if the value is "true", case-insensitive.
	 */
	public boolean getBoolean(final String key, final boolean defaultValue) {
		return "true".equalsIgnoreCase(get(key, String.valueOf(defaultValue)));
	}

	/**
	 * Returns the value as an EmailAddress.
	 * 
	 * @see EmailAddress#EmailAddress(String)
	 * @param key
	 *            The key of the property sought.
	 * @return Email address, if valid.
	 * @throws IllegalArgumentException
	 *             If the value is not a valid address.
	 */
	public EmailAddress getEmailAddress(final PropertyKey key) {
		return new EmailAddress(get(key.getName(), key.getDefaultValue()));
	}

	/**
	 * Returns The value as a int.
	 * 
	 * @param key
	 *            The property to return
	 * @param defaultValue
	 *            The value to return if the property is not found.
	 * @return Returns true if the value is "true", case-insensitive.
	 */
	public int getInt(final String key, final int defaultValue) {
		final String retVal = (String) this.properties.get(key);
		if (StringUtils.isBlank(retVal)) {
			return defaultValue;
		}
		return Integer.parseInt(retVal);
	}

	/**
	 * Returns The value as a long.
	 * 
	 * @param key
	 *            The property to return
	 * @param defaultValue
	 *            The value to return if the property is not found.
	 * @return Returns true if the value is "true", case-insensitive.
	 */
	public long getLong(final String key, final long defaultValue) {
		final String retVal = (String) this.properties.get(key);
		if (StringUtils.isBlank(retVal)) {
			return defaultValue;
		}
		return Long.parseLong(retVal);
	}

	/**
	 * Returns the value, split on commas. Whitespace around the commas is
	 * permitted and removed.
	 * 
	 * @param key
	 *            The property to return.
	 * @return The values as delimited by commas in the property value.
	 */
	public String[] getStrings(final PropertyKey key) {
		return get(key).split("\\s*,\\s*");
	}

	/**
	 * Sets the value for the specified key in the local map. <strong>These
	 * changes will not be persisted!</strong>
	 * 
	 * @param key
	 *            The key of the property to set.
	 * @param value
	 *            The value of the property to set.
	 */
	public void set(final String key, final String value) {
		log.fine("Property " + key + " set to " + value);
		this.properties.put(key, value);
	}

}
