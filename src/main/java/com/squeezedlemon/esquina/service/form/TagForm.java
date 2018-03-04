package com.squeezedlemon.esquina.service.form;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squeezedlemon.esquina.service.entity.Tag;

public class TagForm {

	@Valid
	private Tag tag;

	public TagForm() {

	}

	@JsonCreator
	public TagForm(@JsonProperty("tag") String tagName) {
		tag = new Tag();
		tag.setTag(tagName);
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
}
