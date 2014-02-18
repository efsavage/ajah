/*
 * PROPRIETARY and CONFIDENTIAL
 * 
 * Copyright 2014 MondoGoal
 * 
 * All rights reserved.
 */
package com.ajah.util.data;

import com.ajah.util.IdentifiableEnum;

/**
 * Valid genders.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum Gender implements IdentifiableEnum<String> {

	/**
	 * Unknown/Not applicable.
	 */
	UNKNOWN("0", "x", "Unknown", "unknown"),
	/**
	 * Male.
	 */
	MALE("1", "m", "Male", "male", "man", "boy"),
	/**
	 * Female.
	 */
	FEMALE("2", "f", "Female", "female", "woman", "girl"),
	/**
	 * Undisclosed or unisex.
	 */
	UNDISCLOSED("3", "u", "Undisclosed", "private");

	/**
	 * Finds a Gender that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching Gender, or null.
	 */
	public static Gender get(final String string) {
		// Try normal fields first
		for (final Gender gender : values()) {
			if (gender.getId().equals(string) || gender.getCode().equals(string) || gender.name().equals(string) || gender.getName().equals(string)) {
				return gender;
			}
		}
		// Try alternates
		for (final Gender gender : values()) {
			for (final String alternate : gender.alternates) {
				if (alternate.equalsIgnoreCase(string)) {
					return gender;
				}
			}
		}
		return null;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String[] alternates;

	private Gender(final String id, final String code, final String name, final String... alternates) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.alternates = alternates;
	}

	/**
	 * The short, display-friendly code of the gender. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the gender. Cannot be null.
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * The internal ID of the gender.
	 * 
	 * @return The internal ID of the gender. Cannot be null.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The display-friendly name of the gender. If no name is applicable, it
	 * should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the gender. Cannot be null.
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
