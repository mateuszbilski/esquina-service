package com.squeezedlemon.esquina.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squeezedlemon.esquina.service.entity.Checkin;
import com.squeezedlemon.esquina.service.entity.Location;
import com.squeezedlemon.esquina.service.entity.User;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {

	public List<Checkin> findFirst25ByUserInOrderByDateDesc(List<User> users);
	
	public List<Checkin> findByLocationOrderByDateDesc(Location location);
}
