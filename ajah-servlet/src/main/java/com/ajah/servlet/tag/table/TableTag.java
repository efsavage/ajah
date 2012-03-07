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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.util.StringUtils;

/**
 * Constructs an HTML table.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TableTag extends TagSupport {

	private String cssClass;

	/**
	 * Creates a closing table tag.
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			this.pageContext.getOut().write("</table>");
			return Tag.EVAL_PAGE;
		} catch (final IOException e) {
			throw new JspException(e);
		}
	}

	/**
	 * Creates an opening table tag.
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			this.pageContext.getOut().write("<table");
			if (!StringUtils.isBlank(this.id)) {
				this.pageContext.getOut().write(" id=\"");
				this.pageContext.getOut().write(this.id);
				this.pageContext.getOut().write("\"");
			}
			if (!StringUtils.isBlank(this.cssClass)) {
				this.pageContext.getOut().write(" class=\"");
				this.pageContext.getOut().write(this.cssClass);
				this.pageContext.getOut().write("\"");
			}
			this.pageContext.getOut().write(">");
			return Tag.EVAL_BODY_INCLUDE;
		} catch (final IOException e) {
			throw new JspException(e);
		}
	}

}
