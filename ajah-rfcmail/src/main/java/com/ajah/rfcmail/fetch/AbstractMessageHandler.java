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

import com.ajah.lang.ListMap;
import com.ajah.rfcmail.AjahMessage;
import com.ajah.util.AjahUtils;

/**
 * Basic implementation of {@link MessageHandler} that can handle chained
 * handlers.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public abstract class AbstractMessageHandler implements MessageHandler {

	ListMap<MessageHandlerResult, MessageHandler> messageHandlers = new ListMap<>();

	/**
	 * Handle the message via {@link #innerHandle(AjahMessage)} and then call
	 * handle on child handlers.
	 * 
	 * @see com.ajah.rfcmail.fetch.MessageHandler#handle(AjahMessage)
	 */
	@Override
	public final MessageHandlerResponse handle(AjahMessage message) throws MessagingException {
		AjahUtils.requireParam(message, "message");
		AjahUtils.requireParam(message.getId(), "message.id");
		MessageHandlerResponse response = innerHandle(message);
		for (MessageHandler messageHandler : this.messageHandlers.getList(response.getResult())) {
			messageHandler.handle(message);
		}
		return null;
	}

	protected abstract MessageHandlerResponse innerHandle(AjahMessage message) throws MessagingException;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addHandler(MessageHandlerResult result, MessageHandler messageHandler) throws MessagingException {
		this.messageHandlers.putValue(result, messageHandler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		for (MessageHandlerResult result : this.messageHandlers.keySet()) {
			for (MessageHandler messageHandler : this.messageHandlers.get(result)) {
				messageHandler.close();
			}
		}
	}

}
