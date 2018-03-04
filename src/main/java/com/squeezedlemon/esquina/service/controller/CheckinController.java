package com.squeezedlemon.esquina.service.controller;

import java.awt.geom.Point2D;
import java.util.Calendar;
import java.util.Map;

import org.geotools.referencing.GeodeticCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.squeezedlemon.esquina.service.entity.Checkin;
import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.application.CheckinProximityException;
import com.squeezedlemon.esquina.service.exception.application.ValidationException;
import com.squeezedlemon.esquina.service.form.CheckinForm;
import com.squeezedlemon.esquina.service.service.entity.CheckinService;
import com.squeezedlemon.esquina.service.service.entity.LocationService;
import com.squeezedlemon.esquina.service.service.entity.UserService;
import com.squeezedlemon.esquina.service.service.response.CheckinResponseBuilder;

@PreAuthorize("hasRole('ROLE_USER')")
@RestController
public class CheckinController {
	
	private static final Double PROXIMITY_THRESHOLD = 50.0; //meters
	private static final String LOCATION_NOT_FOUND_MESSAGE_CODE = "exception.entityNotFoundException.location";
	
	@Autowired
	private CheckinService checkinService;
	
	@Autowired
	private CheckinResponseBuilder checkinResponseBuilder;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator validator;

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST, 
		value = "/user/logged/checkin")
	public Map<String, Object> checkin(@RequestBody CheckinForm form, BindingResult errors) throws ApplicationException {
		
		//Prepare form for validating
		form.getCheckin().setDate(Calendar.getInstance().getTime());

		//Validating
		validator.validate(form, errors);
		if (!errors.hasErrors()) {
			form.getCheckin().setUser(userService.findById(SecurityContextHolder.getContext().getAuthentication().getName()));
			Location loc = locationService.findById(form.getLocationId());
			if (loc != null) {
				form.getCheckin().setLocation(loc);
				if (checkProximity(form)) {
					Checkin checkin = checkinService.create(form.getCheckin());
					return checkinResponseBuilder.checkinCreated(checkin.getId());
				} else {
					throw new CheckinProximityException();
				}
			} else {
				//TODO do something with this fixed values!!!
    			errors.addError(new FieldError("form", "locationId", form.getLocationId(), false, null, null, LOCATION_NOT_FOUND_MESSAGE_CODE));
    			throw new ValidationException(errors, null);
			}
		} else {
			throw new ValidationException(errors, null);
		}
	}
	
	@RequestMapping(produces = "application/json;charset=UTF-8", method = RequestMethod.GET, value = "/user/logged/activity")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> activityStream() throws ApplicationException {
		return checkinResponseBuilder.getActivityStream(SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
	private boolean checkProximity(CheckinForm form) {
		
		GeodeticCalculator calc = new GeodeticCalculator();
	    Point2D userCoordinates = new Point2D.Double(form.getLongitude().doubleValue(),
	    		form.getLatitude().doubleValue());
	    Point2D locationCoordinates = new Point2D.Double(form.getCheckin().getLocation().getLongitude().doubleValue(), 
	    		form.getCheckin().getLocation().getLatitude().doubleValue());
	    calc.setStartingGeographicPoint(userCoordinates);
	    calc.setDestinationGeographicPoint(locationCoordinates);
	    
	    return (calc.getOrthodromicDistance() <= PROXIMITY_THRESHOLD);
	}
	
}
