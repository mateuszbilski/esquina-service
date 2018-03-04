package com.squeezedlemon.esquina.service.form;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squeezedlemon.esquina.service.entity.TagTranslation;

public class TagTranslationForm {

	@Valid
	private TagTranslation tagTranslation;
	
	@NotEmpty(message = "error.valid.empty")
	private String language;
	
	public TagTranslationForm() {
		
	}
	
	@JsonCreator
	public TagTranslationForm(@JsonProperty("translation") String translation) {
		tagTranslation = new TagTranslation();
		tagTranslation.setTranslation(translation);
	}

	public TagTranslation getTagTranslation() {
		return tagTranslation;
	}

	public void setTagTranslation(TagTranslation tagTranslation) {
		this.tagTranslation = tagTranslation;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	
}
