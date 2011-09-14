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

import javax.mail.MessagingException;

/**
 * Class for fetching mail.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public interface MailFetcher extends AutoCloseable {

	/**
	 * Execute the mail fetching process.
	 * 
	 * @throws MessagingException
	 *             If there was a problem with the messages themselves.
	 * @throws IOException
	 *             If there was a problem fetching the messages.
	 */
	void go() throws MessagingException, IOException;

	/**
	 * Adds a message handler to this mailfetcher to act on messages that are
	 * found.
	 * 
	 * @param messageHandler
	 *            The message handler to add. MessageHandlers will be invoked in
	 *            the order in which they are added.
	 */
	void addMessageHandler(MessageHandler messageHandler);
}
