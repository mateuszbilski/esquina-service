package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Table(name = "TRACKS")
public class Track implements Serializable {

	private static final long serialVersionUID = 7106539566985693571L;
	
	@Id
	@SequenceGenerator(name = "seq_tracks_id", sequenceName="SEQ_TR_ID", allocationSize=1)
	@GeneratedValue(generator = "seq_tracks_id", strategy = GenerationType.SEQUENCE)
	@Column(name = "TR_ID")
	private Long id;

	@Min(value = -90, message = "error.valid.latitude.min")
	@Max(value = 90, message = "error.valid.latitude.max")
	@NotNull(message = "error.valid.null")
	@Column(name = "TR_LATITUDE", nullable = false)
	private BigDecimal latitude;
	
	@Min(value = -180, message = "error.valid.longitude.min")
	@Max(value = 180, message = "error.valid.longitude.max")
	@NotNull(message = "error.valid.null")
	@Column(name = "TR_LONGITUDE", nullable = false)
	private BigDecimal longitude;
	
	@NotNull(message = "error.valid.null")
	@Column(name = "TR_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AC_NAME")
	private User user;
	
	public Track() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
