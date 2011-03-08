package com.ajah.geo;

/**
 * Represents a country/nation/sovereign state.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public interface Country {

	/**
	 * The internal ID of the country. Is not dictated by any external/official
	 * nomenclature, although it may match one for the sake of readability.
	 * 
	 * Example: The ID of the United States might be "us".
	 * 
	 * @return The internal ID. Should never be null or empty.
	 */
	public String getId();

	/**
	 * The public abbreviation of the state. Is not dictated by the official
	 * nomenclature, but may match one for consistency such as ISO or those
	 * defined by postal services.
	 * 
	 * Example: The abbreviation of the United States might be "USA".
	 * 
	 * @return The public abbreviation of the country. Should never be null or
	 *         empty.
	 */
	public String getAbbr();

	/**
	 * The common name of the country.
	 * 
	 * Example: The name of The United States of America would be
	 * "United States", not the official "The United States of America".
	 * 
	 * @return The common name of the country. Should never be null or empty.
	 */
	public String getName();

}
