package com.squeezedlemon.esquina.service.service.response;

import java.util.List;
import java.util.Map;

import com.squeezedlemon.esquina.service.entity.Address;

public interface AddressResponseBuilder {

	public Map<String, Object> addressCreated(Long addressId);
	
	public Map<String, Object> addressDetails(Long addressId);
	
	public Map<String, Object> addressesDetails(List<Long> addressIds);
	
	public Map<String, Object> addressesDetails();
}
