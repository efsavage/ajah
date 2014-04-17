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
package com.ajah.email.data;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.email.EmailMessage;

/**
 * Manages the persistance and transport of email messages.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmailMessageManager {

	@Autowired
	EmailTransport transport;

	/**
	 * Sends a message via the configured transport.
	 * 
	 * @param message
	 *            The message to send.
	 * @throws MessagingException
	 *             If the message could not be transmitted.
	 * @throws AddressException
	 *             If any of the addresses are invalid.
	 */
	public void send(final EmailMessage message) throws AddressException, MessagingException {
		this.transport.send(message);
	}

}
