package com.squeezedlemon.esquina.service.service.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.entity.LocationDescription;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;
import com.squeezedlemon.esquina.service.exception.application.DuplicateEntityException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	private static final String LOCATION_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.location";
	
	private static final String LOCATION_DUPLICATED_MESSAGE_CODE = "exception.duplicateEntityException.location";
	
	@Autowired
	private LocationRepository repository;
	
	@Autowired
	private LocationDescriptionService locationDescriptionService;

	public List<Location> search(String query) throws DataRepositoryException {
		List<LocationDescription> descriptionsMatched = locationDescriptionService.search(query);
		Map<Long, Location> map = new HashMap<>();
		for (LocationDescription item : descriptionsMatched) {
			if (!map.containsKey(item.getLocation().getId())) {
				map.put(item.getLocation().getId(), item.getLocation());
			}
		}
		
		return Lists.newLinkedList(map.values());
	}
	
	/* Methods from IService */
	
	@Override
	public Location create(Location t)
			throws DataRepositoryException {
		if (t.getId() == null || !repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new DuplicateEntityException(LOCATION_DUPLICATED_MESSAGE_CODE);
		}
	}

	@Override
	public void delete(Long id) throws DataRepositoryException {
		if (repository.exists(id)) {
			repository.delete(id);
		} else {
			throw new EntityNotFoundException(LOCATION_NOT_FOUND_MESSAGE_CODE);
		}
	}

	@Override
	public List<Location> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Location> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Location findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Location update(Location t)
			throws DataRepositoryException {
		if (repository.exists(t.getId())) {
			return repository.save(t);
		} else {
			throw new EntityNotFoundException(LOCATION_NOT_FOUND_MESSAGE_CODE);
		}
	}

}
