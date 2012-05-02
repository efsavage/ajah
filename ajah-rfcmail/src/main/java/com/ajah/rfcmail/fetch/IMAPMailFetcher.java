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
package com.ajah.rfcmail.fetch;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.sun.mail.imap.IMAPFolder;

/**
 * Class for fetching mail from an IMAP server.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IMAPMailFetcher extends AbstractMailFetcher {

	private static final Logger log = Logger.getLogger(IMAPMailFetcher.class.getName());

	private String username;
	private String password;
	private String hostname;

	/**
	 * Fetches mail from default folder and all child folders.
	 */
	@Override
	public void go() throws MessagingException, IOException {

		final Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		final Session session = Session.getDefaultInstance(props, null);
		final Store store = session.getStore("imaps");
		store.connect(getHostname(), getUsername(), getPassword());
		log.fine("Connected to: " + store);
		final IMAPFolder defaultFolder = (IMAPFolder) store.getDefaultFolder();
		log.fine("Default folder is: " + defaultFolder.getFullName());
		processFolder(defaultFolder);
	}

	private void processFolder(final IMAPFolder folder) throws MessagingException, IOException {
		log.fine("Processing folder " + folder.getFullName());

		final long start = System.currentTimeMillis();
		if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
			folder.open(Folder.READ_ONLY);
			// long uidNext = folder.getUIDNext();
			final Message[] messages = folder.getMessages();
			log.fine(messages.length + " messages");
			final FetchProfile fp = new FetchProfile();
			fp.add("Message-Id");
			folder.fetch(messages, fp);
			long messageCount = 0;
			for (final Message message : messages) {
				messageCount++;
				try {
					for (MessageHandler messageHandler : getMessageHandlers()) {
						log.finest(messageHandler.getClass().getSimpleName() + " handling " + ((MimeMessage) message).getMessageID());
						messageHandler.handle(new AjahMimeMessage(message.getInputStream()));
						return;
					}
				} catch (final MessagingException e) {
					log.log(Level.SEVERE, e.getMessage(), e);
				}
			}
			log.finest(messageCount + " messages");
		}
		final long end = System.currentTimeMillis();
		log.finest("Processed " + folder.getFullName() + " in " + (end - start) + "ms");
		if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
			final Folder[] children = folder.list();
			if (children != null) {
				for (final Folder child : children) {
					processFolder((IMAPFolder) child);
				}
			}
		}
	}

}
