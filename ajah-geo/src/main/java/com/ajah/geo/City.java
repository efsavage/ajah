package com.ajah.geo;

/**
 * Represents a city/town/village/etc.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface City {

	/**
	 * The internal ID of the city.
	 * 
	 * @return The internal ID. Should never be null or empty.
	 */
	String getId();

	/**
	 * The common name of the city.
	 * 
	 * Example: The name of Boston, Massachusetts, USA would be "Boston", not
	 * the official "The City of Boston".
	 * 
	 * @return The common name of the city. Should never be null or empty.
	 */
	String getName();

	/**
	 * The state of which this city is a part, if applicable. Since not all
	 * countries have states, this may be null.
	 * 
	 * Example: The city of Boston, Massachusetts, USA would be USState.MA
	 * 
	 * @return The State object for this city's state. May be null or empty.
	 */
	State getState();

	/**
	 * The country of which this state is a part. If in a country that has
	 * states, it should pass through to getState().getCountry() for
	 * consistency.
	 * 
	 * Example: The country of Boston, Massachusetts, USA would be
	 * ISOCountry.USA
	 * 
	 * @return The Country object for this city's nation. Should never be null
	 *         or empty.
	 */
	Country getCountry();

}