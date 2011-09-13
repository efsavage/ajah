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

import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * An extension of MimeMessage with some convenience methods.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class AjahMimeMessage extends MimeMessage{


	/**
	 * Calls {@link MimeMessage#MimeMessage(javax.mail.Session, InputStream)}
	 * with a null session.
	 * 
	 * @param inputStream
	 *            The input stream of the message.
	 * @throws MessagingException
	 *             If the message could not be constructed.
	 */
	public AjahMimeMessage(final InputStream inputStream) throws MessagingException {
		super(null, inputStream);
	}

	

	@Override
	public InputStream getContentStream() throws MessagingException {
		return super.getContentStream();
	}

}
