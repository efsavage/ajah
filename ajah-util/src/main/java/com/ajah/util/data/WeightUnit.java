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

/**
 * A list of weight units with a baseline value (nanogram) for conversions.
 * Units that are actually mass units are based on standard gravity.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public enum WeightUnit {

	/**
	 * Nanogram
	 */
	NANOGRAM(1),
	/**
	 * Microgram
	 */
	MICROGRAM(1000),
	/**
	 * Microgram
	 */
	MILLIGRAM(1_000_000),
	/**
	 * Gram
	 */
	GRAM(1_000_000_000),
	/**
	 * Kilogram
	 */
	KILOGRAM(1_000_000_000_000L),
	/**
	 * Ounce
	 */
	OUNCE(28_349_523_125L);

	private final long nanograms;

	private WeightUnit(long nanograms) {
		this.nanograms = nanograms;
	}

	/**
	 * Returns this unit in nanograms, the smallest unit supported by this
	 * class.
	 * 
	 * @return the nanogram equivalent of one of this unit.
	 */
	public long getNanograms() {
		return this.nanograms;
	}

}
