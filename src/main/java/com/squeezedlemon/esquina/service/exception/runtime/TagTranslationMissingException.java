package com.squeezedlemon.esquina.service.exception.runtime;

public class TagTranslationMissingException extends RuntimeException {

	private static final long serialVersionUID = 8109895595884517172L;

	public TagTranslationMissingException() {
		super();
	}

	public TagTranslationMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public TagTranslationMissingException(String message) {
		super(message);
	}

	public TagTranslationMissingException(Throwable cause) {
		super(cause);
	}
	
}
