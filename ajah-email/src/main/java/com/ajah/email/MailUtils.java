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
package com.ajah.email;

import java.util.logging.Level;

import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.Transport;

import lombok.extern.java.Log;

import com.sun.mail.imap.IMAPFolder;

/**
 * Utilities for dealing with Mail
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class MailUtils {

	/**
	 * Closes an {@link IMAPFolder}, logging but ignoring any errors that occur
	 * while doing so.
	 * 
	 * @param folder
	 *            The folder to close.
	 */
	public static void safeClose(final IMAPFolder folder) {
		if (folder != null && folder.isOpen()) {
			try {
				folder.close(false);
			} catch (final MessagingException e) {
				log.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

	/**
	 * Closes an {@link IMAPFolder}, logging but ignoring any errors that occur
	 * while doing so.
	 * 
	 * @param store
	 *            The store to close.
	 */
	public static void safeClose(final Store store) {
		if (store != null && store.isConnected()) {
			try {
				store.close();
			} catch (final MessagingException e) {
				log.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

	/**
	 * Closes an {@link Transport}, logging but ignoring any errors that occur
	 * while doing so.
	 * 
	 * @param transport
	 *            The transport to close.
	 */
	public static void safeClose(final Transport transport) {
		if (transport != null && transport.isConnected()) {
			try {
				transport.close();
			} catch (final MessagingException e) {
				log.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

}
