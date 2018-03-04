package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import com.squeezedlemon.esquina.service.entity.LocationDescription;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;

public interface LocationDescriptionService extends
		IService<LocationDescription, Long> {

	List<LocationDescription> search(String query) throws DataRepositoryException;
}
