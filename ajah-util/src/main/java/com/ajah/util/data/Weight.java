/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a quantity of a weight with a unit.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weight {

	private BigDecimal quanity;
	private WeightUnit unit;

	/**
	 * Public constructor for doubles. The internal representation is still a
	 * {@link BigDecimal}.
	 * 
	 * @param quantity
	 *            The quantity of the weight.
	 * @param unit
	 *            The unit of the weight.
	 */
	public Weight(final double quantity, final WeightUnit unit) {
		this.quanity = new BigDecimal(quantity);
		this.unit = unit;
	}

	/**
	 * Converts this weight to a weight in a different unit.
	 * 
	 * @param newUnit
	 *            The unit to convert to.
	 * @return The converted weight.
	 */
	public Weight to(final WeightUnit newUnit) {
		final BigDecimal nanograms = this.quanity.multiply(new BigDecimal(this.unit.getNanograms()));
		return new Weight(nanograms.divide(new BigDecimal(newUnit.getNanograms()), 12, RoundingMode.HALF_UP), newUnit);
	}

}
