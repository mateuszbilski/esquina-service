package com.squeezedlemon.esquina.service.controller;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.squeezedlemon.esquina.service.entity.Address;
import com.squeezedlemon.esquina.service.entity.LangCode;
import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.entity.LocationDescription;
import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.exception.application.ValidationException;
import com.squeezedlemon.esquina.service.form.LocationDescriptionForm;
import com.squeezedlemon.esquina.service.form.LocationForm;
import com.squeezedlemon.esquina.service.service.entity.AddressService;
import com.squeezedlemon.esquina.service.service.entity.LangCodeService;
import com.squeezedlemon.esquina.service.service.entity.LocationDescriptionService;
import com.squeezedlemon.esquina.service.service.entity.LocationService;
import com.squeezedlemon.esquina.service.service.entity.TagService;
import com.squeezedlemon.esquina.service.service.response.LocationResponseBuilder;

@PreAuthorize("isAuthenticated()")
@RestController
public class LocationController {

	private static final String ERROR_VALID_TAG_NOT_FOUND = "error.valid.tag.notFound";

	private static final String INVALID_LANGUAGE_MESSAGE_CODE = "error.valid.langCode.invalid";

	private static final String LOCATION_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.location";
	
	private static final String LOCATION_DESCRIPTION_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.locationDescription";
	
	private static final String ADDRESS_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.address";
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private LangCodeService langCodeService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationDescriptionService locationDescriptionService;
	
	@Autowired
	private LocationResponseBuilder locationResponseBuilder;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private Validator validator;
	
	/**
	 * Creates location entity. Notice that new entity hasn't got any location descriptions or tags. You should add them manually  
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST, 
			value = "location")
	public Map<String, Object> createLocation(@RequestBody LocationForm form, BindingResult errors) throws ApplicationException {
		
		prepareLocationForm(form, errors);
		form.getLocation().setCreatedDate(Calendar.getInstance().getTime());
		validator.validate(form, errors);
		
		if (!errors.hasErrors()) {
			Location location = locationService.create(form.getLocation());
			return locationResponseBuilder.locationCreated(location.getId());
		} else {
			throw new ValidationException(errors, null);
		}
	}

	/**
	 * Modifies location entity. It doesn't change location description or tags.
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT,
			value = "location/{locationId}") 
	public void modifyLocation(@PathVariable Long locationId, @RequestBody LocationForm form, BindingResult errors) throws ApplicationException {
		
		prepareLocationForm(form, errors);

		Location loc = locationService.findById(locationId);
		if (loc != null) {
			form.getLocation().setId(locationId);
			form.getLocation().setCreatedDate(loc.getCreatedDate());
			form.getLocation().setLocationDescriptions(loc.getLocationDescriptions());
		} else {
			throw new EntityNotFoundException(LOCATION_NOT_FOUND_MESSAGE_CODE);
		}
		validator.validate(form, errors);
		
		if (!errors.hasErrors()) {
			locationService.update(form.getLocation());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	
	/**
	 * Returns Location entity with appropriate description (see findAppropriateDescription method)
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value = "location/{locationId}")
	public Map<String, Object> getLocation(@PathVariable Long locationId) throws ApplicationException {

		Location location = locationService.findById(locationId);
		if (location != null) {
			return locationResponseBuilder.locationDetails(location.getId());
		} else {
			throw new EntityNotFoundException(LOCATION_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	
	/**
	 * Returns list of all locations stored in system with appropriate description (based on appropriate language).  
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value = "location")
	public Map<String, Object> getLocations() {
		
		return locationResponseBuilder.locationsDetails();
	}
	
	/**
	 * Creates new description for location 
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST,
			value = "location/{locationId}/description")
	public Map<String, Object> createLocationDescription(@PathVariable Long locationId, @Valid @RequestBody LocationDescriptionForm form,
			BindingResult errors) throws ApplicationException {
		
		prepareLocationDescriptionForm(locationId, form, errors);
		
		if (!errors.hasErrors()) {
			LocationDescription description = locationDescriptionService.create(form.getLocationDescription());
			return locationResponseBuilder.descriptionCreated(description.getId());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	/**
	 * Modifies LocationDescription entity 
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT,
			value = "location/{locationId}/description/{descriptionId}")
	public void modifyLocationDescription(@PathVariable Long locationId, @PathVariable Long descriptionId, 
			@Valid @RequestBody LocationDescriptionForm form, BindingResult errors) throws ApplicationException {
		
		prepareLocationDescriptionForm(locationId, form, errors);
		form.getLocationDescription().setId(descriptionId);
		if (!errors.hasErrors()) {
			locationDescriptionService.update(form.getLocationDescription());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	/**
	 * Deletes LocationDescription entity 
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.DELETE, 
			value = "location/{locationId}/description/{descriptionId}") 
	public void deleteLocationDescription(@PathVariable Long locationId, @PathVariable Long descriptionId) 
			throws ApplicationException {
		
		LocationDescription description = locationDescriptionService.findById(descriptionId);
		if (description != null && !description.getLocation().getId().equals(locationId)) {
			throw new EntityNotFoundException(LOCATION_DESCRIPTION_NOT_FOUND_MESSAGE_CODE);
		}
		
		try {
			locationDescriptionService.delete(descriptionId);
		} catch (EntityNotFoundException ex) {
			//It's ok. Client shouldn't see this error, because DELETE is idempotent
		}
	}
	
	/**
	 * Returns LocationDescription with descriptionId, that corresponds Location entity (locationId) 
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, 
			value = "location/{locationId}/description/{descriptionId}")
	public Map<String, Object> getLocationDescription(@PathVariable Long locationId, @PathVariable Long descriptionId) 
			throws ApplicationException {
		
		LocationDescription description = locationDescriptionService.findById(descriptionId);
		if (description == null || !description.getLocation().getId().equals(locationId)) {
			throw new EntityNotFoundException(LOCATION_DESCRIPTION_NOT_FOUND_MESSAGE_CODE);
		} else {
			return locationResponseBuilder.descriptionDetails(description.getId());
		}
	}
	
	/**
	 * Returns all LocationDescripton entities that belongs to Location
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET,
			value = "location/{locationId}/description")
	public Map<String, Object> getLocationDescriptions(@PathVariable Long locationId) throws ApplicationException {
		
		if (locationService.findById(locationId) != null) {
			return locationResponseBuilder.descriptionsDetails(locationId);
		} else {
			throw new EntityNotFoundException(LOCATION_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	
	/**
	 * Prepares LocationForm for validating. Method gets Address entity and writes one
	 * into Location entity
	 */
	private void prepareLocationForm(LocationForm form, BindingResult errors) {
		if (form.getAddressId() != null) {
			Address address = addressService.findById(form.getAddressId());
			if (address != null) {
				form.getLocation().setAddress(address);
			} else {
				//TODO do something with fixed values!!!
				errors.addError(new FieldError("form", "addressId", form.getAddressId(), false, null, null, ADDRESS_NOT_FOUND_MESSAGE_CODE));
			}
		}
		
		if (form.getTagsList() != null && !form.getTagsList().isEmpty()) {
			List<Tag> lst = new LinkedList<>();
			for (String tagName : form.getTagsList()) {
				Tag t = tagService.findById(tagName);
				if (t != null) {
					lst.add(t);
				} else {
					errors.addError(new FieldError("form", "tagsList", form.getTagsList(), false, null, new Object[]{tagName}, ERROR_VALID_TAG_NOT_FOUND));
				}
			}
			form.getLocation().setTags(lst);
		}
	}
	
	/**
	 * Prepares LocationDescriptionForm for validating. Method sets langCode and location in form  
	 */
	private void prepareLocationDescriptionForm(Long locationId, LocationDescriptionForm form, BindingResult errors) 
			throws ApplicationException {
		
		LangCode langCode = (form.getLanguage() != null ? langCodeService.findById(form.getLanguage()) : null);
		if (langCode != null) {
			form.getLocationDescription().setLangCode(langCode);
		} else {
			//TODO do something with fixed values!!!
			errors.addError(new FieldError("form", "language", form.getLanguage(), false, null, null, INVALID_LANGUAGE_MESSAGE_CODE));	
		}
		
		Location location = (locationId != null ? locationService.findById(locationId) : null);
		if (location != null) {
			form.getLocationDescription().setLocation(location);
		} else {
			throw new EntityNotFoundException(LOCATION_NOT_FOUND_MESSAGE_CODE);
		}
		
	}
	
}