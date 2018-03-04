package com.squeezedlemon.esquina.service.exception.application;

import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends DataRepositoryException {

	private static final long serialVersionUID = -1777798984968890301L;
	
	private static final String STANDARD_MESSAGE_CODE = "excpetion.duplicateEntityException";
	
	private static final HttpStatus STANDARD_RESPONSE_CODE = HttpStatus.CONFLICT;

	public DuplicateEntityException() {
		super();
	}
	
	public DuplicateEntityException(String overridenMessaegCode, Object[] messageParams, String exceptionMessage) {
		super(overridenMessaegCode, messageParams, exceptionMessage);
	}

	public DuplicateEntityException(String overridenMessageCode, Object[] messageParams) {
		super(overridenMessageCode, messageParams);
	}

	public DuplicateEntityException(String overridenMessageCode) {
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
