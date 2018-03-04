package com.squeezedlemon.esquina.service.form;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squeezedlemon.esquina.service.entity.LocationDescription;

public class LocationDescriptionForm {

	@Valid
	private LocationDescription locationDescription;

	@NotEmpty(message = "error.valid.empty")
	private String language;
	
	public LocationDescriptionForm() {
		
	}
	
	@JsonCreator
	public LocationDescriptionForm(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("defaultLang") Boolean defaultLang) {
		
		locationDescription = new LocationDescription();
		locationDescription.setName(name);
		locationDescription.setDescription(description);
		locationDescription.setDefaultLang(defaultLang);
	}

	public LocationDescription getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(LocationDescription locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
