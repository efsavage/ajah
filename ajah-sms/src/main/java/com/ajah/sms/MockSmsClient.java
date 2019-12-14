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

import lombok.extern.java.Log;

import com.twilio.sdk.TwilioRestClient;

/**
 * A simple SMS client. Currently wraps {@link TwilioRestClient}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class MockSmsClient implements SmsClient {

	/**
	 * Constructs a client using configuration properties twilio.sid,
	 * twilio.token and twilio.sender.default.
	 */
	public MockSmsClient() {
		// Intentionally empty
	}

	/**
	 * Fetches inbound messages.
	 */
	@Override
	public void getInboundMessages() {
		// Intentionally empty
	}

	/**
	 * Fetches outbound messages.
	 */
	@Override
	public void getOutboundMessages() {
		// Intentionally empty
	}

	/**
	 * Sends an Sms to a phone number.
	 * 
	 * @param to
	 *            The number to send to.
	 * @param message
	 *            The message to send.
	 */
	@Override
	public void send(final String to, final String message) {
		log.info("SMS from " + to + ": " + message + " (using mock client, not sent)");
	}

}
