package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "ADDRESSES")
public class Address implements Serializable {

	private static final long serialVersionUID = -8782596003793739097L;

	@Id
	@SequenceGenerator(name = "seq_addresses_id", sequenceName="SEQ_AD_ID", allocationSize=1)
	@GeneratedValue(generator = "seq_addresses_id", strategy = GenerationType.SEQUENCE)
	@Column(name = "AD_ID")
	private Long id;
	
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "AD_COUNTRY")
	private String country;
	
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "AD_CITY")
	private String city;
	
	@Column(name = "AD_POSTAL_CODE")
	private String postalCode;
	
	@Column(name = "AD_STREET")
	private String street;
	
	public Address() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
}
