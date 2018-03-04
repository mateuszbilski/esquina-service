package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import com.squeezedlemon.esquina.service.entity.Checkin;
import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;

public interface CheckinService extends IService<Checkin, Long> {
	
	public List<Checkin> findNewestCheckinsFromUsers(List<User> users) throws DataRepositoryException;
	
	public List<Checkin> findLocationCheckins(Location location);

}
