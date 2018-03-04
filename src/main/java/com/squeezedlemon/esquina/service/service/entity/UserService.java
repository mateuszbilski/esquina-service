package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;

public interface UserService extends IService<User, String> {
	
	public List<User> search(String query) throws DataRepositoryException ;

}
