package com.squeezedlemon.esquina.service.exception.runtime;

public class DefaultLocationDescriptionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2842971358529579160L;

	public DefaultLocationDescriptionNotFoundException() {
		super();
	}

	public DefaultLocationDescriptionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DefaultLocationDescriptionNotFoundException(String message) {
		super(message);
	}

	public DefaultLocationDescriptionNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
