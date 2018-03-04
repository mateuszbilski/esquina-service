package com.squeezedlemon.esquina.service.service.response;

import java.util.Map;

import com.squeezedlemon.esquina.service.exception.application.ApplicationException;

public interface TestResponseBuilder {
	
	public Map<String, Object> greeting() throws ApplicationException;
	
	public Map<String, Object> memberGreeting(String accountName) throws ApplicationException;
	
}
