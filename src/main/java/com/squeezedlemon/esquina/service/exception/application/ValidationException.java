package com.squeezedlemon.esquina.service.exception.application;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

/**
 * Class is specially designed for storing validation errors. It keeps reference to BindingResult object and
 * additionally has custom mapping map for translating property between pojo class and json.
 * There is no need to catch these exceptions, because there is specially method, which converts to json this exception.
 * @author Mateusz Bilski
 *
 */
public class ValidationException extends ApplicationException {

	private static final long serialVersionUID = -9033883801547672789L;
	
	private static final String STANDARD_MESSAGE_CODE = "exception.validationException";
	
	private static final HttpStatus STANDARD_RESPONSE_CODE = HttpStatus.UNPROCESSABLE_ENTITY;
	
	private Map<String, String> customMapping;
	
	private BindingResult bindingResult;

	public ValidationException(BindingResult bindingResult, Map<String, String> customMapping) {
		this.bindingResult = bindingResult;
		this.customMapping = customMapping;
	}
	
	@Override
	public String getStandardMessageCode() {
		return STANDARD_MESSAGE_CODE;
	}
	
	@Override
	public HttpStatus getStandardResponseCode() {
		return STANDARD_RESPONSE_CODE;
	}

	public Map<String, String> getCustomMapping() {
		return customMapping;
	}

	public void setCustomMapping(Map<String, String> customMapping) {
		this.customMapping = customMapping;
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

}
