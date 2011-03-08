package com.ajah.geo.address;

import lombok.Data;

import com.ajah.geo.City;

/**
 * Simple implementation of the Address interface.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Data
public class AddressImpl implements Address {

	protected String address1;
	protected String address2;
	protected String address3;
	protected String address4;
	protected City city;
	protected PostalCode postalCode;

}