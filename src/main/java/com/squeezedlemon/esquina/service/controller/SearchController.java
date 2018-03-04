package com.squeezedlemon.esquina.service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.BadRequestException;
import com.squeezedlemon.esquina.service.service.response.LocationResponseBuilder;
import com.squeezedlemon.esquina.service.service.response.UserResponseBuilder;

@RestController
@PreAuthorize("isAuthenticated()")
public class SearchController {

	private static final String SEARCH_QUERY_STRING_INVALID_MESSAGE_CODE = "error.valid.search.queryString";
	
	@Autowired
	private UserResponseBuilder userResponseBuilder;
	
	@Autowired
	private LocationResponseBuilder locationResponseBuilder;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json", method = RequestMethod.GET, value="/search/user")
	public Map<String, Object> searchUser(@RequestParam(required = true) String query) throws ApplicationException {
		query = StringUtils.trimWhitespace(query);
		if (query.length() >= 2) {
			return userResponseBuilder.searchDetails(query);
		} else {
			throw new BadRequestException(SEARCH_QUERY_STRING_INVALID_MESSAGE_CODE);
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json", method = RequestMethod.GET, value="/search/location")
	public Map<String, Object> searchLocation(@RequestParam(required = true) String query) throws ApplicationException {
		query = StringUtils.trimWhitespace(query);
		if (query.length() >= 2) {
			return locationResponseBuilder.searchDetails(query);
		} else {
			throw new BadRequestException(SEARCH_QUERY_STRING_INVALID_MESSAGE_CODE);
		}
	}
}
