package com.squeezedlemon.esquina.service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.squeezedlemon.esquina.service.entity.Follower;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.BadRequestException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.service.entity.FollowerService;
import com.squeezedlemon.esquina.service.service.entity.UserService;
import com.squeezedlemon.esquina.service.service.response.UserResponseBuilder;

@PreAuthorize("isAuthenticated()")
@RestController
public class UserController {

	private static final String USER_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.user.detailed";

	private static final String MISSING_PARAMETER_MESSAGE_CODE = "exception.badRequestException.missingParameters";
	
	@Autowired
	private FollowerService followerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserResponseBuilder userResponseBuilder;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value="user/{accountName}")
	public Map<String, Object> getUser(@PathVariable String accountName) throws ApplicationException {
		if (userService.findById(accountName) != null) {
			return userResponseBuilder.userDetails(accountName);
		} else {
			throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE_CODE, new Object[] {accountName});
		}
	}
	
	/**
	 * This request is executed in context of actual logged user
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.POST, value="user/logged/following/{accountName}")
	public Map<String, Object> followUser(@PathVariable String accountName) throws ApplicationException {
		Follower following = followerService.followUser(SecurityContextHolder.getContext().getAuthentication().getName(), accountName);
		return userResponseBuilder.followingCreated(following.getId());
	}
	 
	/**
	 * This request is executed in context of actual logged user
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.DELETE, value="user/logged/following/{accountName}")
	public void unfollowUser(@PathVariable String accountName) throws ApplicationException {
		try {
			followerService.unfollowUser(SecurityContextHolder.getContext().getAuthentication().getName(), accountName);
		} catch (EntityNotFoundException ex) {
			//It's Okay because DELETE method must be idempotent
		}
	}
}

