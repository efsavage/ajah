package com.ajah.geo.us;

import com.ajah.geo.address.PostalCode;
import com.ajah.util.StringUtils;

/**
 * USPS ZIP Code. Format is ##### or #####-####. When printing this, use
 * toString().
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class ZipCode implements PostalCode {

	/**
	 * Returns the 5 digit portion of the ZIP code.
	 * 
	 * @return 5 digit portion of the ZIP code. May be null.
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * Sets the 5 digit portion of the ZIP code. Will allow nulls, otherwise
	 * only accepts 5 digit numeric string.
	 * 
	 * @param zip
	 *            5 digit numeric string, or null.
	 */
	public void setZip(String zip) {
		if (zip != null && !zip.matches("\\d{5}")) {
			throw new IllegalZipCodeFormatException(zip);
		}
		this.zip = zip;
	}

	/**
	 * Returns the 4 digit portion of the ZIP code.
	 * 
	 * @return 4 digit portion of the ZIP code. May be null.
	 */
	public String getZip4() {
		return this.zip4;
	}

	/**
	 * Sets the 4 digit portion of the ZIP code. Will allow nulls, otherwise
	 * only accepts 4 digit numeric string.
	 * 
	 * @param zip4
	 *            4 digit numeric string, or null.
	 */
	public void setZip4(String zip4) {
		if (zip4 != null && !zip4.matches("\\d{4}")) {
			throw new IllegalZipCodeFormatException(zip4);
		}
		this.zip4 = zip4;
	}

	private String zip;
	private String zip4;

	/**
	 * If zip4 property is set, will return 10-character ZIP code. If not, will
	 * return 5-character ZIP code. If zip is null, will return null.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.zip == null || StringUtils.isBlank(this.zip4)) {
			return this.zip;
		}
		return this.zip + "-" + this.zip4;
	}

}