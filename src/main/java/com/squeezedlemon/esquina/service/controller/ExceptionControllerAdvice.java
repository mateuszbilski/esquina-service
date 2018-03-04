package com.squeezedlemon.esquina.service.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.ValidationException;
import com.squeezedlemon.esquina.service.exception.runtime.CannotMapFieldNameException;
import com.squeezedlemon.esquina.service.util.ErrorInfo;
import com.squeezedlemon.esquina.service.util.ExceptionErrorInfo;
import com.squeezedlemon.esquina.service.util.ValidationErrorInfo;
import com.squeezedlemon.esquina.service.util.ErrorInfo.ErrorType;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	private static final String INVALID_FIELD_DEFAULT_MESSAGE = "error.valid.defaultValue";
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@Autowired
	private MessageSource messageSource;

	 @ExceptionHandler(ValidationException.class)
	 public ResponseEntity<ValidationErrorInfo> validationExceptionHandler(HttpServletRequest request, ValidationException ex) {
		 ValidationErrorInfo errorInfo = new ValidationErrorInfo();
		 errorInfo.setType(ErrorType.APPLICATION);
		 errorInfo.setStatusCode(ex.getResponseCode().value());
		 errorInfo.setRequestUri(request.getRequestURI());
		 errorInfo.setValidationErrors(buildValidationErrors(ex.getBindingResult(),  ex.getCustomMapping()));
		 
		 return ResponseEntity.status(ex.getResponseCode()).header("Content-Type", "application/json;charset=UTF-8").body(errorInfo);
	 }

	@ExceptionHandler(ApplicationException.class)
	@ResponseBody
	public ResponseEntity<ExceptionErrorInfo> applicationExceptionHandler(HttpServletRequest request, ApplicationException applicationException) {

		logger.error(String.format("Handling Application Exception (request uri: %s): ", 
				request.getRequestURI()), applicationException);
		
		ExceptionErrorInfo.ExceptionInfo exceptionInfo = null;
		exceptionInfo = new ExceptionErrorInfo.ExceptionInfo();
		exceptionInfo.setExceptionClass(applicationException.getClass());
		exceptionInfo.setExceptionMessage(applicationException.getMessage());

		String localizedMessage = null;
		try {
			localizedMessage = messageSource.getMessage(applicationException.getLocalizationMessageCode(), applicationException.getMessageParams(),
					LocaleContextHolder.getLocaleContext().getLocale());
		} catch (NoSuchMessageException ex) {
			logger.error(String.format("Missing message:%s for lang:%s", applicationException.getLocalizationMessageCode(), LocaleContextHolder
					.getLocaleContext().getLocale()));
		}
		exceptionInfo.setLocalizedMessage(localizedMessage);

		ExceptionErrorInfo errorInfo = new ExceptionErrorInfo();
		errorInfo.setType(ErrorInfo.ErrorType.APPLICATION);
		errorInfo.setExceptionInfo(exceptionInfo);
		errorInfo.setRequestUri(request.getRequestURI());
		errorInfo.setStatusCode(applicationException.getResponseCode().value());

		return ResponseEntity.status(applicationException.getResponseCode()).header("Content-Type", "application/json;charset=UTF-8").body(errorInfo);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		logger.error(String.format("Handling MVC Exception (request uri: %s): ", 
				request.getDescription(true)), ex);
		
		ExceptionErrorInfo.ExceptionInfo exceptionInfo = null;
		exceptionInfo = new ExceptionErrorInfo.ExceptionInfo();
		exceptionInfo.setExceptionClass(ex.getClass());
		exceptionInfo.setExceptionMessage(ex.getMessage());

		ExceptionErrorInfo errorInfo = new ExceptionErrorInfo();
		errorInfo.setType(ErrorInfo.ErrorType.MVC);
		errorInfo.setExceptionInfo(exceptionInfo);
		errorInfo.setRequestUri(request.getDescription(false));
		errorInfo.setStatusCode(status.value());

		return ResponseEntity.status(status).header("Content-Type", "application/json;charset=UTF-8").body((Object) errorInfo);
	}
	
	private Map<String, List<String>> buildValidationErrors(BindingResult errors, Map<String, String> customMapping) {
		Map<String, List<String>> validationErrors = new HashMap<>();
		for (FieldError item : errors.getFieldErrors()) {
			String mapFieldName = mapFieldName(item.getField(), customMapping);

			List<String> lst = validationErrors.get(mapFieldName);
			if (lst == null) {
				lst = new LinkedList<String>();
				validationErrors.put(mapFieldName, lst);
			}
			
			lst.add(prepareMessage(item.getDefaultMessage()));
		}
		
		return validationErrors;
	}
	
	private String mapFieldName(String objectName, Map<String, String> customMapping) {
		
		String name = null;
		
		if (customMapping == null) {
			name = objectName.substring(objectName.lastIndexOf(".") + 1);
		} else {
			name = customMapping.get(objectName);
		}
		
		if (name == null || name.isEmpty()) {
			throw new CannotMapFieldNameException(objectName);
		} else {
			return name;
		}
		
	}
	
	private String prepareMessage(String code) {
		String message = null;
		
		if (code != null) {
			try {
				message = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException ex) {
				logger.error(String.format("Missing message:%s for lang:%s", code, LocaleContextHolder.getLocaleContext().getLocale()));
			}
		}
		
		if (code == null || message == null) {
			message = messageSource.getMessage(INVALID_FIELD_DEFAULT_MESSAGE, null, LocaleContextHolder.getLocale());
		}
		
		return message;
	}
}
