package com.squeezedlemon.esquina.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.entity.TagTranslation;

public interface TagTranslationRepository extends
		JpaRepository<TagTranslation, Long> {
	
	public TagTranslation findByLangCodeAndTag(LangCode langCode, Tag tag);
}
