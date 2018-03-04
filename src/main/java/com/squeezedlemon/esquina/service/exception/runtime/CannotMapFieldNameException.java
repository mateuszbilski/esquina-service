package com.squeezedlemon.esquina.service.exception.runtime;


public class CannotMapFieldNameException extends RuntimeException {

	private static final long serialVersionUID = -8697106269436340279L;

	public CannotMapFieldNameException() {
		super();
	}

	public CannotMapFieldNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotMapFieldNameException(String message) {
		super(message);
	}

	public CannotMapFieldNameException(Throwable cause) {
		super(cause);
	}

}
