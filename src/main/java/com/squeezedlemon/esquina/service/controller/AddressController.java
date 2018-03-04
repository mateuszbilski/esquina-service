package com.squeezedlemon.esquina.service.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.squeezedlemon.esquina.service.entity.Address;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.EntityNotFoundException;
import com.squeezedlemon.esquina.service.exception.application.ValidationException;
import com.squeezedlemon.esquina.service.form.AddressForm;
import com.squeezedlemon.esquina.service.service.entity.AddressService;
import com.squeezedlemon.esquina.service.service.response.AddressResponseBuilder;

@PreAuthorize("isAuthenticated()")
@RestController
public class AddressController {

	private static final String ADDRESS_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.address";
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressResponseBuilder addressResponseBuilder;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json;charser=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST, value = "address")
	public Map<String, Object> createAddress(@Valid @RequestBody AddressForm form, BindingResult errors) throws ApplicationException {
		if (!errors.hasErrors()) {
			Address address = addressService.create(form.getAddress());
			return addressResponseBuilder.addressCreated(address.getId());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT, value = "address/{addressId}")
	public void modifyAddress(@Valid @RequestBody AddressForm form, BindingResult errors, @PathVariable Long addressId) throws ApplicationException {
		if (!errors.hasErrors()) {
			form.getAddress().setId(addressId);
			addressService.update(form.getAddress());
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value = "address/{addressId}")
	public Map<String, Object> getAddress(@PathVariable Long addressId) throws ApplicationException {
		Address address = addressService.findById(addressId);
		if (address != null) {
			return addressResponseBuilder.addressDetails(address.getId());
		} else {
			throw new EntityNotFoundException(ADDRESS_NOT_FOUND_MESSAGE_CODE);
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value = "address")
	public Map<String, Object> getAddresses() throws ApplicationException {
		return addressResponseBuilder.addressesDetails();
	}
}
