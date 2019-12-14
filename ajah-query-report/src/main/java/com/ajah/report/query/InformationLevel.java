/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.report.query;

import com.ajah.util.IdentifiableEnum;

/**
 * Denotes the level of various types of information contained in a report.
 * Based on the levels and descriptions defined in <a
 * href="http://www.itl.nist.gov/lab/bulletns/bltnmar04.htm">FIPS 199</a>.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum InformationLevel implements IdentifiableEnum<String> {

	/**
	 * The report contains no personal information, note that this level does
	 * not appear in the standard.
	 */
	NONE("0", "none", "None", "None."),
	/**
	 * The potential impact is low if the loss of confidentiality, integrity, or
	 * availability could be expected to have a limited adverse effect on
	 * organizational operations, organizational assets, or individuals. A
	 * limited adverse effect could mean that the loss of confidentiality,
	 * integrity, or availability might:
	 * 
	 * <ul>
	 * <li>Cause a degradation in mission capability to an extent and duration
	 * that the organization is able to perform its primary functions, but the
	 * effectiveness of the functions is noticeably reduced;</li>
	 * 
	 * <li>Result in minor damage to organizational assets, minor financial
	 * loss, or minor harm to individuals.</li>
	 * </ul>
	 */
	LOW("1", "low", "Low", "Low."),
	/**
	 * The potential impact is moderate if the loss of confidentiality,
	 * integrity, or availability could be expected to have a serious adverse
	 * effect on organizational operations, organizational assets, or
	 * individuals. A serious adverse effect could mean that the loss of
	 * confidentiality, integrity, or availability might:
	 * 
	 * <ul>
	 * <li>Cause a significant degradation in mission capability to an extent
	 * and duration that the organization is able to perform its primary
	 * functions, but the effectiveness of the functions is significantly
	 * reduced;</li>
	 * 
	 * <li>Result in significant damage to organizational assets, significant
	 * financial loss, or significant harm to individuals, but not loss of life
	 * or serious life threatening injuries.</li>
	 * </ul>
	 */
	MODERATE("2", "moderate", "Moderate", "Moderate."),
	/**
	 * The potential impact is high if the loss of confidentiality, integrity,
	 * or availability could be expected to have a severe or catastrophic
	 * adverse effect on organizational operations, organizational assets, or
	 * individuals. A severe or catastrophic adverse effect could mean that the
	 * loss of confidentiality, integrity, or availability might:
	 * 
	 * <ul>
	 * <li>Cause a severe degradation in or loss of mission capability to an
	 * extent and duration that the organization is not able to perform one or
	 * more of its primary functions;</li>
	 * 
	 * <li>Result in major damage to organizational assets, major financial
	 * loss, or severe or catastrophic harm to individuals involving loss of
	 * life or serious life threatening injuries.</li>
	 * </ul>
	 */
	HIGH("3", "high", "High", "High.");

	/**
	 * Finds a QueryReportType that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching QueryReportType, or null.
	 */
	public static InformationLevel get(final String string) {
		for (final InformationLevel type : values()) {
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

	InformationLevel(final String id, final String code, final String name, final String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
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
