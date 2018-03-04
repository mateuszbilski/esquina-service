package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.squeezedlemon.esquina.service.validation.RegexConstants;

@Entity
@Table(name = "LANG_CODES")
public class LangCode implements Serializable {

	private static final long serialVersionUID = -8330191008520498162L;
	
	@NotEmpty(message = "error.valid.empty") @Pattern(regexp = RegexConstants.LANG_CODE_PATTERN, message = "error.valid.langCode.invalid")
	@Id @Column(name = "LC_LANG_CODE")
	private String langCode;
	
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "LC_UNIFIED_DESC", nullable = false)
	private String unifiedDescription;
	
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "LC_LOCAL_DESC", nullable = false)
	private String localDescription;
	
	public LangCode() {

	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getUnifiedDescription() {
		return unifiedDescription;
	}

	public void setUnifiedDescription(String unifiedDescription) {
		this.unifiedDescription = unifiedDescription;
	}

	public String getLocalDescription() {
		return localDescription;
	}

	public void setLocalDescription(String localDescription) {
		this.localDescription = localDescription;
	}

}
