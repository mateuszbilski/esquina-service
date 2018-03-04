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

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "TAG_TLS", uniqueConstraints = 
		@UniqueConstraint(columnNames = {"TA_TAG", "LC_LANG_CODE"}))
public class TagTranslation implements Serializable {

	private static final long serialVersionUID = -4224666820254150410L;

	@Id
	@SequenceGenerator(name = "seq_tag_tls_id", sequenceName="SEQ_TT_ID", allocationSize=1)
	@GeneratedValue(generator = "seq_tag_tls_id", strategy = GenerationType.SEQUENCE)
	@Column(name = "TT_ID")
	private Long id;
	
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "TT_TRANSLATION", nullable = false)
	private String translation;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LC_LANG_CODE")
	private LangCode langCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TA_TAG")
	private Tag tag;
	
	public TagTranslation() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public LangCode getLangCode() {
		return langCode;
	}

	public void setLangCode(LangCode langCode) {
		this.langCode = langCode;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
}
