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
package com.ajah.util.text;

import java.text.NumberFormat;

import com.ajah.util.IdentifiableEnum;

/**
 * An enum of common formats, so we can generically store data in a BigDecimal
 * format, but maintain formatting rules separately.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum BigDecimalFormat implements IdentifiableEnum<String> {

	/**
	 * Decimal format, with 0 decimal places.
	 */
	DEC_0("0", "decimal0", "Decimal #", "Decimal format, with 0 decimal places.", NumberFormat.getInstance(), 0),
	/**
	 * Decimal format, with 1 decimal places.
	 */
	DEC_1("1", "decimal1", "Decimal #.#", "Decimal format, with 1 decimal places.", NumberFormat.getInstance(), 1),
	/**
	 * Decimal format, with 2 decimal places.
	 */
	DEC_2("2", "decimal2", "Decimal #.##", "Decimal format, with 2 decimal places.", NumberFormat.getInstance(), 2),
	/**
	 * Decimal format, with 3 decimal places.
	 */
	DEC_3("3", "decimal3", "Decimal #.###", "Decimal format, with 3 decimal places.", NumberFormat.getInstance(), 3),
	/**
	 * Decimal format, with 4 decimal places.
	 */
	DEC_4("4", "decimal4", "Decimal #.####", "Decimal format, with 4 decimal places.", NumberFormat.getInstance(), 4),
	/**
	 * A one-time event. Example: Perfect score.
	 */
	CURRENCY_0("20", "currency0", "Currency #", "Currency format, with 0 decimal places.", NumberFormat.getCurrencyInstance(), 0),
	/**
	 * A one-time event. Example: Perfect score.
	 */
	CURRENCY_1("21", "currency0", "Currency #.#", "Currency format, with 1 decimal places.", NumberFormat.getCurrencyInstance(), 1),
	/**
	 * A one-time event. Example: Perfect score.
	 */
	CURRENCY_2("22", "currency0", "Currency #.##", "Currency format, with 2 decimal places.", NumberFormat.getCurrencyInstance(), 2),
	/**
	 * A one-time event. Example: Perfect score.
	 */
	CURRENCY_3("23", "currency0", "Currency #.###", "Currency format, with 3 decimal places.", NumberFormat.getCurrencyInstance(), 3),
	/**
	 * A one-time event. Example: Perfect score.
	 */
	CURRENCY_4("24", "currency0", "Currency #.###", "Currency format, with 4 decimal places.", NumberFormat.getCurrencyInstance(), 4);

	/**
	 * Finds a AchievementType that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching AchievementType, or null.
	 */
	public static BigDecimalFormat get(final String string) {
		for (final BigDecimalFormat type : values()) {
			if (type.getId().equals(string) || type.getCode().equals(string) || type.name().equals(string) || type.getName().equals(string)) {
				return type;
			}
		}
		return null;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String description;
	private NumberFormat numberFormat;
	private int maxDecimalPlaces;

	private BigDecimalFormat(final String id, final String code, final String name, final String description, final NumberFormat numberFormat, final int maxDecimalPlaces) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.numberFormat = numberFormat;
		this.maxDecimalPlaces = maxDecimalPlaces;
		this.numberFormat.setMaximumFractionDigits(this.maxDecimalPlaces);
	}

	/**
	 * The short, display-friendly code of the type. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the type. Cannot be null.
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * The display-friendly description of the type.
	 * 
	 * @return The display-friendly description of the type. May be null.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * The internal ID of the type.
	 * 
	 * @return The internal ID of the type. Cannot be null.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The display-friendly name of the type. If no name is applicable, it
	 * should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the type. Cannot be null.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
