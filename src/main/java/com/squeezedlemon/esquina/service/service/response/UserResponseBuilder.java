package com.squeezedlemon.esquina.service.service.response;

import java.util.Map;

import com.squeezedlemon.esquina.service.exception.application.ApplicationException;

public interface UserResponseBuilder {

	public Map<String, Object> userDetails(String accountName);
	
	public Map<String, Object> followingCreated(Long followingId);
	
	public Map<String, Object> userEntityDetails(String accountName);
	
	public Map<String, Object> searchDetails(String query) throws ApplicationException;
	
}
