 package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.repository.LangCodeRepository;

@Service
public class LangCodeServiceImpl implements LangCodeService {

	private static final String LANGUAGE_CODE_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.langCode";
	private static final String LANGUAGE_CODE_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.langCode";
			
	@Autowired
	private LangCodeRepository repository;
	
	/* Methods from IService */
	
	@Override
	public LangCode create(LangCode t) throws DataRepositoryException {
		if(!repository.exists(t.getLangCode())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(LANGUAGE_CODE_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(String id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(LANGUAGE_CODE_NOT_FOUND_MESSAGE_CODE); 
		}
	}

	@Override
	public List<LangCode> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<LangCode> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public LangCode findById(String id) {
		return repository.findOne(id);
	}

	@Override
	public LangCode update(LangCode t) throws DataRepositoryException {
		if (repository.exists(t.getLangCode())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(LANGUAGE_CODE_NOT_FOUND_MESSAGE_CODE);
		}
	}


}
