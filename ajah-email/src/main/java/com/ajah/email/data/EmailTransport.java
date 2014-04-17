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

package com.ajah.email.data;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.ajah.email.EmailMessage;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public interface EmailTransport {

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            The message to send.
	 * @throws MessagingException
	 *             If the message could not be transmitted.
	 * @throws AddressException
	 *             If any of the addresses are invalid.
	 */
	void send(EmailMessage message) throws AddressException, MessagingException;

}
