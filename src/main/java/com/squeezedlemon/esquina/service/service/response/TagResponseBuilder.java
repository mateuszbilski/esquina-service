package com.squeezedlemon.esquina.service.service.response;

import java.util.List;
import java.util.Map;

import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.entity.TagTranslation;

public interface TagResponseBuilder {
	
public Map<String, Object> tagCreated(String tagName);
	
	public Map<String, Object> tagDetails(String tagName, String langCode);
	
	public Map<String, Object> tagsDetails(String langCode);
	
	public Map<String, Object> tagsDetails(List<Tag> tags, String langCode);
	
	public Map<String, Object> translationDetails(Long translationId);
	
	public Map<String, Object> translationsDetails(String tagName);
	
	public Map<String, Object> translationCreated(Long translationId);
	
}
