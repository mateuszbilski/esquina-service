package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

	private static final String TAG_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.tag";
	
	private static final String TAG_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.tag";
	
	@Autowired
	private TagRepository repository;
	
	/* Methods from IService */
	
	@Override
	public Tag create(Tag t) throws DataRepositoryException {
		if (!repository.exists(t.getTag())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(TAG_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(String id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(TAG_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<Tag> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Tag> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Tag findById(String id) {
		return repository.findOne(id);
	}

	@Override
	public Tag update(Tag t) throws DataRepositoryException {
		if (repository.exists(t.getTag())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(TAG_NOT_FOUND_MESSAGE_CODE);
		}
	}

}
