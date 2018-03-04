package com.squeezedlemon.esquina.service.service.response;

import java.util.List;
import java.util.Map;

import com.squeezedlemon.esquina.service.exception.application.ApplicationException;

public interface LocationResponseBuilder {

	public Map<String, Object> locationCreated(Long locationId);
	
	public Map<String, Object> locationDetails(Long locationId);
	
	public Map<String, Object> locationEntityDetails(Long locationId);
	
	public Map<String, Object> locationsDetails();
	
	public Map<String, Object> locationsDetails(List<Long> locationIds);
	
	public Map<String, Object> descriptionCreated(Long descriptionId);
	
	public Map<String, Object> descriptionDetails(Long descriptionId);
	
	public Map<String, Object> descriptionsDetails(Long locationId);
	
	public Map<String, Object> searchDetails(String query) throws ApplicationException;
	
}
