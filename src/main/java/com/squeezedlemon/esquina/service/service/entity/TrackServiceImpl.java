package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.Track;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.repository.TrackRepository;

@Service
public class TrackServiceImpl implements TrackService {

	private static final String TRACK_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.track";
	
	private static final String TRACK_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.track";
	
	@Autowired
	private TrackRepository repository;
	
	/* Methods from IService */
	
	@Override
	public Track create(Track t)
			throws DataRepositoryException {
		if (t.getId() == null || !repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(TRACK_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(Long id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(TRACK_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<Track> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Track> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Track findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Track update(Track t)
			throws DataRepositoryException {
		if (repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(TRACK_NOT_FOUND_MESSAGE_CODE);
		}
	}

}
