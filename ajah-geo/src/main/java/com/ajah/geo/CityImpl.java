package com.ajah.geo;

/**
 * Skeleton implementation of City interface. Usable as-is or suitable for
 * extension.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public class CityImpl implements City {

	protected String id;

	protected String name;

	protected State state;

	protected final Country country = null;

	/**
	 * If state is set, will return state.getCountry(), otherwise will return
	 * local country field.
	 * 
	 * @see State#getCountry()
	 * @return Country object as set via setCountry or from state, may be null.
	 */
	@Override
	public Country getCountry() {
		if (this.state == null) {
			return this.country;
		}
		return this.state.getCountry();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State getState() {
		return this.state;
	}

	/**
	 * Sets the ID.
	 * 
	 * @param id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 */
	public void setState(final State state) {
		this.state = state;
	}

}
