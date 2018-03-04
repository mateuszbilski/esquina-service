package com.squeezedlemon.esquina.service.service.response;

import java.util.Map;

import com.squeezedlemon.esquina.service.exception.application.ApplicationException;

public interface CheckinResponseBuilder {
	
	public Map<String, Object> checkinCreated(Long id);
	
	public Map<String, Object> checkinDetails(Long checkinId);
	
	public Map<String, Object> getActivityStream(String username) throws ApplicationException;
}
