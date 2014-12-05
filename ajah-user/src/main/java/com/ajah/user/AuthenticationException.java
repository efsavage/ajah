package com.ajah.user;

/**
 * Superclass that encompasses various authentication errors.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public abstract class AuthenticationException extends Exception {

	/**
	 * @see Exception#Exception()
	 */
	public AuthenticationException() {
		super();
	}

	/**
	 * @see Exception#Exception(String)
	 */
	public AuthenticationException(final String message) {
		super(message);
	}

}
