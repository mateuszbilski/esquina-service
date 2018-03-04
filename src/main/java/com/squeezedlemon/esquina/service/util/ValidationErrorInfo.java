package com.squeezedlemon.esquina.service.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationErrorInfo extends ErrorInfo {
	
	@JsonProperty("validationErrors")
	private Map<String, List<String>> validationErrors;
	
	public ValidationErrorInfo() {
		super();
	}

	public Map<String, List<String>> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(Map<String, List<String>> validationErrors) {
		this.validationErrors = validationErrors;
	}
	
}
