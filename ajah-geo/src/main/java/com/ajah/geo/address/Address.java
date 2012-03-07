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
package com.ajah.geo.address;

import com.ajah.geo.City;

/**
 * Represents a physical mailing address. This interface should apply to any
 * country.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public interface Address {

	/**
	 * Line 1 of the address. A null value here will likely not be a valid
	 * address for most systems.
	 * 
	 * @return Line 1 of the address. May be null.
	 */
	String getAddress1();

	/**
	 * Line 2 of the address.
	 * 
	 * @return Line 2 of the address. May be null.
	 */
	String getAddress2();

	/**
	 * Line 3 of the address.
	 * 
	 * @return Line 3 of the address. May be null.
	 */
	String getAddress3();

	/**
	 * Line 4 of the address.
	 * 
	 * @return Line 4 of the address. May be null.
	 */
	String getAddress4();

	/**
	 * City where the address is located. State and country can be derived from
	 * this. A null value here will likely not be a valid address for most
	 * systems.
	 * 
	 * @return City where the address is located. May be null.
	 */
	City getCity();

	/**
	 * Postal code for the address.
	 * 
	 * @return Postal code for the address. May be null.
	 */
	PostalCode getPostalCode();

}
