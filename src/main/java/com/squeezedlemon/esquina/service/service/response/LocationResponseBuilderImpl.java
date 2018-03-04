package com.squeezedlemon.esquina.service.service.response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.squeezedlemon.esquina.service.entity.Account;
import com.squeezedlemon.esquina.service.entity.Checkin;
import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.entity.LocationDescription;
import com.squeezedlemon.esquina.service.entity.Tag;
import com.squeezedlemon.esquina.service.exception.application.ApplicationException;
import com.squeezedlemon.esquina.service.exception.runtime.DefaultLocationDescriptionNotFoundException;
import com.squeezedlemon.esquina.service.service.entity.AccountService;
import com.squeezedlemon.esquina.service.service.entity.CheckinService;
import com.squeezedlemon.esquina.service.service.entity.LocationDescriptionService;
import com.squeezedlemon.esquina.service.service.entity.LocationService;

@Service
public class LocationResponseBuilderImpl implements LocationResponseBuilder {
	
	private static final String SEARCH_RESULT_PROPERTY = "result";

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AddressResponseBuilder addressResponseBuilder;
	
	@Autowired
	private CheckinService checkinService;
	
	@Autowired
	private CheckinResponseBuilder checkinResponseBuilder;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationDescriptionService locationDescriptionService;
	
	@Autowired
	private TagResponseBuilder tagResponseBuilder;
	
	@Override
	@Transactional
	public Map<String, Object> searchDetails(String query) throws ApplicationException {
		List<Location> matchedLocations = locationService.search(query);
		List<Map<String, Object>> matchedLocationsJson = new LinkedList<>();
		for (Location item : matchedLocations) {
			matchedLocationsJson.add(locationDetails(item.getId()));
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put(SEARCH_RESULT_PROPERTY, matchedLocationsJson);
		return response;
	}
	
	@Override
	@Transactional
	public Map<String, Object> locationCreated(Long locationId) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("id", locationId);
		return map;
	}

	@Override
	@Transactional
	public Map<String, Object> locationDetails(Long locationId) {
		Map<String, Object> map = locationEntityDetails(locationId);
		
		List<Checkin> locationCheckins = checkinService.findLocationCheckins(locationService.findById(locationId));
		List<Map<String, Object>> checkins = new LinkedList<>(); 
		for (Checkin item : locationCheckins) {
			Map<String, Object> checkinMap = checkinResponseBuilder.checkinDetails(item.getId());
			checkinMap.remove("location");
			checkins.add(checkinMap);
		}
		map.put("checkins", checkins);
		return map;
	}
	
	@Override
	@Transactional
	public Map<String, Object> locationEntityDetails(Long locationId) {
		Map<String, Object> map = new HashMap<>();
		
		Location location = locationService.findById(locationId);
		
		LocationDescription description = findAppropriateDescription(location.getLocationDescriptions());
		if (description == null) {
			throw new DefaultLocationDescriptionNotFoundException(
					String.format("Missing default location description for locationId: %d", locationId));
		}
		
		map.put("id", location.getId());
		map.put("latitude", location.getLatitude());
		map.put("longitude", location.getLongitude());
		map.put("score", location.getScore());
		map.put("checkinCount", location.getCheckinCount());
		map.put("createdDate", location.getCreatedDate());
		map.put("owner", location.getOwner());
		map.put("website", location.getWebsite());
		map.put("icon", (location.getIcon() != null ? location.getIcon().getId() : null) );
		map.put("backgroundImage", (location.getBackgroundImage() != null ? location.getBackgroundImage().getId() : null) );
		
		if (location.getAddress() != null) {
			map.put("address", addressResponseBuilder.addressDetails(location.getAddress().getId()));
		} else {
			map.put("address", null);
		}
	
		List<Map<String, Object>> tagsList = new LinkedList<>();
		for (Tag item : location.getTags()) {
			tagsList.add(tagResponseBuilder.tagDetails(item.getTag(), LocaleContextHolder.getLocale().getLanguage()));
		}
		map.put("tags", tagsList);
		
		map.put("name", description.getName());
		map.put("description", description.getDescription());
		map.put("language", description.getLangCode().getLangCode());
		
		return map;
	}
	
	@Override
	@Transactional
	public Map<String, Object> locationsDetails() {
		
		List<Map<String, Object>> lst = new LinkedList<>();
		List<Location> locations = locationService.findAll();
		for (Location item : locations) {
			lst.add(locationDetails(item.getId()));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("locations", lst);
		return map;
	}

	@Override
	@Transactional
	public Map<String, Object> locationsDetails(List<Long> locationIds) {
		
		List<Map<String, Object>> lst = new LinkedList<>();
		for (Long itemId : locationIds) {
			lst.add(locationDetails(itemId));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("locations", lst);
		return map;
	}

	@Override
	@Transactional
	public Map<String, Object> descriptionCreated(Long descriptionId) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", descriptionId);
		return map;
	}

	@Override
	@Transactional
	public Map<String, Object> descriptionDetails(Long descriptionId) {
		
		LocationDescription description = locationDescriptionService.findById(descriptionId);
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", description.getId());
		map.put("name", description.getName());
		map.put("description", description.getDescription());
		map.put("defaultLang", description.getDefaultLang());
		map.put("language", description.getLangCode().getLangCode());
		map.put("locationId", description.getLocation().getId());
		return map;
	}
	
	@Override
	@Transactional
	public Map<String, Object> descriptionsDetails(Long locationId) {
		
		List<Map<String, Object>> descriptionsList = new LinkedList<>();
		List<LocationDescription> descriptions = locationService.findById(locationId).getLocationDescriptions();
		
		for (LocationDescription item : descriptions) {
			descriptionsList.add(descriptionDetails(item.getId()));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("descriptions", descriptionsList);
		return map;
	}
	
	/**
	 * Returns LocationDescription object in specific language that is resolved in this way:
	 * 1. language from user entity (only for account with ROLE_USER)
	 * 2. language from accept-language header (for others accounts)
	 * 3. default language
	 * 4. null object is returned if above methods fail
	 */
	private LocationDescription findAppropriateDescription(List<LocationDescription> descriptions) {
		
		//TODO optimize
		LocationDescription defaultDescription = null; 
		
		Map<String, LocationDescription> map = new HashMap<>();
		for (LocationDescription item : descriptions) {
			map.put(item.getLangCode().getLangCode(), item);
			if (item.getDefaultLang()) {
				defaultDescription = item;
			}
		}
		
		Account acc = accountService.findById(SecurityContextHolder.getContext().getAuthentication().getName());
		if (acc.getUser() != null) {
			//from user entity (only for account with ROLE_USER)	
			if (map.containsKey(acc.getUser().getLangCode().getLangCode())) {
				return map.get(acc.getUser().getLangCode().getLangCode());
			}
		} else {
			//from accept-language header (for others accounts)
			if (map.containsKey(LocaleContextHolder.getLocale().getLanguage())) {
				return map.get(LocaleContextHolder.getLocale().getLanguage());
			}
		}
		
		//If there aren't any appropriate description, now return default description
		return defaultDescription;
		
	}
}
