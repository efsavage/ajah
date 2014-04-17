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
package com.ajah.email;

import java.util.Collection;
import java.util.List;

import com.ajah.util.data.format.EmailAddress;

/**
 * Simple bean that represents a message. The reason we're not using a standard
 * javamail object is that we will want to persist this and transmit it across
 * other channels (web services, JMS, etc).
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public interface EmailMessage {

	/**
	 * Sets the subject of the message.
	 * 
	 * @param string
	 *            The subject of the message.
	 */
	void setSubject(final String string);

	/**
	 * Returns the From: Address of the message.
	 * 
	 * @return The From: Address of the message.
	 */
	EmailAddress getFrom();

	/**
	 * Returns the From: Name of the message.
	 * 
	 * @return The From: Name of the message.
	 */
	String getFromName();

	/**
	 * Returns the recipients of the message.
	 * 
	 * @return The recipients of the message.
	 */
	Collection<EmailRecipient> getRecipients();

	/**
	 * Returns the plaintext version of the message.
	 * 
	 * @return The plaintext version of the message.
	 */
	String getText();

	/**
	 * Returns the subject of the message.
	 * 
	 * @return The subject of the message.
	 */
	String getSubject();

	/**
	 * Returns the HTML version of the message.
	 * 
	 * @return The HTML version of the message.
	 */
	String getHtml();

	/**
	 * Returns the tags for this message. This may be used by some transports
	 * like Mandrill.
	 * 
	 * @return The tags for this message.
	 */
	List<String> getTags();

}
