package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final String USER_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.user";
	
	private static final String USER_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.user";
	
	@Autowired
	private UserRepository repository;
	
	public List<User> search(String query) throws DataRepositoryException {
		return repository.findByAccountNameContainingOrLastNameIgnoreCaseContaining(query, query);
	}
	
	
	/* Methods from IService */
	
	@Override
	public User create(User t) throws DataRepositoryException {
		if (!repository.exists(t.getAccountName())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(USER_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(String id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<User> findAll() {
		return repository.findByAccountEnabledTrueAndAccountDeletedNull();
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return repository.findByAccountEnabledTrueAndAccountDeletedNull(pageable);
	}

	@Override
	public User findById(String id) {
		return repository.findByAccountNameAndAccountEnabledTrueAndAccountDeletedNull(id);
	}

	@Override
	public User update(User t) throws DataRepositoryException {
		if (repository.exists(t.getAccountName())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE_CODE);
		}
	}

}
