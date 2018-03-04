 package com.squeezedlemon.esquina.service.service.response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.entity.TagTranslation;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.runtime.TagTranslationMissingException;
import com.squeezedlemon.esquina.service.service.entity.LangCodeService;
import com.squeezedlemon.esquina.service.service.entity.TagService;
import com.squeezedlemon.esquina.service.service.entity.TagTranslationService;

@Service
public class TagResponseBuilderImpl implements TagResponseBuilder {

	private static final String TAGS_LIST_PROPERTY = "tags";
	private static final String TRANSLATION_ID_PROPERTY = "id";
	private static final String TRANSLATIONS_LIST_PROPERTY = "translations";
	private static final String LANGUAGE_PROPERTY = "language";
	private static final String TRANSLATION_PROPERTY = "translation";
	private static final String TAG_ICON_PROPERTY = "icon";
	private static final String TAG_PROPERTY = "tag";

	@Autowired
	private LangCodeService langCodeService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private TagTranslationService tagTranslationService;
	
	@Override
	public Map<String, Object> tagCreated(String tagName) {
		Map<String, Object> map = new HashMap<>();
		
		map.put(TAG_PROPERTY, tagName);
		return map;
	}

	@Override
	@Transactional
	public Map<String, Object> tagDetails(String tagName, String langCode) {
		Map<String, Object> map = new HashMap<>();
		
		Tag t = tagService.findById(tagName);
		TagTranslation translation = findAppropriateTagTranslation(tagName, langCode);
		
		map.put(TAG_PROPERTY, t.getTag());
		map.put(TAG_ICON_PROPERTY, (t.getIcon() != null ? t.getIcon().getId() : null) );
		map.put(TRANSLATION_PROPERTY, translation.getTranslation());
		map.put(LANGUAGE_PROPERTY, translation.getLangCode().getLangCode());
		return map;
	}
	
	@Transactional
	@Override
	public Map<String, Object> tagsDetails(String langCode) {
		
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> tagsDetailsList = new LinkedList<>();
		List<Tag> tags = tagService.findAll();
		
		for (Tag item : tags) {
			tagsDetailsList.add(tagDetails(item.getTag(), langCode));
		}
		map.put(TAGS_LIST_PROPERTY, tagsDetailsList);
		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> tagsDetails(List<Tag> tags, String langCode) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> tagsDetailsList = new LinkedList<>();
		
		for (Tag item : tags) {
			tagsDetailsList.add(tagDetails(item.getTag(), langCode));
		}
		map.put(TAGS_LIST_PROPERTY, tagsDetailsList);
		return map;
	}

	@Override
	@Transactional
	public Map<String, Object> translationDetails(Long translationId) {
		Map<String, Object> map = new HashMap<>();
		
		TagTranslation translation = tagTranslationService.findById(translationId);

		map.put(TRANSLATION_ID_PROPERTY, translation.getId());
		map.put(TRANSLATION_PROPERTY, translation.getTranslation());
		map.put(LANGUAGE_PROPERTY, translation.getLangCode().getLangCode());
		map.put(TAG_PROPERTY, translation.getTag().getTag());
		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> translationsDetails(String tagName) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> translationDetailsList = new LinkedList<>();
		List<TagTranslation> translations = tagService.findById(tagName).getTagTranslations();
		
		for (TagTranslation item : translations) {
			translationDetailsList.add(translationDetails(item.getId()));
		}
		
		map.put(TRANSLATIONS_LIST_PROPERTY, translationDetailsList);
		return map;
		
	}

	@Transactional
	@Override
	public Map<String, Object> translationCreated(Long translationId) {
		Map<String, Object> map = new HashMap<>();
		
		map.put(TRANSLATION_ID_PROPERTY, translationId);
		return map;
	}
	
	private TagTranslation findAppropriateTagTranslation(String tagName, String langCode) {
		
		TagTranslation tagTranslation;
		try {
			tagTranslation = tagTranslationService.findByLangCodeAndTag(langCodeService.findById(langCode), 
					tagService.findById(tagName));
			
			if (tagTranslation != null) {
				return tagTranslation;
			} else {
				throw new DataRepositoryException();
			}
		} catch (DataRepositoryException e) {
			e.printStackTrace();
			throw new TagTranslationMissingException(String.format("Missing translation for tag: %s in %s", tagName, 
					langCode));
		}	
	}

}
