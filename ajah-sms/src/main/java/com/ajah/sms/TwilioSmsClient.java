/*
 *  Copyright 2013-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.sms;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.java.Log;

import com.ajah.util.AjahUtils;
import com.ajah.util.config.Config;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;

/**
 * A simple SMS client. Currently wraps {@link TwilioRestClient}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class TwilioSmsClient implements SmsClient {

	Account mainAccount;
	private String defaultSender;

	/**
	 * Constructs a client using configuration properties twilio.sid,
	 * twilio.token and twilio.sender.default.
	 */
	public TwilioSmsClient() {
		TwilioRestClient client = new TwilioRestClient(Config.i.get("twilio.sid"), Config.i.get("twilio.token"));
		this.mainAccount = client.getAccount();
		this.defaultSender = Config.i.get("twilio.sender.default");
		log.fine("Using Twilio account " + this.mainAccount.getFriendlyName());
	}

	/**
	 * Sends an Sms to a phone number.
	 * 
	 * @param to
	 *            The number to send to.
	 * @param message
	 *            The message to send.
	 * @throws SmsException
	 *             If the message could not be sent.
	 */
	public void send(String to, String message) throws SmsException {
		AjahUtils.requireParam(message, "message");
		if (message.length() > 160) {
			throw new IllegalArgumentException("Message must be 160 characters or less");
		}
		SmsFactory smsFactory = this.mainAccount.getSmsFactory();
		Map<String, String> smsParams = new HashMap<>();
		smsParams.put("To", to); // Replace with a valid phone number
		smsParams.put("From", this.defaultSender); // Replace with a valid phone
		smsParams.put("Body", message);
		try {
			smsFactory.create(smsParams);
		} catch (TwilioRestException e) {
			throw new SmsException(e);
		}
	}

	/**
	 * Fetches inbound messages.
	 */
	public void getInboundMessages() {
		// SmsList messages = this.mainAccount.getSmsMessages();
		// log.warning(messages.getTotal() + " messages");
		// for (Sms message : messages) {
		// log.warning(message.getFrom() + ": " + message.getBody());
		// }
		//
	}

	/**
	 * Fetches outbound messages.
	 */
	public void getOutboundMessages() {
	}

}
