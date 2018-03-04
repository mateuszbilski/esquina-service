package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.entity.TagTranslation;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.repository.TagTranslationRepository;

@Service
public class TagTranslationServiceImpl implements TagTranslationService {

	private static final String TAG_TRANSLATION_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.tagTranslation";
	
	private static final String TAG_TRANSLATION_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.tagTranslation";
	
	@Autowired
	private TagTranslationRepository repository;

	/* Methods from IService */
	
	@Override
	public TagTranslation create(TagTranslation t)
			throws DataRepositoryException {
		if (t.getId() == null || !repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(TAG_TRANSLATION_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(Long id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(TAG_TRANSLATION_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<TagTranslation> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<TagTranslation> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public TagTranslation findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public TagTranslation update(TagTranslation t)
			throws DataRepositoryException {
		if (repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(TAG_TRANSLATION_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public TagTranslation findByLangCodeAndTag(LangCode langCode, Tag tag) throws DataRepositoryException {
		return repository.findByLangCodeAndTag(langCode, tag);
	}
}
