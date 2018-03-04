package com.squeezedlemon.esquina.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.entity.LocationDescription;

public interface LocationDescriptionRepository extends
		JpaRepository<LocationDescription, Long> {

	public LocationDescription findByLocationAndDefaultLangIsTrue(Location location);
	
	public List<LocationDescription> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationOwnerContainingIgnoreCase(String name, String description, String owner);
	
}
