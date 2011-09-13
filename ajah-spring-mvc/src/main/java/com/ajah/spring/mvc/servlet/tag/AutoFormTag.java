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

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import javax.validation.constraints.NotNull;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ajah.html.dtd.FormMethod;
import com.ajah.html.dtd.InputType;
import com.ajah.html.element.Checkbox;
import com.ajah.html.element.Div;
import com.ajah.html.element.Form;
import com.ajah.html.element.Input;
import com.ajah.html.element.InputImpl;
import com.ajah.html.element.ListItem;
import com.ajah.html.element.UnorderedList;
import com.ajah.spring.mvc.form.AutoForm;
import com.ajah.spring.mvc.form.AutoFormUtils;
import com.ajah.spring.mvc.form.Submit;
import com.ajah.spring.mvc.form.validation.Match;
import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;
import com.ajah.util.crypto.Password;
import com.ajah.util.data.format.EmailAddress;

/**
 * Renders an AutoForm.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class AutoFormTag extends TagSupport {

	private static final Logger log = Logger.getLogger(AutoFormTag.class.getName());

	private static final long serialVersionUID = -5998541642357295851L;

	private Object autoForm;

	private boolean compact = false;

	/**
	 * Denotes if whitespace should be added for readability.
	 * 
	 * @return true if whitespace should be removed (default), otherwise false
	 */
	public boolean isCompact() {
		return this.compact;
	}

	/**
	 * Denotes if whitespace should be added for readability.
	 * 
	 * @param compact
	 *            true if whitespace should be removed, otherwise false
	 */
	public void setCompact(boolean compact) {
		this.compact = compact;
	}

	/**
	 * Returns the autoForm for this tag to render.
	 * 
	 * @return The autoForm for this tag to render. May be null.
	 */
	public Object getAutoForm() {
		return this.autoForm;
	}

	/**
	 * Sets the autoForm for this tag to render.
	 * 
	 * @param autoForm
	 *            The autoForm for this tag to render, required.
	 */
	public void setAutoForm(Object autoForm) {
		AjahUtils.requireParam(autoForm, "autoForm");
		if (autoForm.getClass().isAnnotationPresent(AutoForm.class)) {
			this.autoForm = autoForm;
		} else {
			throw new IllegalArgumentException(autoForm.getClass().getName() + " does not have " + AutoForm.class.getName() + " annotation");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doStartTag() throws JspException {
		if (this.autoForm == null) {
			setAutoForm(this.pageContext.getRequest().getAttribute("ajahAutoForm"));
		}
		AjahUtils.requireParam(this.autoForm, "autoForm");
		try {
			JspWriter out = this.pageContext.getOut();
			Div div = new Div().css("asm-auto");

			Enumeration<String> attributes = this.pageContext.getRequest().getAttributeNames();
			while (attributes.hasMoreElements()) {
				String attribute = attributes.nextElement();
				if (attribute.startsWith("org.springframework.validation.BindingResult.")) {
					BindingResult result = (BindingResult) this.pageContext.getRequest().getAttribute(attribute);
					log.fine("Found " + result.getErrorCount() + " global errors");
					UnorderedList errs = div.add(new UnorderedList().css("asm-err"));
					for (ObjectError error : result.getAllErrors()) {
						ResourceBundle rb = ResourceBundle.getBundle("/AutoFormValidation");
						String message = error.getDefaultMessage();
						if (message.startsWith("{")) {
							message = rb.getString(error.getDefaultMessage().substring(1, message.length() - 1));
						}
						for (int i = 0; i < (error.getArguments() == null ? 0 : error.getArguments().length); i++) {
							message = message.replaceAll("\\{" + i + "\\}", error.getArguments()[i].toString());
						}
						errs.add(new ListItem(message));
					}
				}
			}

			Form form = new Form(FormMethod.POST).css("asm-auto");
			for (Field field : this.autoForm.getClass().getFields()) {
				log.fine(field.getName());
				Input<?> input = getInput(field, this.autoForm.getClass().getFields());
				form.getInputs().add(input);
			}
			String submitText = null;
			if (this.autoForm.getClass().isAnnotationPresent(Submit.class)) {
				submitText = this.autoForm.getClass().getAnnotation(Submit.class).value();
			}
			if (StringUtils.isBlank(submitText)) {
				submitText = StringUtils.capitalize(StringUtils.splitCamelCase(this.autoForm.getClass().getSimpleName().replaceAll("Form$", "")));
			}
			Input<InputImpl> input = new InputImpl("submit", submitText, InputType.SUBMIT);
			form.getInputs().add(input);
			div.add(form);
			div.render(out, isCompact() ? -1 : 0);
		} catch (IOException e) {
			throw new JspException(e);
		} catch (IllegalArgumentException e) {
			throw new JspException(e);
		} catch (IllegalAccessException e) {
			throw new JspException(e);
		}
		return Tag.EVAL_PAGE;
	}

	private Input<?> getInput(Field field, Field[] allFields) throws IllegalArgumentException, IllegalAccessException {

		String label = AutoFormUtils.getLabel(field);

		Input<?> input = null;
		log.fine(field.getType().toString());
		// TODO handle type="email" and such http://diveintohtml5.org/forms.html
		if (field.getType().equals(String.class)) {
			input = new InputImpl(label, field.getName(), (String) field.get(this.autoForm), InputType.TEXT);
		} else if (field.getType().equals(EmailAddress.class)) {
			input = new InputImpl(label, field.getName(), StringUtils.safeToString(field.get(this.autoForm)), InputType.TEXT);
		} else if (field.getType().isAssignableFrom(Password.class)) {
			input = new InputImpl(label, field.getName(), StringUtils.safeToString(field.get(this.autoForm)), InputType.PASSWORD);
		} else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
			input = new Checkbox(label, field.getName(), "true", field.getBoolean(this.autoForm));
		} else if (field.getType().equals(Long.class) || field.getType().equals(long.class) || field.getType().equals(Integer.class)
				|| field.getType().equals(int.class)) {
			input = new InputImpl(label, field.getName(), StringUtils.safeToString(field.get(this.autoForm)), InputType.TEXT);
		} else {
			throw new IllegalArgumentException(field.getType().getName() + " not supported");
		}
		if (field.isAnnotationPresent(NotNull.class)) {
			log.fine("NotNull is present on " + field.getName());
			input.css("required");
		} else {
			log.fine("NotNull is NOT present on " + field.getName());
		}
		if (field.isAnnotationPresent(Match.class)) {
			log.fine("Match is present on " + field.getName());
			input.data("match", field.getAnnotation(Match.class).value());
			Field target = findField(field.getAnnotation(Match.class).value(), allFields);
			if (target == null) {
				throw new IllegalArgumentException("No field called " + field.getAnnotation(Match.class).value() + " found to match on");
			}
			input.data("match-name", AutoFormUtils.getLabel(target));
		} else {
			log.fine("Match is NOT present on " + field.getName());
		}
		return input;
	}

	/**
	 * Finds a field by name.
	 * 
	 * @param target
	 *            The name of the field to target.
	 * @param allFields
	 *            The list of fields to search across.
	 * @return Matching field, if found, otherwise null.
	 */
	private static Field findField(String target, Field[] allFields) {
		for (Field field : allFields) {
			if (field.getName().equals(target)) {
				return field;
			}
		}
		return null;
	}

}