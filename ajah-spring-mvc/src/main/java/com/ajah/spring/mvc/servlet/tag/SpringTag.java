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
package com.ajah.spring.mvc.servlet.tag;

import java.util.Locale;
import java.util.logging.Level;

import javax.servlet.jsp.tagext.TagSupport;

import lombok.extern.java.Log;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

import com.ajah.util.StringUtils;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public abstract class SpringTag extends TagSupport {


	protected String getMessage(final MessageSourceResolvable resolvable) {
		final ApplicationContext appContext = (ApplicationContext) this.pageContext.getServletContext().getAttribute("appContext");
		final MessageSource messageSource = appContext.getBean(MessageSource.class);
		if (log.isLoggable(Level.FINEST)) {
			log.finest(StringUtils.join(resolvable.getCodes()));
		}
		return messageSource.getMessage(resolvable, Locale.getDefault());
	}

}
