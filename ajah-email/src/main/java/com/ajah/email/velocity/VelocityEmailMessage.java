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
package com.ajah.email.velocity;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.ajah.email.EmailMessage;
import com.ajah.util.StringUtils;
import com.ajah.util.data.format.EmailAddress;

/**
 * This is an implementation of {@link EmailMessage} that uses one or two
 * Velocity templates and a model to construct the body of the message.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class VelocityEmailMessage implements EmailMessage {

	private EmailAddress from;
	private EmailAddress[] to;
	private Map<String, Object> model = new HashMap<String, Object>();
	private String subject;
	private String textTemplate;
	private String htmlTemplate;
	private VelocityEngine velocityEngine = new VelocityEngine();

	/**
	 * Constructs the message and initializes the {@link VelocityEngine}.
	 * 
	 * @param from
	 *            The From address
	 * @param to
	 *            The To addresses
	 */
	public VelocityEmailMessage(EmailAddress from, EmailAddress[] to) {
		this.from = from;
		this.to = to;
		this.velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		this.velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		this.velocityEngine.init();
	}

	/**
	 * Constructs the message from the text template.
	 * 
	 * @see VelocityEngine#mergeTemplate(String, String,
	 *      org.apache.velocity.context.Context, java.io.Writer)
	 * @see com.ajah.email.EmailMessage#getText()
	 * @return The output of the merged velocity template, if the text template
	 *         is null, will return null.
	 */
	@Override
	public String getText() {
		if (StringUtils.isBlank(this.textTemplate)) {
			return null;
		}
		VelocityContext context = new VelocityContext(this.model);
		StringWriter w = new StringWriter();
		this.velocityEngine.mergeTemplate(this.textTemplate, "UTF-8", context, w);
		return w.toString();

	}

	/**
	 * Constructs the message from the HTML template.
	 * 
	 * @see VelocityEngine#mergeTemplate(String, String,
	 *      org.apache.velocity.context.Context, java.io.Writer)
	 * @see com.ajah.email.EmailMessage#getHtml()
	 * @return The output of the merged velocity template, if the text template
	 *         is null, will return null.
	 */
	@Override
	public String getHtml() {
		if (StringUtils.isBlank(this.htmlTemplate)) {
			return null;
		}
		VelocityContext context = new VelocityContext(this.model);
		StringWriter w = new StringWriter();
		this.velocityEngine.mergeTemplate(this.htmlTemplate, "UTF-8", context, w);
		return w.toString();

	}

}