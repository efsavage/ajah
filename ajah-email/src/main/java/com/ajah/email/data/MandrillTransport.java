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

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import lombok.Data;
import lombok.extern.java.Log;

import org.apache.commons.collections.CollectionUtils;

import com.ajah.email.EmailMessage;
import com.ajah.email.EmailRecipient;
import com.ajah.util.StringUtils;
import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;

/**
 * Transport for the Mandrill service. See {@link MandrillApi} for more
 * information.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
@Data
public class MandrillTransport implements EmailTransport {

	private MandrillApi mandrillApi;

	private String apiKey;

	/**
	 * @see com.ajah.email.data.EmailTransport#send(com.ajah.email.EmailMessage)
	 */
	@Override
	public synchronized void send(final EmailMessage emailMessage) throws AddressException, MessagingException {
		final MandrillMessage message = new MandrillMessage();
		message.setSubject(emailMessage.getSubject());
		message.setHtml(StringUtils.isBlank(emailMessage.getHtml()) ? "" : emailMessage.getHtml());
		if (StringUtils.isBlank(emailMessage.getText())) {
			message.setAutoText(Boolean.TRUE);
		} else {
			message.setText(emailMessage.getText());
		}
		message.setFromEmail(emailMessage.getFrom().toString());
		if (StringUtils.isBlank(emailMessage.getFromName())) {
			message.setFromName(emailMessage.getFromName());
		}

		for (final EmailRecipient emailRecipient : emailMessage.getRecipients()) {
			// Mandrill doesn't support CC, instead you send it to multiple
			// recipients and set the "Preserve Recipients" flag to disclosed
			// them or not.
			if (message.getTo() == null) {
				message.setTo(new ArrayList<Recipient>());
			}
			final Recipient recipient = new Recipient();
			recipient.setEmail(emailRecipient.getAddress().toString());
			recipient.setName(emailRecipient.getName());

			switch (emailRecipient.getType()) {
			case BCC:
				recipient.setType(Recipient.Type.BCC);
				break;
			case CC:
				message.setPreserveRecipients(Boolean.TRUE);
				recipient.setType(Recipient.Type.CC);
				break;
			case TO:
				recipient.setType(Recipient.Type.TO);
				break;
			default:
				recipient.setType(Recipient.Type.TO);
				break;
			}

			message.getTo().add(recipient);
		}

		if (CollectionUtils.isEmpty(emailMessage.getTags())) {
			message.setTags(emailMessage.getTags());
		}

		try {
			final MandrillMessageStatus[] messageStatusReports = new MandrillApi(this.apiKey).messages().send(message, Boolean.FALSE);
			for (final MandrillMessageStatus status : messageStatusReports) {
				log.fine(status.getStatus());
			}
		} catch (MandrillApiError | IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	public void setApiKey(final String apiKey) {
		this.apiKey = apiKey;
		this.mandrillApi = new MandrillApi(apiKey);
	}

	public MandrillTransport() {
	}

	public MandrillTransport(String apiKey) {
		this.apiKey = apiKey;
	}

}
