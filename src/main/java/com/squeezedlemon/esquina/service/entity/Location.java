package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Table(name = "LOCATIONS")
public class Location implements Serializable {

	private static final long serialVersionUID = -3256087937395895256L;
	
	@Id
	@SequenceGenerator(name = "seq_locations_id", sequenceName="SEQ_LO_ID", allocationSize=1)
	@GeneratedValue(generator = "seq_locations_id", strategy = GenerationType.SEQUENCE)
	@Column(name = "LO_ID")
	private Long id;
	
	@Min(value = -90, message = "error.valid.latitude.min")
	@Max(value = 90, message = "error.valid.latitude.max")
	@NotNull(message = "error.valid.null")
	@Column(name = "LO_LATITUDE", nullable = false)
	private BigDecimal latitude;
	
	@Min(value = -180, message = "error.valid.longitude.min")
	@Max(value = 180, message = "error.valid.longitude.max")
	@NotNull(message = "error.valid.null")
	@Column(name = "LO_LONGITUDE", nullable = false)
	private BigDecimal longitude;
	
	@Column(name = "LO_SCORE", nullable = false, insertable = false, updatable = false)
	private BigDecimal score;
	
	@Column(name = "LO_CHECKIN_COUNT", nullable = false, insertable = false, updatable = false)
	private Long checkinCount;
	
	@NotNull(message = "error.valid.null")
	@Column(name = "LO_CREATED_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name = "LO_OWNER")
	private String owner;
	
	@Column(name = "LO_WEBSITE")
	private String website;
	
	@OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "LO_ICON")
	private Image icon;
	
	@OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "LO_BIMAGE")
	private Image backgroundImage;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AD_ID", nullable = true)
	private Address address;
	
	@OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
	private List<LocationDescription> locationDescriptions;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "LOCATIONS_TAGS", 
			joinColumns = @JoinColumn(name = "LOCATIONS_LO_ID"),
			inverseJoinColumns = @JoinColumn(name = "TAGS_TA_TAG"))
	private List<Tag> tags;
	
	public Location() {
		
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

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Long getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(Long checkinCount) {
		this.checkinCount = checkinCount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<LocationDescription> getLocationDescriptions() {
		return locationDescriptions;
	}

	public void setLocationDescriptions(
			List<LocationDescription> locationDescriptions) {
		this.locationDescriptions = locationDescriptions;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
}
