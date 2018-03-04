package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.Address;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

	private static final String ADDRESS_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.address";
	private static final String ADDRESS_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.address";
	@Autowired
	private AddressRepository repository;
	
	/* Methods form IService */
	
	@Override
	public Address create(Address t) throws DataRepositoryException {
		if (t.getId() == null || !repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(ADDRESS_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(Long id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(ADDRESS_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<Address> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Address> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Address findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Address update(Address t) throws DataRepositoryException {
		if (repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(ADDRESS_NOT_FOUND_MESSAGE_CODE);
		}
	}

}
