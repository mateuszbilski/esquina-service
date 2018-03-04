package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "LOCATION_DESCS", uniqueConstraints = 
		@UniqueConstraint(columnNames = {"LC_LANG_CODE", "LO_ID"}))
public class LocationDescription implements Serializable {

	private static final long serialVersionUID = 7779407671186229892L;

	@Id
	@SequenceGenerator(name = "seq_location_descs_id", sequenceName="SEQ_LD_ID", allocationSize=1)
	@GeneratedValue(generator = "seq_location_descs_id", strategy = GenerationType.SEQUENCE)
	@Column(name = "LD_ID")
	private Long id;
	
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "LD_NAME", nullable = false)
	private String name;
	
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "LD_DESC")
	private String description;
	
	@NotNull(message = "error.valid.null")
	@Column(name = "LD_DEFAULT", nullable = false)
	private Boolean defaultLang;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LC_LANG_CODE")
	private LangCode langCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LO_ID")
	private Location location;
	
	public LocationDescription() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LangCode getLangCode() {
		return langCode;
	}

	public void setLangCode(LangCode langCode) {
		this.langCode = langCode;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Boolean getDefaultLang() {
		return defaultLang;
	}

	public void setDefaultLang(Boolean defaultLang) {
		this.defaultLang = defaultLang;
	}
	
}
