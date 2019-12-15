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
package com.ajah.servlet.tag;

import java.util.List;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.ajah.servlet.util.SessionUtils;
import com.ajah.util.CollectionUtils;

/**
 * Sets the confirmation messages on the page context and removes them from the
 * session context. Will only execute the body if there are messages.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class ConfirmationMessagesTag extends TagSupport {

	private String var;

	/**
	 * Sets the confirmation messages on the page context and removes them from
	 * the session context. Will only execute the body if there are messages.
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() {
		final List<String> confirmationMessages = SessionUtils.getConfirmationMessages(this.pageContext.getSession());
		if (CollectionUtils.isEmpty(confirmationMessages)) {
			return Tag.SKIP_BODY;
		}
		this.pageContext.setAttribute(this.var, confirmationMessages);
		SessionUtils.clearConfirmationMessages(this.pageContext.getSession());
		return Tag.EVAL_BODY_INCLUDE;
	}

	/**
	 * Returns the variable to store the list on in the page context.
	 * 
	 * @return The variable to store the list on in the page context.
	 */
	public String getVar() {
		return this.var;
	}

	/**
	 * Sets the variable to store the list on in the page context.
	 * 
	 * @param var
	 *            The variable to store the list on in the page context.
	 */
	public void setVar(final String var) {
		this.var = var;
	}

}
