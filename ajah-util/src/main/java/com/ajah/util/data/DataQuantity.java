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
package com.ajah.util.data;

import java.math.BigInteger;

/**
 * Bean to represent a quantity of data. Constructed with a unit and quantity,
 * can be converted to other units. Useful where quantities cannot be expressed
 * in bytes without using {@link BigInteger}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class DataQuantity {

	private final long quantity;

	private final DataSizeUnit unit;

	/**
	 * Constructs this bean with the required fields, the unit this quantity is
	 * expressed in, and the quantity of that unit.
	 * 
	 * @param quantity
	 * @param unit
	 */
	public DataQuantity(final long quantity, final DataSizeUnit unit) {
		this.quantity = quantity;
		this.unit = unit;
	}

	/**
	 * The quantity of the base unit of this bean.
	 * 
	 * @return The quantity of the base unit of this bean.
	 */
	public long getQuantity() {
		return this.quantity;
	}

	/**
	 * The unit this bean was constructed as a quantity of.
	 * 
	 * @return The unit this bean was constructed as a quantity of.
	 */
	public DataSizeUnit getUnit() {
		return this.unit;
	}

}
