package com.squeezedlemon.esquina.service.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public enum SystemLanguage {
	POLISH("pl"), ENGLISH("en");
	
	private String langCode;
	
	private static final Map<String, SystemLanguage> map;
	
	static {
		map = new HashMap<String, SystemLanguage>();
		for (SystemLanguage item : SystemLanguage.values()) {
			map.put(item.langCode, item);
		}
	}
	
	public static SystemLanguage enumFromString(String langCode) {
		return map.get(langCode);
	}
	
	private SystemLanguage(String langCode) {
		/**
		 * According to JavaDoc, it's bad style to compare langCode string's.
		 * Better solution is to build new Locale object with lang code and get 
		 * actual lang code from this one
		 */
		this.langCode = (new Locale(langCode)).getLanguage();	 
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	
}
