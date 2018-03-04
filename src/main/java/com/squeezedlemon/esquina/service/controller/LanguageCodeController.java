package com.squeezedlemon.esquina.service.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.exception.application.ValidationException;
import com.squeezedlemon.esquina.service.form.LangCodeForm;
import com.squeezedlemon.esquina.service.service.entity.LangCodeService;
import com.squeezedlemon.esquina.service.service.response.LangCodeResponseBuilder;

@RestController
public class LanguageCodeController {

	private static final String LANG_CODE_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.langCode";

	@Autowired
	private LangCodeService langCodeService;
	
	@Autowired
	private LangCodeResponseBuilder langCodeResponseBuilder;
	
	@Autowired
    private Validator validator;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST, 
			value = "langCode")
	public Map<String, Object> createLanguageCode(@Valid @RequestBody LangCodeForm form, BindingResult errors) throws ApplicationException {
		if (!errors.hasErrors()) {
			LangCode langCode = langCodeService.create(form.getLangCode());
			return langCodeResponseBuilder.languageCreated(langCode.getLangCode());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT, 
			value="langCode/{langCodeName}") 
	public void modifyLanguageCode(@PathVariable String langCodeName, @RequestBody LangCodeForm form, BindingResult errors) 
			throws ApplicationException {
		
		form.getLangCode().setLangCode(langCodeName);
		validator.validate(form, errors);
		
		if (!errors.hasErrors()) {
			langCodeService.update(form.getLangCode());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	@PreAuthorize("permitAll")
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value="langCode") 
	public Map<String, Object> getLanguageCodes() throws ApplicationException {
		return langCodeResponseBuilder.languageCodesDetails();
	}
	
	@PreAuthorize("permitAll")
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value="langCode/{langCodeName}")
	public Map<String, Object> getLanguageCode(@PathVariable String langCodeName) throws ApplicationException {
		LangCode langCode = langCodeService.findById(langCodeName);
		if (langCode != null) {
			return langCodeResponseBuilder.languageCodeDetails(langCode.getLangCode());
		} else {
			throw new EntityNotFoundException(LANG_CODE_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
}
