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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.validation.constraints.NotNull;

import lombok.extern.java.Log;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ajah.crypto.Password;
import com.ajah.html.dtd.ButtonType;
import com.ajah.html.dtd.FormMethod;
import com.ajah.html.dtd.InputType;
import com.ajah.html.element.Button;
import com.ajah.html.element.Checkbox;
import com.ajah.html.element.Div;
import com.ajah.html.element.Form;
import com.ajah.html.element.Input;
import com.ajah.html.element.InputImpl;
import com.ajah.html.element.Italic;
import com.ajah.html.element.ListItem;
import com.ajah.html.element.Option;
import com.ajah.html.element.Script;
import com.ajah.html.element.Select;
import com.ajah.html.element.TextArea;
import com.ajah.html.element.UnorderedList;
import com.ajah.spring.mvc.form.AutoForm;
import com.ajah.spring.mvc.form.AutoFormUtils;
import com.ajah.spring.mvc.form.HtmlText;
import com.ajah.spring.mvc.form.Icon;
import com.ajah.spring.mvc.form.LongText;
import com.ajah.spring.mvc.form.Submit;
import com.ajah.spring.mvc.form.validation.Match;
import com.ajah.util.AjahUtils;
import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;
import com.ajah.util.data.format.EmailAddress;
import com.ajah.util.reflect.IntrospectionUtils;

/**
 * Renders an AutoForm.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Log
public class AutoFormTag extends SpringTag {

	/**
	 * Finds a field by name.
	 * 
	 * @param target
	 *            The name of the field to target.
	 * @param allFields
	 *            The list of fields to search across.
	 * @return Matching field, if found, otherwise null.
	 */
	private static Field findField(final String target, final Field[] allFields) {
		for (final Field field : allFields) {
			if (field.getName().equals(target)) {
				return field;
			}
		}
		return null;
	}

	private Object autoForm;

	private boolean compact = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doStartTag() throws JspException {
		if (this.autoForm == null) {
			setAutoForm(this.pageContext.getRequest().getAttribute("ajahAutoForm"));
		}
		AjahUtils.requireParam(this.autoForm, "autoForm");
		try (JspWriter out = this.pageContext.getOut()) {
			final Div div = new Div().css("asm-auto");

			final Enumeration<String> attributes = this.pageContext.getRequest().getAttributeNames();
			while (attributes.hasMoreElements()) {
				final String attribute = attributes.nextElement();
				if (attribute.startsWith("org.springframework.validation.BindingResult.")) {
					final BindingResult result = (BindingResult) this.pageContext.getRequest().getAttribute(attribute);
					log.fine("Found " + result.getErrorCount() + " global errors");
					if (result.getErrorCount() > 0) {
						final Div alertBox = div.add(new Div().css("alert").css("alert-error"));
						final UnorderedList errs = alertBox.add(new UnorderedList().css("asm-err"));
						for (final ObjectError error : result.getAllErrors()) {
							errs.add(new ListItem(getMessage(error)));
						}
					}
				}
			}

			final Form form = new Form(FormMethod.POST).css("well").css("asm-auto");
			if (!StringUtils.isBlank(getId())) {
				form.setId(getId());
			}
			for (final Field field : this.autoForm.getClass().getFields()) {
				log.fine(field.getName() + " has a value of " + field.get(this.autoForm));
				final Input<?> input = getInput(field, this.autoForm.getClass().getFields());
				form.getInputs().add(input);
			}

			String submitText = null;
			Icon iconLeft = null;
			Icon iconRight = null;
			if (this.autoForm.getClass().isAnnotationPresent(Submit.class)) {
				final Submit submit = this.autoForm.getClass().getAnnotation(Submit.class);
				submitText = submit.value();
				if (submit.iconLeft() != null && submit.iconLeft() != Icon.NONE) {
					iconLeft = submit.iconLeft();
				}
				if (submit.iconRight() != null && submit.iconRight() != Icon.NONE) {
					iconRight = submit.iconRight();
				}
			}
			if (StringUtils.isBlank(submitText)) {
				submitText = StringUtils.capitalize(StringUtils.splitCamelCase(this.autoForm.getClass().getSimpleName().replaceAll("Form$", "")));
			}
			final Button submitButton = new Button().text(submitText).type(ButtonType.SUBMIT).css("btn").css("btn-primary");
			if (iconLeft != null) {
				submitButton.addBeforeText(new Italic().css(iconLeft.getBootstrapClass()));
			}
			if (iconRight != null) {
				submitButton.add(new Italic().css(iconRight.getBootstrapClass()));
			}
			form.getInputs().add(submitButton);
			div.add(form);

			final Script script = new Script();
			final StringBuilder code = new StringBuilder();
			code.append("\n$(document).ready(function() {\n");
			for (final Input<?> child : form.getInputs()) {
				if (child instanceof TextArea && ((TextArea) child).isHtml()) {
					this.pageContext.getRequest().setAttribute("jjScriptHtmlEditor", Boolean.TRUE);
					code.append("\t$(\".rich-text"
							+ "\").cleditor({width:\"95%\", height:\"100%\", controls: \"style bold italic strikethrough | bullets numbering | outdent indent | rule image link unlink | removeformat source\"});\n");
					break;
				}
			}
			code.append("\t$(\"#" + form.getInputs().get(0).getId() + "\").focus();\n");
			code.append("});\n");
			script.setText(code.toString());
			div.add(script);

			div.render(out, isCompact() ? -1 : 0);
		} catch (final IOException | IllegalAccessException | IllegalArgumentException e) {
			throw new JspException(e);
		}
		return Tag.EVAL_PAGE;
	}

	/**
	 * Returns the autoForm for this tag to render.
	 * 
	 * @return The autoForm for this tag to render. May be null.
	 */
	public Object getAutoForm() {
		return this.autoForm;
	}

	private Input<?> getInput(final Field field, final Field[] allFields) throws IllegalArgumentException, IllegalAccessException {

		final String label = AutoFormUtils.getLabel(field);

		Input<?> input = null;
		log.fine(field.getType().toString());
		// TODO handle type="email" and such http://diveintohtml5.org/forms.html
		if (field.getType().equals(String.class)) {
			if (field.isAnnotationPresent(HtmlText.class)) {
				// An HTML-enabled textarea input
				input = new TextArea(label, field.getName(), (String) field.get(this.autoForm), AutoFormUtils.getInputType(field), true);
			} else if (field.isAnnotationPresent(LongText.class)) {
				// A textarea input
				input = new TextArea(label, field.getName(), (String) field.get(this.autoForm), AutoFormUtils.getInputType(field), false);
			} else {
				// A normal text input
				input = new InputImpl(label, field.getName(), (String) field.get(this.autoForm), AutoFormUtils.getInputType(field));
			}
		} else if (field.getType().equals(EmailAddress.class)) {
			// An email input
			input = new InputImpl(label, field.getName(), StringUtils.safeToString(field.get(this.autoForm)), AutoFormUtils.getInputType(field));
		} else if (field.getType().isAssignableFrom(Password.class)) {
			// A password input
			input = new InputImpl(label, field.getName(), "", InputType.PASSWORD);
		} else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
			// A checkbox input
			input = new Checkbox(label, field.getName(), "true", field.getBoolean(this.autoForm));
		} else if (field.getType().equals(Long.class) || field.getType().equals(long.class) || field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
			// An integer input
			input = new InputImpl(label, field.getName(), StringUtils.safeToString(field.get(this.autoForm)), AutoFormUtils.getInputType(field));
		} else if (field.getType().equals(Double.class)) {
			// An floating point input
			input = new InputImpl(label, field.getName(), StringUtils.safeToString(field.get(this.autoForm)), AutoFormUtils.getInputType(field));
		} else if (IntrospectionUtils.isIdentifiableEnum(field)) {
			final Select select = new Select(label, field.getName());
			final Identifiable<?>[] options = (Identifiable<?>[]) field.getType().getEnumConstants();
			for (final Identifiable<?> enumOption : options) {
				log.fine(enumOption.getId().toString() + " / " + enumOption.toString());
				final Option option = new Option(enumOption.getId().toString(), AutoFormUtils.getLabel(field, enumOption));
				select.add(option);
			}
			input = select;
		} else if (IntrospectionUtils.isToStringable(field) && IntrospectionUtils.isFromStringable(field)) {
			log.finest("label: " + label);
			input = new InputImpl(label, field.getName(), StringUtils.safeToString(field.get(this.autoForm)), AutoFormUtils.getInputType(field));
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
			final Field target = findField(field.getAnnotation(Match.class).value(), allFields);
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
	 * Denotes if whitespace should be added for readability.
	 * 
	 * @return true if whitespace should be removed (default), otherwise false
	 */
	public boolean isCompact() {
		return this.compact;
	}

	/**
	 * Sets the autoForm for this tag to render.
	 * 
	 * @param autoForm
	 *            The autoForm for this tag to render, required.
	 */
	public void setAutoForm(final Object autoForm) {
		AjahUtils.requireParam(autoForm, "autoForm");
		if (autoForm.getClass().isAnnotationPresent(AutoForm.class)) {
			this.autoForm = autoForm;
		} else {
			throw new IllegalArgumentException(autoForm.getClass().getName() + " does not have " + AutoForm.class.getName() + " annotation");
		}
	}

	/**
	 * Denotes if whitespace should be added for readability.
	 * 
	 * @param compact
	 *            true if whitespace should be removed, otherwise false
	 */
	public void setCompact(final boolean compact) {
		this.compact = compact;
	}

}
