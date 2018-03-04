package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGES")
public class Image implements Serializable {

	private static final long serialVersionUID = -6532876600549803356L;

	@Id
	@Column(name = "IM_ID")
	private String id;
	
	@Lob
	@Column(name = "IM_DATA", nullable = false)
	@Basic(fetch = FetchType.LAZY, optional = false)
	private Blob data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Blob getData() {
		return data;
	}

	public void setData(Blob data) {
		this.data = data;
	}
	
}
