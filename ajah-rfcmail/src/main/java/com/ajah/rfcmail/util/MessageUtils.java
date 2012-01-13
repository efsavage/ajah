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
package com.ajah.rfcmail.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ajah.rfcmail.AjahMessage;
import com.ajah.util.StringUtils;
import com.ajah.util.net.AjahMimeType;

/**
 * Utilties for dealing with email messages.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class MessageUtils {

	private static final Logger log = Logger.getLogger(MessageUtils.class.getName());

	/**
	 * Extracts as much text as possible from a message.
	 * 
	 * @param msg
	 *            The message to extract text from.
	 * @return The text that was extracted from a message.
	 * @throws MessagingException
	 *             If the message could not be understood.
	 * @throws IOException
	 *             If the body of the message could not be read.
	 */
	public static String getContentAsText(AjahMessage msg) throws MessagingException, IOException {
		if (msg.getAjahMimeType() == AjahMimeType.TEXT_PLAIN) {
			// Nothing to do here
			return (String) msg.getContent();
		} else if (msg.getAjahMimeType() == AjahMimeType.TEXT_HTML && msg.getContent() instanceof String) {
			try {
				return getHTMLAsText((String) msg.getContent());
			} catch (IllegalArgumentException e) {
				throw new MessagingException((String) msg.getContent(), e);
			}
		}
		throw new MessagingException(msg.getAjahMimeType() + " has a " + msg.getContent().getClass().getName());
	}

	private static String getHTMLAsText(String html) {
		Document doc = Jsoup.parse(html);
		String text = doc.body().text();
		return text;
	}

	/**
	 * Extracts as much text as possible from a message part.
	 * 
	 * @param bodyPart
	 *            The message part to extract text from.
	 * @return The text that was extracted from a message part.
	 * @throws MessagingException
	 *             If the message part could not be understood.
	 * @throws IOException
	 *             If the message part could not be read.
	 */
	public static String getContentAsText(BodyPart bodyPart) throws MessagingException, IOException {
		AjahMimeType ajahMimeType = AjahMimeType.get(bodyPart.getContentType());
		switch (ajahMimeType) {
		case TEXT_PLAIN:
			return (String) bodyPart.getContent();
		case TEXT_HTML:
			return getHTMLAsText((String) bodyPart.getContent());
		default:
			throw new MessagingException("Can't process " + bodyPart.getContentType());
		}
	}

	/**
	 * Determines if the message is text/html.
	 * 
	 * @param msg
	 *            The message to inspect.
	 * @return true if the message is text/html, otherwise false.
	 * @throws MessagingException
	 */
	public static boolean isHtml(AjahMessage msg) throws MessagingException {
		return msg != null && (msg.getAjahMimeType() == AjahMimeType.TEXT_HTML);
	}

	/**
	 * Determines if the message part is text/html.
	 * 
	 * @param bodyPart
	 *            The message part to inspect.
	 * @return true if the message part is text/html, otherwise false.
	 * @throws MessagingException
	 */
	public static boolean isHtml(BodyPart bodyPart) throws MessagingException {
		return bodyPart != null && (AjahMimeType.get(bodyPart.getContentType()) == AjahMimeType.TEXT_HTML);
	}

	/**
	 * Returns the specified header, if it exists. If more than one matching
	 * header exists, returns the first non-blank one. If a
	 * {@link MessagingException} occurs while reading the header, returns null.
	 * 
	 * @param message
	 *            The message to extra headers from, required.
	 * @param headerName
	 *            The headerName to extract, required.
	 * @return The value of the specified header, if it exists.
	 */
	public static String getHeaderSafe(Message message, String headerName) {
		String[] headers;
		try {
			headers = message.getHeader(headerName);
		} catch (MessagingException e) {
			log.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
		if (headers == null || headers.length == 0) {
			return null;
		}
		if (headers.length == 1) {
			return headers[0];
		}
		for (String header : headers) {
			if (!StringUtils.isBlank(header)) {
				return header;
			}
		}
		return null;
	}

	/**
	 * Gets the content of a message as text and removes extra whitespace.
	 * 
	 * @see #getContentAsText(AjahMessage)
	 * @param message
	 * @return The content of a message as text with extra whitespace removed.
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static String getContentAsCleanText(AjahMessage message) throws MessagingException, IOException {
		return removeExtraWhitespace(getContentAsText(message));
	}

	private static String removeExtraWhitespace(String text) {
		String retVal = text.replaceAll("\\\\r", "\\\\n").replaceAll("\\\\n+", "\\\\n");
		return retVal.trim();
	}

}
