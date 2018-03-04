package com.squeezedlemon.esquina.service.form;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squeezedlemon.esquina.service.entity.LangCode;

public class LangCodeForm {

	private static final String LOCAL_DESCRIPTION_PROPERTY = "localDescription";
	private static final String UNIFIED_DESCRIPTION_PROPERTY = "unifiedDescription";
	private static final String LANGUAGE_CODE_PROPERTY = "langCode";
	
	@Valid
	private LangCode langCode;
	
	public LangCodeForm() {
		
	}
	
	@JsonCreator
	public LangCodeForm(
			@JsonProperty(LANGUAGE_CODE_PROPERTY) String langCodeName,
			@JsonProperty(UNIFIED_DESCRIPTION_PROPERTY) String unified,
			@JsonProperty(LOCAL_DESCRIPTION_PROPERTY) String local) {
		
		langCode = new LangCode();
		langCode.setLangCode(langCodeName);
		langCode.setUnifiedDescription(unified);
		langCode.setLocalDescription(local);
	}

	public LangCode getLangCode() {
		return langCode;
	}

	public void setLangCode(LangCode langCode) {
		this.langCode = langCode;
	}
	
}
