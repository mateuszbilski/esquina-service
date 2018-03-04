package com.squeezedlemon.esquina.service.service.entity;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.squeezedlemon.esquina.service.entity.Checkin;
import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.repository.CheckinRepository;

@Service
public class CheckinServiceImpl implements CheckinService {

	private static final String CHECKIN_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.checkin";
	private static final String CHECKIN_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.checkin";
	
	@Autowired
	private CheckinRepository repository;

	@Override
	public List<Checkin> findLocationCheckins(Location location) {
		return repository.findByLocationOrderByDateDesc(location);
	}
	
	@Override
	public List<Checkin> findNewestCheckinsFromUsers(List<User> users) throws DataRepositoryException {
		if (users.isEmpty()) {
			return new LinkedList<Checkin>();
		} else {
			return repository.findFirst25ByUserInOrderByDateDesc(users);
		}
	}
	
	//Methods from IService
	
	@Override
	public Checkin create(Checkin t) throws DataRepositoryException {
		if(t.getId() == null || !repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(CHECKIN_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(Long id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(CHECKIN_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<Checkin> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Checkin> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Checkin findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Checkin update(Checkin t) throws DataRepositoryException {
		if (repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(CHECKIN_NOT_FOUND_MESSAGE_CODE);
		}
	}

}
