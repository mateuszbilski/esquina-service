package com.squeezedlemon.esquina.service.service.response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.service.entity.LangCodeService;

@Service
public class LangCodeResponseBuilderImpl implements LangCodeResponseBuilder {

	private static final String LANGUAGE_CODES_LIST_PROPERTY = "langCodes";
	private static final String LOCAL_DESCRIPTION_PROPERTY = "local";
	private static final String UNIFIED_PROPERTY = "unified";
	private static final String LANG_CODE_PROPERTY = "langCode";

	@Autowired
	private LangCodeService langCodeService;
	
	@Override
	public Map<String, Object> languageCreated(String langCode) {
		Map<String, Object> map = new HashMap<>();
		map.put(LANG_CODE_PROPERTY, langCode);
		return map;
	}
	
	@Override
	public Map<String, Object> languageCodeDetails(String langCode) {
		
		LangCode langCodeEntity = langCodeService.findById(langCode);
		Map<String, Object> map = new HashMap<>();
		map.put(LANG_CODE_PROPERTY, langCodeEntity.getLangCode());
		map.put(UNIFIED_PROPERTY, langCodeEntity.getUnifiedDescription());
		map.put(LOCAL_DESCRIPTION_PROPERTY, langCodeEntity.getLocalDescription());
		
		return map;
	}

	@Override 
	public Map<String, Object> languageCodesDetails(List<String> langCodes) {
		Map<String, Object> map = new HashMap<>();
		
		List<Map<String, Object>> langCodesList = new LinkedList<>();
		for (String itemId : langCodes) {
			langCodesList.add(languageCodeDetails(itemId));
		}
		map.put(LANGUAGE_CODES_LIST_PROPERTY, langCodesList);
		
		return map;
	}
	
	@Override 
	public Map<String, Object> languageCodesDetails() {
		Map<String, Object> map = new HashMap<>();
		
		List<LangCode> langCodes = langCodeService.findAll();
		List<Map<String, Object>> langCodesList = new LinkedList<>();
		for (LangCode item : langCodes) {
			langCodesList.add(languageCodeDetails(item.getLangCode()));
		}
		map.put(LANGUAGE_CODES_LIST_PROPERTY, langCodesList);
		
		return map;
	}
	
	
}
