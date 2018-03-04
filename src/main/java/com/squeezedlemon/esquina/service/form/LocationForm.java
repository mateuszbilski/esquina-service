package com.squeezedlemon.esquina.service.form;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squeezedlemon.esquina.service.entity.Location;

public class LocationForm {

	@Valid
	private Location location;
	
	private Long addressId;
	
	private List<String> tagsList;
	
	public LocationForm() {
		
	}
	
	@JsonCreator
	public LocationForm(
			@JsonProperty("latitude") BigDecimal latitude,
			@JsonProperty("longitude") BigDecimal longitude,
			@JsonProperty("owner") String owner,
			@JsonProperty("website") String website) {
		
		location = new Location();
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		location.setOwner(owner);
		location.setWebsite(website);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public List<String> getTagsList() {
		return tagsList;
	}

	public void setTagsList(List<String> tagsList) {
		this.tagsList = tagsList;
	}
	
}
