package com.squeezedlemon.esquina.service.exception.application;

import org.springframework.http.HttpStatus;

public class InvalidEntityException extends DataRepositoryException {

	private static final long serialVersionUID = -2239037640833832258L;
	
	private final static String STANDARD_MESSAGE_CODE = "exception.validationException";
	
	private final static HttpStatus STANDARD_RESPONSE_CODE = HttpStatus.UNPROCESSABLE_ENTITY;
	
	public InvalidEntityException() {
		super();
	}
	
	public InvalidEntityException(String overridenMessaegCode, Object[] messageParams, String exceptionMessage) {
		super(overridenMessaegCode, messageParams, exceptionMessage);
	}

	public InvalidEntityException(String overridenMessageCode, Object[] messageParams) {
		super(overridenMessageCode, messageParams);
	}

	public InvalidEntityException(String overridenMessageCode) {
		super(overridenMessageCode);
	}

	@Override
	public String getStandardMessageCode() {
		return STANDARD_MESSAGE_CODE;
	}
	
	@Override
	public HttpStatus getStandardResponseCode() {
		return STANDARD_RESPONSE_CODE;
	}
}
