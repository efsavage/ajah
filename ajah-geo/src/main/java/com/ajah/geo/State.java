package com.ajah.geo;

/**
 * The State interface represents a country subdivision, such as state in the US
 * or province in Canada.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public interface State {

	/**
	 * The internal ID of the state. Is not dictated by any external/official
	 * nomenclature, although it may match one for the sake of readability.
	 * 
	 * Example: The ID of Massachusetts, USA might be "ma".
	 * 
	 * @return The internal ID. Should never be null or empty.
	 */
	String getId();

	/**
	 * The common name of the state.
	 * 
	 * Example: The name of Massachusetts, USA would be "Massachusetts", not the
	 * official "The Commonwealth of Massachusetts".
	 * 
	 * @return The common name of the state. Should never be null or empty.
	 */
	String getName();

	/**
	 * The public abbreviation of the state. Should match the official
	 * nomenclature, typically defined by postal services.
	 * 
	 * Example: The abbreviation of Massachusetts, USA would be "MA".
	 * 
	 * @return The public abbreviation of the state. Should never be null or
	 *         empty.
	 */
	String getAbbr();

	/**
	 * The country of which this state is a part.
	 * 
	 * Example: The country of Massachusetts, USA would be ISOCountry.USA
	 * 
	 * @return The Country object for this state's nation. Should never be null
	 *         or empty.
	 */
	Country getCountry();

}
