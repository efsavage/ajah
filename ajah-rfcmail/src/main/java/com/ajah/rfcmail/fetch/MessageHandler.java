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

import javax.mail.MessagingException;

import com.ajah.rfcmail.AjahMessage;

/**
 * An interface for defining an operation to be performed on a message handler.
 * These handlers can be chained with the
 * {@link #addHandler(MessageHandlerResult, MessageHandler)} method.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public interface MessageHandler extends AutoCloseable {

	/**
	 * Process a message.
	 * 
	 * @param message
	 *            The message to process.
	 * @return The result of the message processing, will not be null.
	 * @throws MessagingException
	 */
	MessageHandlerResponse handle(AjahMessage message) throws MessagingException;

	/**
	 * Add a message handler to be activated if this handler yields in the
	 * specified result.
	 * 
	 * @param result
	 *            The result that will activate the child message handler.
	 * @param messageHandler
	 *            The messageHandler to activate.
	 * @throws MessagingException
	 */
	void addHandler(MessageHandlerResult result, MessageHandler messageHandler) throws MessagingException;

	/**
	 * Closes this message handler and all child handlers.
	 * 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close();

}
