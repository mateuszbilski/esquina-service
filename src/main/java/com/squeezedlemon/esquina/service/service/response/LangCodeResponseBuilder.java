package com.squeezedlemon.esquina.service.service.response;

import java.util.List;
import java.util.Map;

import com.squeezedlemon.esquina.service.entity.LangCode;

public interface LangCodeResponseBuilder {

	public Map<String, Object> languageCreated(String langCode);
	
	public Map<String, Object> languageCodeDetails(String langCode);
	
	public Map<String, Object> languageCodesDetails(List<String> langCodes);
	
	public Map<String, Object> languageCodesDetails();
}
