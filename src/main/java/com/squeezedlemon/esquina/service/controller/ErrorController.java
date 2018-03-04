package com.squeezedlemon.esquina.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;
import com.squeezedlemon.esquina.service.util.ErrorInfo;
import com.squeezedlemon.esquina.service.util.ExceptionErrorInfo;

@Controller
public class ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/error", produces="application/json;charset=UTF-8")
    @ResponseBody
    public ErrorInfo handle(HttpServletRequest request) {

		Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (status == null) {
			status = Integer.valueOf(500);
		}
		
		String extraInfo = (String) request.getAttribute("javax.servlet.error.message");
		if (extraInfo != null && extraInfo.isEmpty()) {
			extraInfo = null;
		}
		
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        
		ExceptionErrorInfo.ExceptionInfo exceptionInfo = null;
        if (exception != null) {
        	exception = Throwables.getRootCause(exception);
        	exceptionInfo = new ExceptionErrorInfo.ExceptionInfo();
    		exceptionInfo.setExceptionClass(exception.getClass());
        	exceptionInfo.setExceptionMessage(exception.getMessage());
        }
        
        logger.error(String.format("Error occurred in %s, http code: %d, info: %s", requestUri, status, extraInfo), exception);
        
        ExceptionErrorInfo errorInfo = new ExceptionErrorInfo();
        errorInfo.setType((HttpStatus.Series.CLIENT_ERROR.equals(HttpStatus.Series.valueOf(status)) ? ErrorInfo.ErrorType.MVC : ErrorInfo.ErrorType.RUNTIME));
        errorInfo.setStatusCode(status);
        errorInfo.setExtraInfo(extraInfo);
        errorInfo.setRequestUri(requestUri);
        errorInfo.setExceptionInfo(exceptionInfo);
        
        return errorInfo;
    }
}
