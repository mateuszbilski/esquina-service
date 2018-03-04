package com.squeezedlemon.esquina.service.form;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squeezedlemon.esquina.service.entity.Checkin;

public class CheckinForm {

	@Valid
	private Checkin checkin;
	
	@NotNull(message = "error.valid.null")
	private Long locationId;
	
	@NotNull(message = "error.valid.null")
	@Min(value = -90, message = "error.valid.latitude.min")
	@Max(value = 90, message = "error.valid.latitude.max")
	private BigDecimal latitude;
	
	@NotNull(message = "error.valid.null")
	@Min(value = -180, message = "error.valid.longitude.min")
	@Max(value = 180, message = "error.valid.longitude.max")
	private BigDecimal longitude;
	
	public CheckinForm() {
		
	}
	
	@JsonCreator
	public CheckinForm(
			@JsonProperty("score") BigDecimal score,
			@JsonProperty("comment") String comment) {
		
		checkin = new Checkin();
		checkin.setScore(score);
		checkin.setComment(comment);
	}

	public Checkin getCheckin() {
		return checkin;
	}

	public void setCheckin(Checkin checkin) {
		this.checkin = checkin;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	
}
