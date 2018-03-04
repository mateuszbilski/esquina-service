package com.squeezedlemon.esquina.service.exception.application;

import org.springframework.http.HttpStatus;

public class CheckinProximityException extends ApplicationException {

	private static final long serialVersionUID = 875788928749194755L;
	
	private final static String STANDARD_MESSAGE_CODE = "exception.checkinProximityException";
	
	private final static HttpStatus STANDARD_RESPONSE_CODE = HttpStatus.BAD_REQUEST;
	
	public CheckinProximityException() {
		super();
	}

	public CheckinProximityException(String overridenMessaegCode, Object[] messageParams, String exceptionMessage) {
		super(overridenMessaegCode, messageParams, exceptionMessage);
	}

	public CheckinProximityException(String overridenMessageCode, Object[] messageParams) {
		super(overridenMessageCode, messageParams);
	}

	public CheckinProximityException(String overridenMessageCode) {
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
