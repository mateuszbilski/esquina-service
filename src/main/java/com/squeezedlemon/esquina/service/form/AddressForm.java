package com.squeezedlemon.esquina.service.form;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squeezedlemon.esquina.service.entity.Address;

public class AddressForm {

	private static final String STREET_PROPERTY = "street";
	private static final String POSTAL_CODE_PROPERTY = "postalCode";
	private static final String CITY_PROPERTY = "city";
	private static final String COUNTRY_PROPERTY = "country";
	
	@Valid
	private Address address;
	
	public AddressForm() {
		
	}
	
	public AddressForm(
			@JsonProperty(COUNTRY_PROPERTY) String country,
			@JsonProperty(CITY_PROPERTY) String city,
			@JsonProperty(POSTAL_CODE_PROPERTY) String postalCode,
			@JsonProperty(STREET_PROPERTY) String street) {
		
		address = new Address();
		address.setCountry(country);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setStreet(street);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
