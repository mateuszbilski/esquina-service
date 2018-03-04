package com.squeezedlemon.esquina.service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.service.response.TestResponseBuilder;

@RestController
public class TestController {
	
	@Autowired
	private TestResponseBuilder responseBuilder;
	
	/**
	 * This mapping is required for OpenShift, 'cause after deployment application it sends
	 * request to "/". Without that application cannot start
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/")
	public void defaultMapping() {

	}
	
	@PreAuthorize("isAnonymous()")
	@RequestMapping(method = RequestMethod.GET, value = "/greeting", produces="application/json;charset=UTF-8")
	public Map<String, Object> greeting() throws ApplicationException {
		return responseBuilder.greeting();
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.GET, value = "/greeting/member", produces="application/json;charset=UTF-8")
    public Map<String, Object> memberGreeting() throws ApplicationException {
    	return responseBuilder.memberGreeting(SecurityContextHolder.getContext().getAuthentication().getName());
    }
	
}
