package com.squeezedlemon.esquina.service.service.entity;

import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.entity.TagTranslation;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;

public interface TagTranslationService extends IService<TagTranslation, Long> {

	public TagTranslation findByLangCodeAndTag(LangCode langCode, Tag tag) throws DataRepositoryException;
}
