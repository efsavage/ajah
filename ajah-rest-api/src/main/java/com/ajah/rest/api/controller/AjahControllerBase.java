package com.ajah.rest.api.controller;

import com.ajah.rest.api.error.EmailFormatException;
import com.ajah.rest.api.error.RequiredRequestParameterException;
import com.ajah.util.StringUtils;
import com.ajah.util.Validate;

/**
 * A Controller Base is a "just add mapping" Spring MVC controller. The mappings
 * are omitted so that the URLs don't register on a component/classpath scan.
 * AjahControllerBase serves as a superclass to the other base controllers,
 * which should be included as properties of application-specific controllers.
 * AjahControllerBase can also serve as a superclass for application-specific
 * controllers depending on the preferred method of accessing the utility
 * methods contained here.
 * 
 * @author Eric F. Savage
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>,
 *         <a href="https://github.com/efsavage">github.com/efsavage</a>.
 *
 */
@SuppressWarnings("static-method")
public class AjahControllerBase {

	/**
	 * Require a parameter, either on the request or as part of a submitted
	 * model.
	 * 
	 * @param name
	 *            The property name, this is for human messaging only and is not
	 *            used for any introspection.
	 * @param value
	 *            The current value, may be null.
	 * @throws RequiredRequestParameterException
	 *             If the value is null, or if the value is an empty string.
	 */
	protected void requireParameter(final String name, final Object value) throws RequiredRequestParameterException {
		if (value == null) {
			throw new RequiredRequestParameterException(name);
		}
		if (String.class.isAssignableFrom(value.getClass())) {
			requireParameter(name, (String) value);
		}
	}

	/**
	 * Require a string parameter, either on the request or as part of a
	 * submitted model.
	 * 
	 * @param name
	 *            The property name, this is for human messaging only and is not
	 *            used for any introspection.
	 * @param value
	 *            The current value, may be empty or null.
	 * @throws RequiredRequestParameterException
	 *             If the value is null, or if the value is an empty string.
	 */
	public void requireParameter(final String name, final String value) throws RequiredRequestParameterException {
		if (StringUtils.isBlank(value)) {
			throw new RequiredRequestParameterException(name);
		}
	}

	/**
	 * Require a string parameter to be a valid email format, either on the
	 * request or as part of a submitted model.
	 * 
	 * @param name
	 *            The property name, this is for human messaging only and is not
	 *            used for any introspection.
	 * @param value
	 *            The current value, may be empty or null.
	 * @throws RequiredRequestParameterException
	 *             If the value is null, an empty string.
	 * @throws EmailFormatException
	 *             If the value is not-null, and not an empty string, but is not
	 *             a valid email format.
	 * @see Validate#isEmail(String)
	 */
	protected void requireEmail(final String name, final String value) throws RequiredRequestParameterException, EmailFormatException {
		requireParameter(name, value);
		if (!Validate.isEmail(value)) {
			throw new EmailFormatException(name);
		}
	}

}
