package com.ajah.geo.us;

/**
 * Thrown when there is an attempt to set a ZIP code to an illegal value.
 * 
 * @see ZipCode
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class IllegalZipCodeFormatException extends IllegalArgumentException {

	/**
	 * @see IllegalArgumentException#IllegalArgumentException(String)
	 * @param zip
	 */
	public IllegalZipCodeFormatException(String zip) {
		super(zip + " is not a valid ZIP code");
	}

	private static final long serialVersionUID = 6367113304959164109L;

}