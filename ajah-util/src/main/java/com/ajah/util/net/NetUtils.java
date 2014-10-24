/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.net;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;

import lombok.extern.java.Log;

import com.ajah.util.io.file.FileUtils;

/**
 * Utilities for dealing with networks and DNS.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class NetUtils {

	/**
	 * Returns hostname of the local system, if possible.
	 * 
	 * @return The hostname of the local system, or null if it could not be
	 *         determined.
	 */
	public static String getHostName() {
		String hostname = null;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			File file = new File("/proc/sys/kernel/hostname");
			if (file.exists() && file.canRead()) {
				try {
					hostname = FileUtils.readFile(file);
				} catch (IOException ioe) {
					log.log(Level.WARNING, ioe.getMessage(), ioe);
				}
			}
		}
		return hostname;
	}

}
