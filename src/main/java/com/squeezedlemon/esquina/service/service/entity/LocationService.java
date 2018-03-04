package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;

public interface LocationService extends IService<Location, Long> {
	
	public List<Location> search(String query) throws DataRepositoryException;

}
