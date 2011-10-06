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
package com.ajah.servlet.tag.table;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.html.element.H3;
import com.ajah.html.element.Paragraph;
import com.ajah.util.StringUtils;

/**
 * Acts like c:forEach but with additional functionality like ifEmpty text and
 * titles. This presumably could be done in a .tag file, but I ran into too many
 * issues with type coercion to make it practical.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ForEachTag extends TagSupport {

	private static final Logger log = Logger.getLogger(ForEachTag.class.getName());

	private Object items;
	private String ifEmpty;
	private String var;
	private String title;
	private Iterator<?> iterator = null;

	/**
	 * Creates an opening table tag.
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			if (!StringUtils.isBlank(this.title)) {
				new H3().text(this.title).render(this.pageContext.getOut(), 0);
			}
			if (isEmpty(this.items)) {
				if (!StringUtils.isBlank(this.ifEmpty)) {
					Paragraph p = new Paragraph().text(this.ifEmpty);
					p.render(this.pageContext.getOut(), 0);
				}
				return SKIP_BODY;
			}
			if (this.items instanceof Iterable) {
				this.iterator = ((Iterable<?>) this.items).iterator();
				if (this.iterator.hasNext()) {
					Object item = this.iterator.next();
					if (!StringUtils.isBlank(this.var)) {
						this.pageContext.setAttribute(this.var, item);
					}
					log.finest("Var " + this.var + " is a " + this.pageContext.getAttribute(this.var).getClass().getName());
					return EVAL_BODY_INCLUDE;
				}
			}
			return SKIP_BODY;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doAfterBody() throws JspException {
		log.finest("doAfterBody");
		if (this.iterator != null && this.iterator.hasNext()) {
			Object item = this.iterator.next();
			if (!StringUtils.isBlank(this.var)) {
				this.pageContext.setAttribute(this.var, item);
				log.finest("Var " + this.var + " is a " + this.pageContext.getAttribute(this.var).getClass().getName());
			}
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	/**
	 * @param items2
	 * @return
	 */
	private static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof Collection<?>) {
			if (((Collection<?>) object).size() > 0) {
				return false;
			}
		}
		log.warning("Cannot handle items attribute of class: " + object.getClass().getName());
		return true;
	}
}
