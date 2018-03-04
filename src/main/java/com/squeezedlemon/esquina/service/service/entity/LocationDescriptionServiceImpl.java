package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.LocationDescription;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.exception.application.InvalidEntityException;
import com.squeezedlemon.esquina.service.repository.LocationDescriptionRepository;

@Service
public class LocationDescriptionServiceImpl implements
		LocationDescriptionService {
	
	private static final String INVALID_ENTITY_MESSAGE_CODE = "exception.invalidEntityException.locationDescription.defaultLang";

	private static final String LOCATION_DESCRIPTION_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.locationDescription";

	private static final String LOCATION_DESCRIPTION_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.locationDescription";
	
	@Autowired
	private LocationDescriptionRepository repository;
	
	@Override
	public List<LocationDescription> search(String query) throws DataRepositoryException {
		return repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationOwnerContainingIgnoreCase(query, query, query);
	}
	
	/* Method from IService */
	
	@Override
	public LocationDescription create(LocationDescription t)
			throws DataRepositoryException {
		if (t.getId() == null || !repository.exists(t.getId())) {
			updaetDefaultDescriptionOnSave(t);
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(LOCATION_DESCRIPTION_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(Long id) throws DataRepositoryException {
		if (repository.exists(id)) {
			
			updateDefaultDescriptionOnDelete(id);
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(LOCATION_DESCRIPTION_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<LocationDescription> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<LocationDescription> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public LocationDescription findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public LocationDescription update(LocationDescription t)
			throws DataRepositoryException {
		if (repository.exists(t.getId())) {
			updateDefaultDescriptionOnUpdate(t);
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(LOCATION_DESCRIPTION_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	private void updateDefaultDescriptionOnUpdate(LocationDescription t) throws DataRepositoryException {
		LocationDescription description = repository.findByLocationAndDefaultLangIsTrue(t.getLocation());

		
		if (Boolean.TRUE.equals(t.getDefaultLang())) {
			if (description != null && !description.getId().equals(t.getId())) {
				description.setDefaultLang(false);
				repository.save(description);
			}
		} else {
			 if (description == null || description.getId().equals(t.getId())) {
				 //There must be default lang in system
				 throw new InvalidEntityException(INVALID_ENTITY_MESSAGE_CODE);
			 }
		}
	}
	
	private void updateDefaultDescriptionOnDelete(Long id) throws DataRepositoryException {
		LocationDescription description = findById(id);
		if (Boolean.TRUE.equals(description.getDefaultLang())) {
			//Cannot delete default description, first change it to another description and then delete this one
			throw new InvalidEntityException(INVALID_ENTITY_MESSAGE_CODE);
		}
	}
	
	private void updaetDefaultDescriptionOnSave(LocationDescription t) {
		LocationDescription description = repository.findByLocationAndDefaultLangIsTrue(t.getLocation());
		
		if (Boolean.TRUE.equals(t.getDefaultLang())) {
			if (description != null) {
				description.setDefaultLang(false);
				repository.save(description);
			} else {
				//Nothing to do
			}
		} else {
			if (description == null) {
				t.setDefaultLang(true);
			}
		}
	}

}
