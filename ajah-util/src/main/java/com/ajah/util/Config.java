package com.ajah.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Config object. Works kind of like a stripped-down version of commons config,
 * but without all the bells and whistles (or dependencies). Uses enum style
 * singleton pattern.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum Config {

	i;

	private static final Logger log = Logger.getLogger(Config.class.getName());

	private Properties properties = new Properties();

	private Config() {
		String config = StringUtils.isBlank(System.getProperty("config")) ? "ajah.properties" : System.getProperty("config");
		URL url = ClassLoader.getSystemResource(config);
		if (url != null) {
			try {
				properties.load(url.openStream());
				Logger.getLogger(Config.class.getName()).log(Level.CONFIG, "Loaded properties from " + config);
			} catch (IOException e) {
				Logger.getLogger(Config.class.getName()).log(Level.CONFIG, e.getMessage(), e);
			}
		} else {
			Logger.getLogger(Config.class.getName()).log(Level.CONFIG, "No resource " + config + " found.");
		}
	}

	public String get(String key, String defaultValue) {
		String retVal = (String) properties.get(key);
		if (StringUtils.isBlank(retVal)) {
			return defaultValue;
		}
		return retVal;
	}

	public void set(String key, String value) {
		log.fine("Property " + key + " set to " + value);
		properties.put(key, value);
	}

}