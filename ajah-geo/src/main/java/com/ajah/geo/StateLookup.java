package com.ajah.geo;

import com.ajah.geo.iso.ISOCountry;
import com.ajah.geo.us.CAProvince;
import com.ajah.geo.us.USState;

public class StateLookup {

	public static State get(ISOCountry country, String state) {
		switch (country) {
		case US:
			return USState.get(state);
		case CA:
			return CAProvince.get(state);
		default:
			return null;
		}
	}

}
