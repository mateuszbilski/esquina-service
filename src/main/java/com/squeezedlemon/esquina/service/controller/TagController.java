package com.squeezedlemon.esquina.service.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.entity.TagTranslation;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.exception.application.ValidationException;
import com.squeezedlemon.esquina.service.form.TagForm;
import com.squeezedlemon.esquina.service.form.TagTranslationForm;
import com.squeezedlemon.esquina.service.service.entity.LangCodeService;
import com.squeezedlemon.esquina.service.service.entity.TagService;
import com.squeezedlemon.esquina.service.service.entity.TagTranslationService;
import com.squeezedlemon.esquina.service.service.response.TagResponseBuilder;

@PreAuthorize("isAuthenticated()")
@RestController
public class TagController {

	private static final String TAG_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.tag";
	
	private static final String TAG_TRANSLATION_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.tagTranslation";

	private static final String INVALID_LANGUAGE_CODE_MESSAGE_CODE = "error.valid.langCode.invalid";
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private TagService tagService;
	
	@Autowired 
	private TagTranslationService tagTranslationService;
	
	@Autowired
	private TagResponseBuilder tagResponseBuilder;
	
	@Autowired
	private LangCodeService langCodeService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST, 
			value="tag")
	public Map<String, Object> createTag(@Valid @RequestBody TagForm form, BindingResult errors) throws ApplicationException {
		
		if (!errors.hasErrors()) {
			Tag t = tagService.create(form.getTag());
			return tagResponseBuilder.tagCreated(t.getTag());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT, 
			value="tag/{tagName}")
	public void modifyTag(@PathVariable String tagName, @RequestBody TagForm form, BindingResult errors) throws ApplicationException {
		
		form.getTag().setTag(tagName);
		validator.validate(form, errors);
		
		if (!errors.hasErrors()) {
			Tag t = tagService.findById(form.getTag().getTag());
			if (t != null) {
				//Copy fields and update entity
				form.getTag().setTagTranslations(t.getTagTranslations());
				tagService.update(form.getTag());
			} else {
				throw new EntityNotFoundException(TAG_NOT_FOUND_MESSAGE_CODE);
			}
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	/**
	 * Returns tag with appropriate translation based on context
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, 
			value = "tag/{tagName}")
	public Map<String, Object> getTag(@PathVariable String tagName) throws ApplicationException {

		//Does tag exist?
		Tag tag = tagService.findById(tagName);
		if (tag == null) {
			throw new EntityNotFoundException(TAG_NOT_FOUND_MESSAGE_CODE);
		}
		
		return tagResponseBuilder.tagDetails(tagName, LocaleContextHolder.getLocale().getLanguage());
		
	}
	
	/**
	 * Returns tag with appropriate translation based on context
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value = "tag")
	public Map<String, Object> getTags() throws ApplicationException {

		return tagResponseBuilder.tagsDetails(LocaleContextHolder.getLocale().getLanguage());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, 
			value = "tag/{tagName}/translation/{translationId}")
	public Map<String, Object> getTagTranslation(@PathVariable String tagName, @PathVariable Long translationId) throws ApplicationException {

		TagTranslation tagTranslation = tagTranslationService.findById(translationId);
		if (tagTranslation != null && tagTranslation.getTag().getTag().equals(tagName)) {
			return tagResponseBuilder.translationDetails(tagTranslation.getId());
		} else {
			throw new EntityNotFoundException(TAG_TRANSLATION_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET,
			value = "tag/{tagName}/translation")
	public Map<String, Object> getTagTranslations(@PathVariable String tagName) throws ApplicationException {
		
		Tag tag = tagService.findById(tagName);
		if (tag != null) {
			return tagResponseBuilder.translationsDetails(tag.getTag());
		} else {
			throw new EntityNotFoundException(TAG_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST, 
			value = "tag/{tagName}/translation")
	public Map<String, Object> createTagTranslation(@PathVariable String tagName, @Valid @RequestBody TagTranslationForm form, 
			BindingResult errors) throws ApplicationException {
		
		prepareTagTranslationObject(tagName, form, errors);
		
		if (!errors.hasErrors()) {
			TagTranslation tagTranslation = tagTranslationService.create(form.getTagTranslation());
			return tagResponseBuilder.translationCreated(tagTranslation.getId());
		} else {
			throw new ValidationException(errors, null);
		}
		
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT, 
			value = "tag/{tagName}/translation/{translationId}")
	public void modifyTagTranslation(@PathVariable String tagName, @PathVariable Long translationId, @Valid @RequestBody TagTranslationForm form, 
			BindingResult errors) throws ApplicationException {
		
		prepareTagTranslationObject(tagName, form, errors);
		if (!errors.hasErrors()) {
			form.getTagTranslation().setId(translationId);
			tagTranslationService.update(form.getTagTranslation());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.DELETE, value = "tag/{tagName}/translation/{translationId}")
	public void deleteTagTranslation(@PathVariable String tagName, @PathVariable Long translationId) throws ApplicationException {
		
		TagTranslation translation = tagTranslationService.findById(translationId);
		if (translation != null && !translation.getTag().getTag().equals(tagName)) {
			throw new EntityNotFoundException(TAG_TRANSLATION_NOT_FOUND_MESSAGE_CODE);
		}
		
		try {
			tagTranslationService.delete(translationId);
		} catch (EntityNotFoundException ex) {
			//Client shouldn't see this error, because DELETE is idempotent
		}
	}
	
	private void prepareTagTranslationObject(String tagName, TagTranslationForm form, BindingResult errors) throws ApplicationException {
		
		//Prepare TagTranslation object
		LangCode langCode = (form.getLanguage() != null ? langCodeService.findById(form.getLanguage()) : null);
		if (langCode != null) {
			form.getTagTranslation().setLangCode(langCode);
		} else {
			//TODO do something with this fixed values!!!
			errors.addError(new FieldError("form", "language", form.getLanguage(), false, null, null, INVALID_LANGUAGE_CODE_MESSAGE_CODE));
		}
		
		Tag tag = (tagName != null ? tagService.findById(tagName) : null);
		if (tag != null) {
			form.getTagTranslation().setTag(tag);
		} else {
			throw new EntityNotFoundException(TAG_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
}
