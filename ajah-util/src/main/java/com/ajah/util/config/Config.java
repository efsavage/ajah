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
package com.ajah.util.config;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ajah.util.StringUtils;

/**
 * Config object. Works kind of like a stripped-down version of commons config,
 * but without all the bells and whistles (or dependencies). Uses enum style
 * singleton pattern.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum Config {

	/**
	 * Singleton instance of Config.
	 */
	i;

	private static final Logger log = Logger.getLogger(Config.class.getName());

	private Properties properties = new Properties();

	private Config() {
		String config = StringUtils.isBlank(System.getProperty("config")) ? "ajah.properties" : System.getProperty("config");
		URL url = ClassLoader.getSystemResource(config);
		if (url != null) {
			try {
				this.properties.load(url.openStream());
				Logger.getLogger(Config.class.getName()).log(Level.CONFIG, "Loaded properties from " + config);
			} catch (IOException e) {
				Logger.getLogger(Config.class.getName()).log(Level.CONFIG, e.getMessage(), e);
			}
		} else {
			Logger.getLogger(Config.class.getName()).log(Level.CONFIG, "No resource " + config + " found.");
		}
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
	public String get(String key, String defaultValue) {
		String retVal = (String) this.properties.get(key);
		if (StringUtils.isBlank(retVal)) {
			return defaultValue;
		}
		return retVal;
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
	public String get(PropertyKey key) {
		return get(key.getName(), key.getDefaultValue());
	}

	/**
	 * Sets the value for the specified key in the local map.
	 * 
	 * <strong>These changes will not be persisted!</strong>
	 * 
	 * @param key
	 *            The key of the property to set.
	 * @param value
	 *            The value of the property to set.
	 */
	public void set(String key, String value) {
		log.fine("Property " + key + " set to " + value);
		this.properties.put(key, value);
	}

}