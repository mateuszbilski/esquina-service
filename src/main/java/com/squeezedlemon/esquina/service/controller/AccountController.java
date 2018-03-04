package com.squeezedlemon.esquina.service.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.squeezedlemon.esquina.service.entity.Account;
import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.BadRequestException;
import com.squeezedlemon.esquina.service.exception.application.ValidationException;
import com.squeezedlemon.esquina.service.form.CreateUserAccountForm;
import com.squeezedlemon.esquina.service.service.entity.AccountService;
import com.squeezedlemon.esquina.service.service.entity.LangCodeService;
import com.squeezedlemon.esquina.service.service.response.AccountResponseBuilder;

@RestController
public class AccountController {
	
	private static final String INVALID_LANGUAGE_CODE_MESSAGE_CODE = "error.valid.langCode.invalid";

	private static final String MISSING_PARAMETER_MESSAGE_CODE = "exception.badRequestException.missingParameters";

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private LangCodeService langCodeService;
	
	@Autowired
	private AccountResponseBuilder accountResponseBuilder;

	@PreAuthorize("isAnonymous()")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST, value = "/account")
    public Map<String, Object> createUserAccount(@Valid @RequestBody CreateUserAccountForm form, BindingResult errors) throws ApplicationException {

    	if (!errors.hasErrors()) {
    		LangCode code = langCodeService.findById(form.getLanguage());
    		if (code != null) {
    			form.getUser().setLangCode(code);
    			Account acc = accountService.createAccount(form.getUser(), form.getPassword());
    			return accountResponseBuilder.userAccountCreated(acc.getAccountName());
    		} else {
    			//TODO do something with this fixed values!!!
    			errors.addError(new FieldError("form", "language", form.getLanguage(), false, null, null, INVALID_LANGUAGE_CODE_MESSAGE_CODE));
    			throw new ValidationException(errors, null);
    		}
    	} else {
    		throw new ValidationException(errors, null);
    	}
    }
    
	@PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST, value = "account/logged/password")
    public void changePassword(@RequestBody JsonNode json) throws ApplicationException {
    	
    	JsonNode newPasswordNode = json.get("newPassword");
    	if (newPasswordNode != null && !newPasswordNode.isMissingNode()) {
	    	String accountName = SecurityContextHolder.getContext().getAuthentication().getName();
	    	accountService.changePassword(accountName, newPasswordNode.asText());
    	} else {
    		throw new BadRequestException(MISSING_PARAMETER_MESSAGE_CODE);
    	}
    }
    
	@PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.DELETE, value = "account/logged") 
    public void deactivateAccount() throws ApplicationException {
    	String accountName = SecurityContextHolder.getContext().getAuthentication().getName();
    	accountService.deactivateAccount(accountName);
    }
    
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.POST, value = "account/{accountName}/enabled")
    public void enableAccount(@PathVariable String accountName) throws ApplicationException {
    	accountService.activateAccount(accountName);
    }
}