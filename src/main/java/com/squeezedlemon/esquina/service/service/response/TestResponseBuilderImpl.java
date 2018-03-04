package com.squeezedlemon.esquina.service.service.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.exception.application.ApplicationException;

@Service
public class TestResponseBuilderImpl implements TestResponseBuilder {

	private static final String ANONYMOUS_GREETING_MESSAGE = "greeting.default";
	private static final String MEMBER_GREETING_MESSAGE = "greeting.member";
	
	private static final String GREETING_PROPERTY = "greeting";
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public Map<String, Object> greeting() throws ApplicationException {
		Map<String, Object> map = new HashMap<>();
		map.put(GREETING_PROPERTY, messageSource.getMessage(ANONYMOUS_GREETING_MESSAGE, null, LocaleContextHolder.getLocale()));
		return map;
	}

	@Override
	public Map<String, Object> memberGreeting(String accountName) throws ApplicationException {
		Map<String, Object> map = new HashMap<>();
		map.put(GREETING_PROPERTY, messageSource.getMessage(MEMBER_GREETING_MESSAGE, new Object[]{ accountName }, LocaleContextHolder.getLocale()));
		return map;
	}

}
