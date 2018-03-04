package com.squeezedlemon.esquina.service.exception.application;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {

	private static final long serialVersionUID = 5149827532572008015L;

	private final static String STANDARD_MESSAGE_CODE = "exception.badRequestException";
	
	private final static HttpStatus STANDARD_RESPONSE_CODE = HttpStatus.BAD_REQUEST;
	
	public BadRequestException() {
		super();
	}

	public BadRequestException(String overridenMessaegCode, Object[] messageParams, String exceptionMessage) {
		super(overridenMessaegCode, messageParams, exceptionMessage);
	}

	public BadRequestException(String overridenMessageCode, Object[] messageParams) {
		super(overridenMessageCode, messageParams);
	}

	public BadRequestException(String overridenMessageCode) {
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
